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

public class GalleryFragment extends Fragment implements ServiceAdapter.OnItemClickListener {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private Button btnAddService;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        recyclerView = (RecyclerView) root.findViewById(R.id.recycleServices);
        btnAddService = (Button) root.findViewById(R.id.btnAddService);

        btnAddService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ServiceFormActivity.class));
            }
        });
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ServiceAdapter serviceAdapter = new ServiceAdapter(Service.ITEMS, this);
        recyclerView.setAdapter(serviceAdapter);
    }

    @Override
    public void onClick(ServiceAdapter.ViewHolder viewHolder, String id) {

    }
}