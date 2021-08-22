package com.example.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class ForgetPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forgotpassword);
    }

    public void SubmitEmailForgetPassword(View view){
        Intent intent = new Intent(getApplicationContext(),SetNewPassword.class);
        startActivity(intent);
    }

}
