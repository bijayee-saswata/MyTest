package com.example.bg.mytest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    //private  int i=0;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final LinearLayout layout = findViewById(R.id.lay);
        setSupportActionBar(toolbar);

    /*    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                i++;
                if (i==1)
                {
                    layout.setVisibility(View.VISIBLE);
                }
                else if (i==2)
                {
                    layout.setVisibility(View.INVISIBLE);
                    i=0;
                }
            }
        });
*/
        mAuth = FirebaseAuth.getInstance();
       TextView info= (TextView) findViewById(R.id.info);
        SharedPreferences pref =getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String email = pref.getString("userEmail","");
        String password = pref.getString("userPassword","");
        info.setText("Email: \n"+email+"Password: \n"+password);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(this, "Sign out successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this,loginActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }


}
