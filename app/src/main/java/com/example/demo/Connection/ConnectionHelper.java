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
        url = "jdbc:mysql://aws-thewildvet.ctgmk9otetzz.ap-southeast-2.rds.amazonaws.com:3306/TheWildVetDB?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8&useSSL=false";
        user = "root";//username"
        password = "rootroot";//password

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;

        try
        {
            Class.forName(diver);
            connection = DriverManager.getConnection(url,user,password);//get connection
        }
        catch (Exception e){
            Log.e("Error", e.getMessage());
        }
        return connection;
    }
}
