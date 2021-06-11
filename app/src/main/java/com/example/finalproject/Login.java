package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

public class Login extends AppCompatActivity {
    EditText em,pa;
    Button l;
    TextView bt;
    FirebaseAuth Auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        em=findViewById(R.id.ema);
        pa=findViewById(R.id.pass);
        bt=findViewById(R.id.reg);
        l=findViewById(R.id.LOG);
        Auth=FirebaseAuth.getInstance();

        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email =em.getText().toString().trim();
                String pass =pa.getText().toString().trim();

                if(TextUtils.isEmpty(email)) {
                    em.setError("Enter email");
                }
                if(TextUtils.isEmpty(pass)) {
                    pa.setError("Enter Password");
                }
                Auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                   if(task.isSuccessful()){
                       Toast.makeText(Login.this,"logged in",Toast.LENGTH_SHORT).show();
                       startActivity(new Intent(getApplicationContext(),MainActivity.class));
                   }
                    }
                });
            }
        });

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });


    }
}