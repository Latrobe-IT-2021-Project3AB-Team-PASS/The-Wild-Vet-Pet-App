package com.example.demo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.WindowManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.textfield.TextInputLayout;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ForgetPassword extends AppCompatActivity {

    TextInputLayout Account_email, Account_username;
    Button btnSubmitDetail;

    private Connection connection = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forgotpassword);

        //Variable
        Account_username = (TextInputLayout) findViewById(R.id.ForgetUsername);
        Account_email = (TextInputLayout) findViewById(R.id.ForgetEmail);
        btnSubmitDetail = (Button) findViewById(R.id.btnSubmitEmail);

        btnSubmitDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckEmail checkEmail = new CheckEmail(); // this is the Asynctask, which is used to process in background to reduce load on app process.
                checkEmail.execute();

            }
        });

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    /*public void SubmitEmailForgetPassword(View view){
        Intent intent = new Intent(getApplicationContext(),SetNewPassword.class);
        startActivity(intent);
    }*/

    public class CheckEmail extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean inSuccess = false; //used to check whether the login fails or not

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(ForgetPassword.this,s, Toast.LENGTH_SHORT).show();
            if (inSuccess)
            {
                Toast.makeText(ForgetPassword.this, "Details Match", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),SetNewPassword.class);
                startActivity(intent);
                finish();
            }
        }

        @Override
        protected String doInBackground(String... strings)
        {
            String email = Account_email.getEditText().getText().toString();
            String username = Account_username.getEditText().getText().toString();
            if (email.trim().equals("")||username.trim().equals(""))
            {
                z = "Please enter Email and Username";
            }
            else
            {
                try
                {
                    connection = DBOpenHelper.getConn();
                    if (connection == null)
                    {
                        z = "Check Your Internet Access!";
                    }
                    else
                    {
                        String sql = "select * from Account where Account_username= '" + username.toString() + "' and Account_email = '" + email.toString() +"' ";
                        Statement statement = connection.createStatement();
                        ResultSet resultSet = statement.executeQuery(sql);
                        if (resultSet.next())
                        {
                            z = "Details Match";
                            inSuccess = true;
                            connection.close();
                        }
                        else
                        {
                            z = "Invalid Credentials!";
                            inSuccess = false;
                        }
                    }
                }catch (Exception ex){
                    inSuccess = false;
                    z = ex.getMessage();
                }
            }
            return z;
        }
    }

    @Override
    public void onBackPressed() {
        Intent back = new Intent(ForgetPassword.this,MainActivity.class);
        startActivity(back);
        finish();
    }

}
