package com.example.bg.mytest;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;

public class signUpActivity extends AppCompatActivity {

    private TextView fsignIn;
    private Button signup;
    private EditText email,password;
    private ProgressBar progressBar1;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        fsignIn=(TextView)findViewById(R.id.signInt);
        signup=(Button) findViewById(R.id.signUp);
        email =(EditText)findViewById(R.id.semail);
        password =(EditText)findViewById(R.id.spassword);
        progressBar1 = (ProgressBar) findViewById(R.id.SprogressBar1);
        mAuth = FirebaseAuth.getInstance();

        fsignIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View vi){
                startActivity(new Intent(signUpActivity.this,loginActivity.class));
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar1.setVisibility(View.VISIBLE);
                signUp();
            }
        });

        mAuthListner=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser() !=null)
                {
                    //Intent
                    startActivity(new Intent(signUpActivity.this,MainActivity.class));
                }
            }
        };
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth.addAuthStateListener(mAuthListner);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }


    private void signUp()
    {
        String userId = email.getText().toString();
        String pass = password.getText().toString();

        if(TextUtils.isEmpty(userId) || TextUtils.isEmpty(pass))
        {
            Toast.makeText(getApplicationContext(), "Filled are Empty.",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            mAuth.createUserWithEmailAndPassword(userId, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                 FirebaseUser user = mAuth.getCurrentUser();
                                progressBar1.setVisibility(View.INVISIBLE);
                                Toast.makeText(getApplicationContext(), "Success",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                // If sign in fails, display a message to the user.


                                Toast.makeText(getApplicationContext(), task.getException()+" Authentication failed.",
                                        Toast.LENGTH_LONG).show();
                                progressBar1.setVisibility(View.INVISIBLE);
                                updateUI(null);
                            }

                            // ...
                        }
                    });
        }


    }
    private void updateUI(FirebaseUser currentUser) {
    }

}
