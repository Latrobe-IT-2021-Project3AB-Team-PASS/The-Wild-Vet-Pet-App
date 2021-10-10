package com.example.demo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.service.controls.actions.BooleanAction;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;

import net.sourceforge.jtds.jdbc.Support;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PetEdit extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;

    TextInputLayout Pet_Name, Pet_Type, Pet_DOB, Pet_Notes;
    Button btnChooseImage, btnEditPetDetails;
    ImageView Pet_image;

    RadioGroup PetGender, PetDesexed;
    RadioButton PetMale,PetFemale,PetYes,PetNo;

    int SELECT_IMAGE_CODE = 1;

    Connection con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_edit);
        /*getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        String username = getIntent().getStringExtra("USERNAME");
        String petname = getIntent().getStringExtra("PETNAME");
        String petid = getIntent().getStringExtra("PETID");

        Pet_Name = findViewById(R.id.PetAddName);
        Pet_Type = findViewById(R.id.PetAddType);
        Pet_DOB = findViewById(R.id.PetAddDOB);
        PetGender = findViewById(R.id.PetGender);
        PetDesexed = findViewById(R.id.PetDesexed);
        PetFemale = findViewById(R.id.Petfemale);
        PetMale = findViewById(R.id.Petmale);
        PetYes = findViewById(R.id.PetYes);
        PetNo = findViewById(R.id.PetNo);
        Pet_Notes = findViewById(R.id.PetAddNote);


        btnEditPetDetails = findViewById(R.id.btnEditPetSubmit);


        btnEditPetDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PetEdit.EditPetDetails().execute();
            }
        });



        //Button upload image
        btnChooseImage = findViewById(R.id.choose_image_btn);
        Pet_image = (ImageView) findViewById(R.id.PetAddImage);

        btnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Title"),SELECT_IMAGE_CODE);
            }
        });

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String z = "";

        try {
            con = DBOpenHelper.getConn();
            if (con == null) {
                z = "Check Your Internet Access!";
            } else {

                String details = "SELECT * FROM Pet WHERE Pet_id = '"+ petid.toString()+"'";
                PreparedStatement preparedStatement = con.prepareStatement(details);
                ResultSet resultSet = preparedStatement.executeQuery();


                while (resultSet.next()) {
                    Pet_Name.getEditText().setText(resultSet.getString("Pet_name"));
                    Pet_Type.getEditText().setText(resultSet.getString("Pet_type"));
                    Pet_DOB.getEditText().setText(resultSet.getString("Pet_dob"));
                    Pet_Notes.getEditText().setText(resultSet.getString("Pet_notes"));

                    if(resultSet.getString("Pet_sex").equals("Male")){
                        PetMale.setChecked(true);
                    }else if (resultSet.getString("Pet_sex").equals("Female")){
                        PetFemale.setChecked(true);
                    }

                    if(resultSet.getString("Pet_desexed").equals("Yes")){
                        PetYes.setChecked(true);
                    }else if (resultSet.getString("Pet_desexed").equals("No")){
                        PetNo.setChecked(true);
                    }
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
                        Intent intent = new Intent(PetEdit.this, MyPets.class);
                        intent.putExtra("USERNAME", username);
                        startActivity(intent);
                        break;

                    case R.id.nav_vaccination:
                        Intent intent1 = new Intent(PetEdit.this, vaccination.class);
                        intent1.putExtra("recordUN",username);
                        startActivity(intent1);
                        break;

                    case R.id.nav_medication:
                        Intent intent2 = new Intent(PetEdit.this, Medication.class);
                        intent2.putExtra("recordUN",username);
                        startActivity(intent2);
                        break;

                    case R.id.nav_checkup:
                        Intent intent3 = new Intent(PetEdit.this, CheckUps.class);
                        intent3.putExtra("recordUN",username);
                        startActivity(intent3);
                        break;

                    case R.id.nav_news:
                        Intent intent4 = new Intent(PetEdit.this, News.class);
                        intent4.putExtra("recordUN",username);
                        startActivity(intent4);
                        break;

                    case R.id.nav_parasite:
                        Intent intent5 = new Intent(PetEdit.this, ParasitePrevention.class);
                        intent5.putExtra("recordUN",username);
                        startActivity(intent5);
                        break;

                    case R.id.nav_subscr:
                        Intent intent6 = new Intent(PetEdit.this, Subscription.class);
                        intent6.putExtra("recordUN",username);
                        startActivity(intent6);
                        break;

                    case R.id.nav_contact:
                        Intent intent7 = new Intent(PetEdit.this, ContactUs.class);
                        intent7.putExtra("recordUN",username);
                        startActivity(intent7);
                        break;

                    case R.id.nav_setting:
                        Intent intent8 = new Intent(PetEdit.this, AccountSetting.class);
                        intent8.putExtra("recordUN",username);
                        startActivity(intent8);
                        break;

                    case R.id.nav_support:
                        Intent intent9 = new Intent(PetEdit.this, Support.class);
                        intent9.putExtra("recordUN",username);
                        startActivity(intent9);
                        break;

                    case  R.id.nav_home:
                        Intent intent10 = new Intent(PetEdit.this, Homepage.class);
                        intent10.putExtra("recordUN",username);
                        startActivity(intent10);
                        break;

                    case R.id.nav_logout:
                        Intent intent11 = new Intent(PetEdit.this,MainActivity.class);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==1){
            Uri uri = data.getData();
            Pet_image.setImageURI(uri);
        }
    }

    // this event will enable the back
    // function to the button on press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    // Update Data to Database
    public class EditPetDetails extends AsyncTask<String,String,String> {

        String z = "";
        Boolean inSuccess = false; //used to check whether the login fails or not

        String Pname = Pet_Name.getEditText().getText().toString();
        String PType = Pet_Type.getEditText().getText().toString();
        String PDOB = Pet_DOB.getEditText().getText().toString();
        String PNotes = Pet_Notes.getEditText().getText().toString();


        @Override
        protected void onPreExecute() {
            if (validatePetname() | validatePettype() | validatePetgender() | validatePetdob() | validatePetdesexed() | validatePetnotes()) {
                Toast.makeText(PetEdit.this, "Sending Data to Database", Toast.LENGTH_SHORT).show();
            }else{
                return;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if(!inSuccess) {
                String username = getIntent().getStringExtra("USERNAME");
                String petname = getIntent().getStringExtra("PETNAME");
                String petid = getIntent().getStringExtra("PETID");
                Toast.makeText(PetEdit.this, "Add Pet Details Sucessful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), PetDetails.class);
                intent.putExtra("USERNAME", username);
                intent.putExtra("PETNAME", petname);
                intent.putExtra("PETID", petid);
                startActivity(intent);
            }
            else{
                Toast.makeText(PetEdit.this, z, Toast.LENGTH_SHORT).show();
                return;
            }
        }



        @Override
        protected String doInBackground(String... strings) {

            String gender = null;
            String desexed = null;


            try {
                con = DBOpenHelper.getConn();
                if (con == null){
                    z = "Check Your Internet Connection";
                }
                else
                {
                    //Check Gender Selected
                    if(PetFemale.isChecked()) {
                        gender = "Female";
                    }else if (PetMale.isChecked()){
                        gender = "Male";
                    }

                    //Check Desexed Selected
                    if(PetNo.isChecked()){
                        desexed = "No";
                    }else if (PetYes.isChecked()){
                        desexed = "Yes";
                    }

                    //Convert image to string,BLOB
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) Pet_image.getDrawable();
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                    byte[] bytes = byteArrayOutputStream.toByteArray();
                    String image = Base64.encodeToString(bytes, Base64.DEFAULT);

                    String username = getIntent().getStringExtra("USERNAME");
                    String petid = getIntent().getStringExtra("PETID");

                    String sql = "UPDATE Pet SET Pet_name ='" +Pname+ "', Pet_type ='" +PType+ "', Pet_sex ='" +gender+ "', Pet_dob ='" +PDOB+ "', Pet_image = '"+image+"', Pet_desexed = '"+desexed+"', Pet_notes = '"+PNotes+"' WHERE Pet_id = '"+ petid.toString() +"'";
                    Statement statement = con.createStatement();
                    statement.executeUpdate(sql);
                }
            }catch(Exception e){
                inSuccess = false;
                z = e.getMessage();
            }
            return z;
        }
    }

    //Validation Function

    //Validation Pet Name
    private boolean validatePetname(){
        String val = Pet_Name.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            Pet_Name.setError("Field can not be empty");
            return false;
        }
        else{
            Pet_Name.setError(null);
            Pet_Name.setErrorEnabled(false);
            return true;
        }
    }

    //Validation Pet Type
    private boolean validatePettype(){
        String val = Pet_Type.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            Pet_Type.setError("Field can not be empty");
            return false;
        }
        else{
            Pet_Type.setError(null);
            Pet_Type.setErrorEnabled(false);
            return true;
        }
    }

    //Validation Pet Gender
    private boolean validatePetgender(){
        if(PetGender.getCheckedRadioButtonId() == -1){
            Toast.makeText(this,"Please Select Pet Gender!",Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }

    //Validation Pet DOB
    private boolean validatePetdob(){
        String val = Pet_DOB.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            Pet_DOB.setError("Field can not be empty");
            return false;
        }
        else{
            Pet_DOB.setError(null);
            Pet_DOB.setErrorEnabled(false);
            return true;
        }
    }

    //Validation Pet Desexed
    private boolean validatePetdesexed(){
        if(PetDesexed.getCheckedRadioButtonId() == -1){
            Toast.makeText(this,"Please Select Pet Desexed!",Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }

    //Validation Pet notes
    private boolean validatePetnotes(){
        String val = Pet_Notes.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            Pet_DOB.setError("Field can not be empty");
            return false;
        }else if(val.length() > 100){
            Pet_Notes.setError("Pet notes not more than 100 words");
            return false;
        }
        else{
            Pet_Notes.setError(null);
            Pet_Notes.setErrorEnabled(false);
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        String username = getIntent().getStringExtra("USERNAME");
        String petname = getIntent().getStringExtra("PETNAME");
        String petid = getIntent().getStringExtra("PETID");
        Intent back = new Intent(PetEdit.this,PetDetails.class);
        back.putExtra("USERNAME", username);
        back.putExtra("PETNAME",petname);
        back.putExtra("PETID",petid);
        back.putExtra("PETID",petid);
        back.putExtra("recordUN",username);
        startActivity(back);
    }
}