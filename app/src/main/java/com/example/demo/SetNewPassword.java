package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static com.example.demo.R.*;

import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class SetNewPassword extends AppCompatActivity {

    //Variable
    TextInputLayout NewPassword, ConfirmPassword, ConfirmUser;
    Button SubmitNewPassword;

    private Connection connection = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_set_new_password);

        //Variable
        NewPassword = (TextInputLayout) findViewById(id.NewPass);
        ConfirmPassword = (TextInputLayout) findViewById(id.ConfirmPass);
        ConfirmUser = (TextInputLayout) findViewById(id.ConfirmUsername);
        SubmitNewPassword = (Button) findViewById(id.SubmitNewPass);

        SubmitNewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubmitPass submitPass = new SubmitPass(); // this is the Asynctask, which is used to process in background to reduce load on app process.
                submitPass.execute();

            }
        });

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    /*public void SubmitNewPassword(View view){
        Intent intent = new Intent(getApplicationContext(),ForgetPasswordSuccessMessage.class);
        startActivity(intent);
    }*/

    public class SubmitPass extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean inSuccess = false; //used to check whether the login fails or not

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(SetNewPassword.this, "Set New Password Successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(),ForgetPasswordSuccessMessage.class);
            startActivity(intent);
            finish();
        }

        @Override
        protected String doInBackground(String... strings)
        {
            String Newpass = NewPassword.getEditText().getText().toString();
            String Confirmpass = ConfirmPassword.getEditText().getText().toString();
            String ConfirmUsername = ConfirmUser.getEditText().getText().toString();
            if ((Newpass.trim().equals("")||Confirmpass.trim().equals("")||ConfirmUsername.trim().equals("")) && (Newpass != Confirmpass))
            {
                z = "Please reenter Your Username and New Password";
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
                        String sql = "update Account set Account_password= '"+ Newpass.toString() +"' WHERE Account_username= '" + ConfirmUsername.toString() + "' ";
                        Statement statement = connection.createStatement();
                        statement.executeUpdate(sql);
                    }
                }catch (Exception ex){
                    inSuccess = false;
                    z = ex.getMessage();
                }
            }
            return z;
        }
    }
}