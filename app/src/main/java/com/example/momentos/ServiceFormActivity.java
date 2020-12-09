package com.example.momentos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import entity.Service;

public class ServiceFormActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    private ArrayList<Uri> imageList = new ArrayList<>();
    private Uri imageUrl;
    private Button btnChooseImage;
    private ProgressDialog progressDialog;
    private int upload_count = 0;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    final LodingLogin lodingLogin = new LodingLogin(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_form);
        TextView textView = (TextView) findViewById(R.id.text_form);
        Button btnAction = (Button) findViewById(R.id.btnAction);
        btnChooseImage = (Button) findViewById(R.id.btnChooseImage);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        textView.setText("Crear servicio");
        btnAction.setText("Crear");

        btnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(Intent.createChooser(intent, "Seleccione imagen"), PICK_IMAGE);
            }
        });
        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = ((EditText) findViewById(R.id.inputTitle)).getText().toString();
                String description = ((EditText) findViewById(R.id.txtDescription)).getText().toString();

                if (!title.isEmpty() && !description.isEmpty()) {
                    lodingLogin.startLoading();
                    String userId = mAuth.getCurrentUser().getUid();
                    String uid = mDatabase.child("service").push().getKey();
                    entity.Service service = new entity.Service(uid, title, description, new Date());
                    service.set_userId(userId);
                    StorageReference imageFolder = FirebaseStorage.getInstance().getReference().child("imageFolder");
                    for (upload_count = 0; upload_count < imageList.size(); upload_count++) {
                        Uri individualImage = imageList.get(upload_count);
                        StorageReference imageName = imageFolder.child("image" + individualImage.getLastPathSegment());
                        imageName.putFile(individualImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                imageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String url = String.valueOf(uri);
                                        StoreLink(service, url);
                                    }
                                });
                            }
                        });
                    }
                    Map<String, Object> serviceValues = service.toMap();

                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put("/service/" + uid, serviceValues);

                    mDatabase.updateChildren(childUpdates);
                    lodingLogin.dismissLoading();
                    onBackPressed();
                    finish();
                } else {
                    Toast.makeText(ServiceFormActivity.this, "Debe completar los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void StoreLink(Service service, String url) {
        ArrayList<String> images = service.get_images();
        images.add(url);
        service.set_images(images);
        Map<String, Object> serviceValues = service.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/service/" + service.get_uid(), serviceValues);
        mDatabase.updateChildren(childUpdates);
        /*DatabaseReference databaseReference = mDatabase.child("serviceImage");
        HashMap<String, Object> register = new HashMap<>();
        register.put("img", url);
        register.put("serviceId", service.get);

        databaseReference.push().setValue(register);*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == PICK_IMAGE) {
            //if (requestCode == RESULT_OK) {
                if (data.getClipData() != null) {
                    Integer countClipData = data.getClipData().getItemCount();
                    Integer currentImageSelect = 0;
                    while (currentImageSelect < countClipData) {
                        imageUrl = data.getClipData().getItemAt(currentImageSelect).getUri();
                        imageList.add(imageUrl);
                        currentImageSelect ++;
                    }
                    btnChooseImage.setText(imageList.size() == 1 ? "1 imagen seleccionada" : imageList.size() + " imágenes seleccionadas");
                } else {
                    Toast.makeText(ServiceFormActivity.this, "Seleccione imágenes", Toast.LENGTH_SHORT).show();
                }
            /*} else {
                Toast.makeText(ServiceFormActivity.this, "Naaa", Toast.LENGTH_SHORT).show();
            }*/
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}