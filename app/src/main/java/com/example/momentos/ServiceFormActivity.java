package com.example.momentos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ServiceFormActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    private ArrayList<Uri> imageList = new ArrayList<>();
    private Uri imageUrl;
    private Button btnChooseImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_form);
        TextView textView = (TextView) findViewById(R.id.text_form);
        Button btnAction = (Button) findViewById(R.id.btnAction);
        btnChooseImage = (Button) findViewById(R.id.btnChooseImage);
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
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}