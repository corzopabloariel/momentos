package com.example.momentos.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.momentos.R;
import com.example.momentos.ServiceActivity;
import com.example.momentos.ServiceFormActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

import adapters.ServiceAdapter;
import entity.Service;

public class GalleryFragment extends Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ServiceAdapter serviceAdapter;
    private Button btnAddService;
    private RecyclerView recyclerView;
    private ArrayList<Service> arrayList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        recyclerView = (RecyclerView) root.findViewById(R.id.recycleServices);
        btnAddService = (Button) root.findViewById(R.id.btnAddService);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        getServices();

        btnAddService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ServiceFormActivity.class));
            }
        });
        return root;
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