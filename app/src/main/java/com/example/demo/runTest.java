package com.example.demo;



import androidx.appcompat.app.AppCompatActivity;



import android.os.AsyncTask;

import android.os.Bundle;

import android.view.View;

import android.widget.Button;

import android.widget.TextView;

import java.sql.*;



public class runTest extends AppCompatActivity {



    TextView text,errorText;

    Button show;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_aws_test);







        text = (TextView) findViewById(R.id.textView1);

        errorText = (TextView) findViewById(R.id.textView2);

        show = (Button) findViewById(R.id.button);



        show.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                new Async().execute();

            }

        });

    }

    public static String diver = "com.mysql.cj.jdbc.Driver";

    //private static String url = "jdbc:mysql://192.168.31.24:3306/数据库?characterEncoding=utf-8";
    public static String url = "jdbc:mysql://aws-thewildvet.ca1rdlo6efhz.us-east-2.rds.amazonaws.com:3306/TheWildVetDB?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8&useSSL=false";
    public static String user = "root";//用户名"
    public static String password = "rootroot";//密码

    public static Connection connection = null;
    public static PreparedStatement preparedStatement = null;
    public static ResultSet resultSet = null;


    class Async extends AsyncTask<Void, Void, Void> {


        String records = "",error="";

        @Override

        protected Void doInBackground(Void... voids) {

            try

            {
                String sql = "SELECT * FROM Account";
                Class.forName(diver);
                Connection connection = DriverManager.getConnection(url,user,password);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next()) {

                    records += resultSet.getString(1) + " " + resultSet.getString(2) + "\n";
                }

            }

            catch(Exception e)

            {

                error = e.toString();

            }

            return null;

        }



        @Override

        protected void onPostExecute(Void aVoid) {

            text.setText(records);

            if(error != "")

                errorText.setText(error);

            super.onPostExecute(aVoid);

        }





    }

}