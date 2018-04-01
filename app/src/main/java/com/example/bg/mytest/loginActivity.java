package com.example.bg.mytest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
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

import org.w3c.dom.Text;

public class loginActivity extends AppCompatActivity {

    private TextView fsignUp;

    private EditText email1,pass1;
    private Button login;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        //identification section....

        fsignUp=(TextView)findViewById(R.id.signUpt);
        email1 =(EditText)findViewById(R.id.lemail);
        pass1 =(EditText)findViewById(R.id.lpassword);
        login =(Button) findViewById(R.id.dummy_button);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);


        //function section.....
     fsignUp.setOnClickListener(new View.OnClickListener(){
         @Override
            public void onClick(View vi){
             startActivity(new Intent(loginActivity.this,signUpActivity.class));
            }
        });


        mAuthListner=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser() !=null)
                {
                    //Intent
                    startActivity(new Intent(loginActivity.this,MainActivity.class));
                }
            }
        };


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
                progressBar.setVisibility(View.VISIBLE);
            }
        });


    }





   @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth.addAuthStateListener(mAuthListner);
       /* FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);*/
    }

    private void signIn()
    {

        String user = email1.getText().toString();
        String password = pass1.getText().toString();
        if(TextUtils.isEmpty(user) || TextUtils.isEmpty(password))
        {
            Toast.makeText(getApplicationContext(), "Filled are Empty.",
                    Toast.LENGTH_SHORT).show();
        }
        else{
            mAuth.signInWithEmailAndPassword(user, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(getApplicationContext(), "Authentication Success.",
                                        Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(loginActivity.this,MainActivity.class));
                                progressBar.setVisibility(View.INVISIBLE);
                               // Log.d(TAG, "signInWithEmail:success");
                               // FirebaseUser user = mAuth.getCurrentUser();
                               // updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.

                                Toast.makeText(getApplicationContext(), task.getException()+" Authentication failed.",
                                        Toast.LENGTH_LONG).show();
                                updateUI(null);
                                progressBar.setVisibility(View.INVISIBLE);
                            }

                            // ...
                        }
                    });
        }

    }

    public void saveInfo(View view)
    {
        SharedPreferences pref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("userEmail",email1.getText().toString());
        editor.putString("userPassword",pass1.getText().toString());
        editor.apply();
        if(TextUtils.isEmpty(email1.getText().toString()) || TextUtils.isEmpty(pass1.getText().toString()))
        {
            Toast.makeText(getApplicationContext(), "Filled are Empty.",
                    Toast.LENGTH_SHORT).show();
        }
        else
        Toast.makeText(this,"Remember me applied",Toast.LENGTH_SHORT).show();

    }


   private void updateUI(FirebaseUser currentUser) {
    }
}
