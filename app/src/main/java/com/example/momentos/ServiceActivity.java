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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import adapters.ServiceAdapter;
import entity.Service;

public class ServiceActivity extends AppCompatActivity implements ServiceAdapter.OnItemClickListener {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private Button btnAddService;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        recyclerView = (RecyclerView) findViewById(R.id.recycleServices);
        btnAddService = (Button) findViewById(R.id.btnAddService);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list(recyclerView);

        btnAddService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ServiceActivity.this, ServiceFormActivity.class));
            }
        });
    }

    private void list(@NonNull RecyclerView recyclerView) {
        ServiceAdapter serviceAdapter = new ServiceAdapter(Service.ITEMS, this);
        recyclerView.setAdapter(serviceAdapter);
    }

    @Override
    public void onServiceClick(int position) {
        startActivity(new Intent(ServiceActivity.this, ServiceFormActivity.class));
    }
}