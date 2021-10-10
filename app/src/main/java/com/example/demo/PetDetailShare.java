package com.example.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import net.sourceforge.jtds.jdbc.Support;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.*;

public class PetDetailShare extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;

    TextView Pet_Name, Pet_Type, Pet_Gender, Pet_DOB, Pet_Desexed, Pet_Notes;
    ImageView Pet_Image,imageView;
    Button btnSaveScreen;
    RelativeLayout ShareImage;

    Connection connection;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_detail_share);
        /*getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        pd = new ProgressDialog(PetDetailShare.this);

        String username = getIntent().getStringExtra("USERNAME");
        String petname = getIntent().getStringExtra("PETNAME");
        String petid = getIntent().getStringExtra("PETID");

        //Variable
        Pet_Name = (TextView) findViewById(R.id.PetName);
        Pet_Type = (TextView) findViewById(R.id.PetType);
        Pet_Gender = (TextView) findViewById(R.id.PetGender);
        Pet_DOB = (TextView) findViewById(R.id.PetDOB);
        Pet_Desexed = (TextView) findViewById(R.id.PetDesexed);
        Pet_Notes = (TextView) findViewById(R.id.PetNotes);
        Pet_Image = (ImageView) findViewById(R.id.PetImage);

        ShareImage = (RelativeLayout) findViewById(R.id.ShareImage);

        imageView = (ImageView) findViewById(R.id.imageView);

        btnSaveScreen = (Button) findViewById(R.id.btnSaveImage);

        ActivityCompat.requestPermissions(PetDetailShare.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        ActivityCompat.requestPermissions(PetDetailShare.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);

        Boolean isSuccess = false;

        btnSaveScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = Bitmap.createBitmap(ShareImage.getWidth(),ShareImage.getHeight(),Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                ShareImage.draw(canvas);
                imageView.setImageBitmap(bitmap);

                //to get the image from the ImageView (say iv)
                BitmapDrawable draw = (BitmapDrawable) imageView.getDrawable();
                Bitmap bitmap1 = draw.getBitmap();

                FileOutputStream outStream = null;
                File sdCard = Environment.getExternalStorageDirectory();
                File dir = new File(sdCard.getAbsolutePath() + "/TheWildVet");
                dir.mkdirs();
                String fileName = String.format("%d.jpg", System.currentTimeMillis());
                File outFile = new File(dir, fileName);
                try {
                    outStream = new FileOutputStream(outFile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                try {
                    outStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String z = "";
        Boolean inSuccess = false; //used to check whether the login fails or not

        try {
            connection = DBOpenHelper.getConn();
            if (connection == null) {
                z = "Check Your Internet Access!";
            } else {
                String sql = "Select * From Pet Where Pet_name = '" + petname.toString() + "'";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();


                while (resultSet.next()) {
                    Pet_Name.setText(resultSet.getString("Pet_name"));
                    Pet_Type.setText(resultSet.getString("Pet_type"));
                    Pet_Gender.setText(resultSet.getString("Pet_sex"));
                    Pet_DOB.setText(resultSet.getString("Pet_dob"));
                    Pet_Desexed.setText(resultSet.getString("Pet_desexed"));
                    Pet_Notes.setText(resultSet.getString("Pet_notes"));

                    String image = resultSet.getString("Pet_image");

                    byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    Pet_Image.setImageBitmap(decodedByte);

                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        setUpToolbar();
        navigationView = (NavigationView) findViewById(R.id.navigation_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.nav_pets:
                        //String username = getIntent().getStringExtra("USERNAME");
                        Intent intent = new Intent(PetDetailShare.this, MyPets.class);
                        intent.putExtra("USERNAME",username);
                        startActivity(intent);
                        break;

                    case R.id.nav_vaccination:
                        Intent intent1 = new Intent(PetDetailShare.this, vaccination.class);
                        intent1.putExtra("recordUN",username);
                        startActivity(intent1);
                        break;

                    case R.id.nav_medication:
                        Intent intent2 = new Intent(PetDetailShare.this, Medication.class);
                        intent2.putExtra("recordUN",username);
                        startActivity(intent2);
                        break;

                    case R.id.nav_checkup:
                        Intent intent3 = new Intent(PetDetailShare.this, CheckUps.class);
                        intent3.putExtra("recordUN",username);
                        startActivity(intent3);
                        break;

                    case R.id.nav_news:
                        Intent intent4 = new Intent(PetDetailShare.this, News.class);
                        intent4.putExtra("recordUN",username);
                        startActivity(intent4);
                        break;

                    case R.id.nav_parasite:
                        Intent intent5 = new Intent(PetDetailShare.this, ParasitePrevention.class);
                        intent5.putExtra("recordUN",username);
                        startActivity(intent5);
                        break;

                    case R.id.nav_subscr:
                        Intent intent6 = new Intent(PetDetailShare.this, Subscription.class);
                        intent6.putExtra("recordUN",username);
                        startActivity(intent6);
                        break;

                    case R.id.nav_contact:
                        Intent intent7 = new Intent(PetDetailShare.this, ContactUs.class);
                        intent7.putExtra("recordUN",username);
                        startActivity(intent7);
                        break;

                    case R.id.nav_setting:
                        Intent intent8 = new Intent(PetDetailShare.this, AccountSetting.class);
                        intent8.putExtra("recordUN",username);
                        startActivity(intent8);
                        break;

                    case R.id.nav_support:
                        Intent intent9 = new Intent(PetDetailShare.this, Support.class);
                        intent9.putExtra("recordUN",username);
                        startActivity(intent9);
                        break;

                    case  R.id.nav_home:
                        Intent intent10 = new Intent(PetDetailShare.this, Homepage.class);
                        intent10.putExtra("recordUN",username);
                        startActivity(intent10);
                        break;

                    case R.id.nav_logout:
                        Intent intent11 = new Intent(PetDetailShare.this,MainActivity.class);
                        intent11.putExtra("recordUN",username);
                        startActivity(intent11);
                        break;
                }
                return false;
            }
        });
    }
    public void setUpToolbar() {
        drawerLayout = findViewById(R.id.drawerLayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }

    // this event will enable the back
    // function to the button on press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                String username = getIntent().getStringExtra("USERNAME");
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        String username = getIntent().getStringExtra("USERNAME");
        String petname = getIntent().getStringExtra("PETNAME");
        String petid = getIntent().getStringExtra("PETID");
        Intent back = new Intent(PetDetailShare.this,PetDetails.class);
        back.putExtra("USERNAME", username);
        back.putExtra("PETNAME",petname);
        back.putExtra("recordUN",username);
        startActivity(back);
    }
}