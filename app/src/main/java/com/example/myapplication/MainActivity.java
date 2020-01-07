package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editemail, editpassword;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.textviewsingupcall).setOnClickListener(this);

        editemail = (EditText) findViewById(R.id.loginemail1);
        editpassword = (EditText) findViewById(R.id.loginpassword);
        progressBar = (ProgressBar) findViewById(R.id.progress_circular);
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.loginButton).setOnClickListener(this);
        findViewById(R.id.textviewsingupcall).setOnClickListener(this);

    }

    private void UserLogin() {//before entering user info into the db first check it is valid or not
        String email = editemail.getText().toString().trim();
        String password = editpassword.getText().toString().trim();
        if (email.isEmpty()) {
            editemail.setError("Email cannot be empty");
            editemail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editpassword.setError("Password cannot be empty");
            editpassword.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editemail.setError("Enter valid email id");
            editemail.requestFocus();
            return;
        }
        if (password.length() < 6) {
            editpassword.setError("Password length must be greater than or equal to 6");
            editpassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    finish();
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG);
                }
            }
        });
    }
    //user agr logged in he to usko firse login na krane k loe
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        if (mAuth.getCurrentUser() != null) {
//            finish();
//            startActivity(new Intent(MainActivity.this, homeactivity.class));
//        }
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textviewsingupcall:
                finish();
                startActivity(new Intent(this, com.example.myapplication.SignupActivity.class));
                break;

            case R.id.loginButton:
                UserLogin();
                break;

        }
    }
}

