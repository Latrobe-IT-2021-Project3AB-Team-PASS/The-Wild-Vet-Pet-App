package com.example.demo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import net.sourceforge.jtds.jdbc.Support;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PetDetails extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;

    //Variable
    TextView PetName,PetType,PetGender,PetDOB,PetDesexed,PetID, PetNotes;
    Button btnPetEdit,btnDeletePet;
    ImageView PetImage;

    private Connection connection = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_details);
        /*// calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);*/

        String username = getIntent().getStringExtra("USERNAME");
        String petname = getIntent().getStringExtra("PETNAME");

        //Variable
        PetID = (TextView) findViewById(R.id.PetID);
        PetName = (TextView) findViewById(R.id.PetName);
        PetType = (TextView) findViewById(R.id.PetType);
        PetGender = (TextView) findViewById(R.id.PetGender);
        PetDOB = (TextView) findViewById(R.id.PetDOB);
        PetDesexed = (TextView) findViewById(R.id.PetDesexed);
        PetImage = (ImageView) findViewById(R.id.PetAddImage);
        PetNotes = (TextView) findViewById(R.id.PetNotes);

        btnPetEdit = findViewById(R.id.btnPetEdit);
        btnDeletePet = findViewById(R.id.btnDeletePet);

        btnPetEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String petname = getIntent().getStringExtra("PETNAME");
                String username = getIntent().getStringExtra("USERNAME");
                String petid = PetID.getText().toString();
                Intent petedit = new Intent(PetDetails.this,PetEdit.class);
                petedit.putExtra("USERNAME",username);
                petedit.putExtra("PETNAME",petname);
                petedit.putExtra("PETID",petid);
                startActivity(petedit);
            }
        });

        btnDeletePet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PetDetails.DeletePetDetails().execute();
                finish();
            }
        });

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);

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
                    PetID.setText(resultSet.getString("Pet_id"));
                    PetName.setText(resultSet.getString("Pet_name"));
                    PetType.setText(resultSet.getString("Pet_type"));
                    PetGender.setText(resultSet.getString("Pet_sex"));
                    PetDOB.setText(resultSet.getString("Pet_dob"));
                    PetDesexed.setText(resultSet.getString("Pet_desexed"));

                    String petnotes = resultSet.getString("Pet_notes");
                    if (petnotes != null){
                        PetNotes.setVisibility(View.VISIBLE);
                        PetNotes.setText(petnotes);
                    }else{
                        PetNotes.setVisibility(View.GONE);
                    }


                    String image = resultSet.getString("Pet_image");

                    byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    PetImage.setImageBitmap(decodedByte);

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
                        Intent intent = new Intent(PetDetails.this, MyPets.class);
                        intent.putExtra("USERNAME", username);
                        startActivity(intent);
                        break;

                    case R.id.nav_vaccination:
                        Intent intent1 = new Intent(PetDetails.this, vaccination.class);
                        intent1.putExtra("recordUN",username);
                        startActivity(intent1);
                        break;

                    case R.id.nav_medication:
                        Intent intent2 = new Intent(PetDetails.this, Medication.class);
                        intent2.putExtra("recordUN",username);
                        startActivity(intent2);
                        break;

                    case R.id.nav_checkup:
                        Intent intent3 = new Intent(PetDetails.this, CheckUps.class);
                        intent3.putExtra("recordUN",username);
                        startActivity(intent3);
                        break;

                    case R.id.nav_news:
                        Intent intent4 = new Intent(PetDetails.this, News.class);
                        intent4.putExtra("recordUN",username);
                        startActivity(intent4);
                        break;

                    case R.id.nav_parasite:
                        Intent intent5 = new Intent(PetDetails.this, ParasitePrevention.class);
                        intent5.putExtra("recordUN",username);
                        startActivity(intent5);
                        break;

                    case R.id.nav_subscr:
                        Intent intent6 = new Intent(PetDetails.this, Subscription.class);
                        intent6.putExtra("recordUN",username);
                        startActivity(intent6);
                        break;

                    case R.id.nav_contact:
                        Intent intent7 = new Intent(PetDetails.this, ContactUs.class);
                        intent7.putExtra("recordUN",username);
                        startActivity(intent7);
                        break;

                    case R.id.nav_setting:
                        Intent intent8 = new Intent(PetDetails.this, AccountSetting.class);
                        intent8.putExtra("recordUN",username);
                        startActivity(intent8);
                        break;

                    case R.id.nav_support:
                        Intent intent9 = new Intent(PetDetails.this, Support.class);
                        intent9.putExtra("recordUN",username);
                        startActivity(intent9);
                        break;

                    case  R.id.nav_home:
                        Intent intent10 = new Intent(PetDetails.this, Homepage.class);
                        intent10.putExtra("recordUN",username);
                        startActivity(intent10);
                        break;

                    case R.id.nav_logout:
                        Intent intent11 = new Intent(PetDetails.this,MainActivity.class);
                        intent11.putExtra("recordUN",username);
                        startActivity(intent11);
                        finish();
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

    //Delete Data from Database
    public class DeletePetDetails extends AsyncTask<String,String,String> {
        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute(){
            Toast.makeText(PetDetails.this, "Deleting Pet Data From Database", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(String s) {
            String username = getIntent().getStringExtra("USERNAME");
            Toast.makeText(PetDetails.this, "Delete Pet Details Sucessful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MyPets.class);
            intent.putExtra("USERNAME", username);
            startActivity(intent);
            finish();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                connection = DBOpenHelper.getConn();
                if (connection == null) {
                    z = "Check Your Internet Connection";
                }
                else{
                    String username = getIntent().getStringExtra("USERNAME");
                    String petname = getIntent().getStringExtra("PETNAME");
                    String petid = PetID.getText().toString();
                    String sql = "DELETE FROM Pet WHERE Pet_id = '" + petid.toString() + "'";
                    Statement statement = connection.createStatement();
                    statement.executeUpdate(sql);
                }
            }
            catch(Exception e){
                isSuccess = false;
                z = e.getMessage();
            }
            return z;
        }
    }

    //go to pet details share
    public void GotoPetDetailShare(View view){
        String petname = PetName.getText().toString();
        String petid = PetID.getText().toString();
        String username = getIntent().getStringExtra("USERNAME");
        Intent PetDetailShare = new Intent(PetDetails.this,PetDetailShare.class);
        PetDetailShare.putExtra("PETNAME",petname);
        PetDetailShare.putExtra("USERNAME",username);
        PetDetailShare.putExtra("PETID",petid);
        startActivity(PetDetailShare);
    }

    @Override
    public void onBackPressed() {
        String username = getIntent().getStringExtra("USERNAME");
        Intent back = new Intent(PetDetails.this,MyPets.class);
        back.putExtra("USERNAME", username);
        back.putExtra("recordUN",username);
        startActivity(back);
    }

}
