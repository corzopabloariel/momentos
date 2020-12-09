package com.example.momentos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AccessActivity extends AppCompatActivity {

    private Button btnRegistro, btnAcceder;
    private EditText editTextEmail, editTextPass;
    private String email, pass;
    private FirebaseAuth mAuth;
    final LodingLogin lodingLogin = new LodingLogin(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access);
        btnRegistro = findViewById(R.id.btnRegistro);
        btnAcceder = findViewById(R.id.btnAcceder);
        mAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.inputEmail);
        editTextPass = findViewById(R.id.inputPass);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccessActivity.this, RegisterActivity.class));
            }
        });
        btnAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = editTextEmail.getText().toString();
                pass = editTextPass.getText().toString();

                if (!email.isEmpty() && !pass.isEmpty()) {
                    lodingLogin.startLoading();
                    access();
                } else {
                    Toast.makeText(AccessActivity.this, "Debe completar los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void access() {
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(AccessActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    lodingLogin.dismissLoading();
                    startActivity(new Intent(AccessActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(AccessActivity.this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(AccessActivity.this, MainActivity.class));
            finish();
        }
    }
}