package com.example.momentos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.EventListener;

import adapters.ServiceAdapter;
import entity.Service;

public class ServiceActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ServiceAdapter serviceAdapter;
    private Button btnAddService;
    private RecyclerView recyclerView;
    private ArrayList<Service> arrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        recyclerView = (RecyclerView) findViewById(R.id.recycleServices);
        btnAddService = (Button) findViewById(R.id.btnAddService);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getServices();

        btnAddService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ServiceActivity.this, ServiceFormActivity.class));
            }
        });
    }

    private void getServices() {
        mDatabase.child("services").orderByChild("user_id").equalTo(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for(DataSnapshot ds: dataSnapshot.getChildren()) {
                        String title = ds.child("title").getValue().toString();
                        String text = ds.child("text").getValue().toString();
                        Date onCreate = (Date) ds.child("onCreate").getValue();

                        arrayList.add(new Service(title, text, onCreate, null));
                    }

                    serviceAdapter = new ServiceAdapter(arrayList, R.layout.service_view);
                    recyclerView.setAdapter(serviceAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}