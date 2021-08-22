package com.example.demo.Connection;

import android.os.StrictMode;
import android.util.Log;

import java.sql.*;

public class ConnectionHelper {
    Connection con;
    String diver,url,user,password;

    public Connection connectionclass()
    {
        diver = "com.mysql.cj.jdbc.Driver";
        url = "jdbc:mysql://aws-thewildvet.ca1rdlo6efhz.us-east-2.rds.amazonaws.com:3306/TheWildVetDB?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8&useSSL=false";
        user = "root";//用户名"
        password = "rootroot";//密码

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;

        try
        {
            Class.forName(diver);
            connection = DriverManager.getConnection(url,user,password);//获取连接
        }
        catch (Exception e){
            Log.e("Error", e.getMessage());
        }
        return connection;
    }
}
