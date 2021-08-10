package com.example.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
    }

    public void LoginToHomepage(View view){

        Intent intent = new Intent(getApplicationContext(),Homepage.class);
        startActivity(intent);

    }

    public void callToSignup(View view){

        Intent intent = new Intent(getApplicationContext(),Signup.class);
        startActivity(intent);

    }
}
