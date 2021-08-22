package com.example.demo;

import com.example.demo.DBOpenHelper;
import com.example.demo.R;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.StrictMode;
import android.view.View;
import android.widget.TextView;

import android.widget.ImageView;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class awsTest extends AppCompatActivity {

    private TextView textView1;
    private Connection connection = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aws_test);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);
        textView1 = findViewById(R.id.textView1);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            connection = DBOpenHelper.getConn();
            textView1.setText("SUCCESS");

        } catch (Exception e) {
            e.printStackTrace();
            textView1.setText("Connect failed");
        }
    }

    public void sqlButton(View view){
        if (connection!=null){
            Statement statement = null;
            try {
                String sql = "select * from Account";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()){
                    textView1.setText("Username: "+ resultSet.getString("Account_username") + " password: " + resultSet.getString("Account_password"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            textView1.setText("Connection is null");
        }
    }
}