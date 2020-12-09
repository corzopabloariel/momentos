package com.example.momentos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import entity.User;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        mDataBase = FirebaseDatabase.getInstance().getReference();
        Button btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        Button btnVolver = (Button) findViewById(R.id.btnVolver);

        EditText editTextName = (EditText) findViewById(R.id.inputName);
        EditText editTextEmail = (EditText) findViewById(R.id.inputEmail);
        EditText editTextPass = (EditText) findViewById(R.id.inputPass);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String email = editTextEmail.getText().toString();
                String pass = editTextPass.getText().toString();

                if (!name.isEmpty() && !email.isEmpty() && !pass.isEmpty()) {
                    if (pass.length() >= 6) {
                        registrarUsuario(name, email, pass);
                    } else {
                        // Detalle de Firebase
                        Toast.makeText(RegisterActivity.this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Debe completar los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, AccessActivity.class));
            }
        });
    }

    private void registrarUsuario(String name, String email, String pass) {
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String id = mAuth.getCurrentUser().getUid();
                    User user = new User(name, email, pass, id);
                    Map<String, Object> map = user.toMap();

                    mDataBase.child("users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task_u) {
                            if (task_u.isSuccessful()) {
                                startActivity(new Intent(RegisterActivity.this, ProfileActivity.class));
                                finish();
                            } else {
                                Toast.makeText(RegisterActivity.this, "No se pudo crear los datos", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(RegisterActivity.this, "No se pudo registrar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}