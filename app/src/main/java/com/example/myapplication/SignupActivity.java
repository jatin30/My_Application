package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editemail, editpassword, editname,editname1,editname2,editname3,editcontact1,editcontact2,editcontact3;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        editemail = (EditText) findViewById(R.id.email);
        editpassword = (EditText) findViewById(R.id.password);
        editname = (EditText) findViewById(R.id.name);
        editname1 = (EditText) findViewById(R.id.name1);
        editname2 = (EditText) findViewById(R.id.name2);
        editname3 = (EditText) findViewById(R.id.name3);
        editcontact1 = (EditText) findViewById(R.id.contact1);
        editcontact2 = (EditText) findViewById(R.id.contact2);
        editcontact3 = (EditText) findViewById(R.id.contact3);
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.signupButton).setOnClickListener(this);
        findViewById(R.id.textviewlogincall).setOnClickListener(this);
        progressBar=(ProgressBar)findViewById(R.id.progress_circular1);


    }
    private void registerUser()
    {//before entering user info into the db first check it is valid or not
        FirebaseUser user= mAuth.getCurrentUser();
        String email=editemail.getText().toString().trim();
        String password=editpassword.getText().toString().trim();
        final String name = editname.getText().toString().trim();
        final String name1 = editname1.getText().toString().trim();
        final String name2 = editname2.getText().toString().trim();
        final String name3 = editname3.getText().toString().trim();
        final String contact1 = editcontact1.getText().toString().trim();
        final String contact2 = editcontact2.getText().toString().trim();
        final String contact3 = editcontact3.getText().toString().trim();
        if (email.isEmpty())
        {editemail.setError("Email cannot be empty");
            editemail.requestFocus();
            return;
        }
        if (password.isEmpty())
        {
            editpassword.setError("Password cannot be empty");
            editpassword.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            editemail.setError("Enter valid email id");
            editemail.requestFocus();
            return;
        }
        if (password.length()<6) {
            editpassword.setError("Password length must be greater than or equal to 6");
            editpassword.requestFocus();
            return;
        }
//        if(user.isEmailVerified())
//        {

            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful())
                    {    User user = new User(name,name1,name2,name3,contact1,contact2,contact3);
                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user);
                        finish();
                        startActivity(new Intent(SignupActivity.this,MainActivity.class));
                        Toast.makeText(getApplicationContext(),"you have registered successfully!!",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {  if (task.getException() instanceof FirebaseAuthUserCollisionException)
                    {
                        Toast.makeText(getApplicationContext(),"you have already registered !!",Toast.LENGTH_SHORT).show();

                    }
                    else

                        Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                    }
                }
            });
//        }
//        else
//        {
//            Toast.makeText(getApplicationContext(),"Please click on the link send to your email address",Toast.LENGTH_LONG).show();
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.signupButton:
                registerUser();
                break;
            case R.id.textviewlogincall:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }

    }
}
