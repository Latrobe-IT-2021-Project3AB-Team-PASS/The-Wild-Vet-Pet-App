package com.example.demo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PetEdit extends AppCompatActivity {

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                new EditPetDetails().execute();
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
}