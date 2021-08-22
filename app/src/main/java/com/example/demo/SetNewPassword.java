package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import static com.example.demo.R.*;

public class SetNewPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_set_new_password);
    }

    public void SubmitNewPassword(View view){
        Intent intent = new Intent(getApplicationContext(),ForgetPasswordSuccessMessage.class);
        startActivity(intent);
    }
}