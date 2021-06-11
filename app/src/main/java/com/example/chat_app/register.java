package com.example.chat_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class register extends AppCompatActivity {
    EditText Name,Email,Password,Phone;
    Button reg;
    TextView login;
    FirebaseAuth Auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Name = findViewById(R.id.name);
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        Phone = findViewById(R.id.ema);
        reg = findViewById(R.id.register);
        login = findViewById(R.id.lg);

        Auth=FirebaseAuth.getInstance();

        if(Auth.getCurrentUser()!= null)
        {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email =Email.getText().toString().trim();
                String pass =Password.getText().toString().trim();

                if(TextUtils.isEmpty(email)) {
                    Email.setError("Enter email");
                }
                if(TextUtils.isEmpty(pass)) {
                    Password.setError("Enter Password");
                }
                Auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(register.this,"user created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            Toast.makeText(register.this,"error"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    }});

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),login.class));
            }
        });
    }
}