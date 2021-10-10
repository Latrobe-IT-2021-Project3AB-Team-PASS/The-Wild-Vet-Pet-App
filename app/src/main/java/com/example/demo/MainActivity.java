package com.example.demo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import java.sql.*;

public class MainActivity extends AppCompatActivity {

    //Variables
    TextInputLayout Account_username, Account_password;
    Button btn_login, btn_register, btn_forgetpassword;

    Connection con = null;
    String diver,url,user,pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Account_username = (TextInputLayout) findViewById(R.id.accountUsername);
        Account_password = (TextInputLayout) findViewById(R.id.accountPassword);
        btn_login = (Button) findViewById(R.id.btnLogin);
        btn_register = (Button) findViewById(R.id.btnRegister);
        btn_forgetpassword = (Button) findViewById(R.id.forget_btn);


        //Declaring Server
        diver = "com.mysql.jdbc.Driver";
        url = "jdbc:mysql://aws-thewildvet.ctgmk9otetzz.ap-southeast-2.rds.amazonaws.com:3306/TheWildVetDB?";
        user = "root";
        pass = "rootroot";

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckLogin checkLogin = new CheckLogin(); // this is the Asynctask, which is used to process in background to reduce load on app process.
                checkLogin.execute();


            }
        });


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Signup.class);
                startActivity(intent);
                finish();
            }
        });

        btn_forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ForgetPassword.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public class CheckLogin extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean inSuccess = false; //used to check whether the login fails or not

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(MainActivity.this,s, Toast.LENGTH_SHORT).show();
            if (inSuccess)
            {
                Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),Homepage.class);
                String recordUserName = Account_username.getEditText().getText().toString();
                intent.putExtra("recordUN",recordUserName); //record the account number for delivery to the next activity
                startActivity(intent);
                finish();

            }
        }

        @Override
        protected String doInBackground(String... strings)
        {
            String username = Account_username.getEditText().getText().toString();
            String password = Account_password.getEditText().getText().toString();
            if (username.trim().equals("")||password.trim().equals(""))
            {
                z = "Please enter Username and Password";
            }
            else
            {
                try
                {
                    con = DBOpenHelper.getConn();
                    if (con == null)
                    {
                        z = "Check Your Internet Access!";
                    }
                    else
                    {
                        String sql = "select * from Account where Account_username= '" + username.toString() + "' and Account_password = '" + password.toString() +"' ";
                        Statement statement = con.createStatement();
                        ResultSet resultSet = statement.executeQuery(sql);
                        if (resultSet.next())
                        {
                            z = "Login succesful";
                            inSuccess = true;
                            con.close();
                        }
                        else
                        {
                            z = "Invalid detail!";
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
        finishAffinity();
    }
}
