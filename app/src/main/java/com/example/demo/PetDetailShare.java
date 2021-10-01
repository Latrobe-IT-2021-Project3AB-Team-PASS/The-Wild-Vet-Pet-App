package com.example.demo;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PetDetailShare extends AppCompatActivity {

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
}