package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText fullName,email,phone,pwd1,pwd2;
    Button registerBtn;
    TextView gologin;
    FirebaseAuth uAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fullName=findViewById(R.id.fullName);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phone);
        pwd1=findViewById(R.id.pwd1);
        pwd2=findViewById(R.id.pwd2);
        gologin=findViewById(R.id.gologin);
        registerBtn=findViewById(R.id.registerBtn);
        progressBar=findViewById(R.id.progressBar);
        uAuth=FirebaseAuth.getInstance();

        if(uAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),Login.class));
            finish();
        }
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email1 = email.getText().toString().trim();
                String pwd = pwd1.getText().toString().trim();

                if (TextUtils.isEmpty(email1)) {
                    email.setError("Email field is required!");
                    return;

                }
                if (TextUtils.isEmpty(pwd)) {
                    pwd1.setError("password field is required!");
                    return;
                }
                if (pwd.length()<6) {
                    pwd1.setError("password must be atleast six characters");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                //register user
                uAuth.createUserWithEmailAndPassword(email1,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            Toast.makeText(Register.this,"account created",Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(Register.this,"Error!"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);

                        }
                    }
                });

            }
        });
        gologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });
    }
}
