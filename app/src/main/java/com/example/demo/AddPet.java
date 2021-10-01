package com.example.demo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
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

import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class AddPet extends AppCompatActivity {

    TextInputLayout Pet_Name, Pet_Type, Pet_DOB, Pet_Notes;
    Button btnChooseImage, btnAddPetDetails;
    ImageView Pet_image;

    RadioGroup PetGender, PetDesexed;
    RadioButton PetMale,PetFemale,PetYes,PetNo;

    int SELECT_IMAGE_CODE = 1;

    Connection con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String username = getIntent().getStringExtra("USERNAME");

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

        btnAddPetDetails = findViewById(R.id.btnAddPetSubmit);

        btnAddPetDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddPetDetails().execute();
            }
        });

        //Button upload image
        btnChooseImage = findViewById(R.id.choose_image_btn);
        Pet_image = findViewById(R.id.PetAddImage);

        btnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Title"),SELECT_IMAGE_CODE);
            }
        });
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


    // Insert Data to Database
    public class AddPetDetails extends AsyncTask<String,String,String>{

        String z = "";
        Boolean isSuccess = false;

        String Pname = Pet_Name.getEditText().getText().toString();
        String PType = Pet_Type.getEditText().getText().toString();
        String PDOB = Pet_DOB.getEditText().getText().toString();
        String PNotes = Pet_Notes.getEditText().getText().toString();


        @Override
        protected void onPreExecute() {
            if (!validatePetname() | !validatePettype() | !validatePetgender() | !validatePetdob() | !validatePetdesexed() | !validatePetnotes()) {
                return;
            }else{
                Toast.makeText(AddPet.this, "Sending Data to Database", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if(isSuccess) {
                Toast.makeText(AddPet.this, z, Toast.LENGTH_SHORT).show();
            }
            else{
                String username = getIntent().getStringExtra("USERNAME");
                Toast.makeText(AddPet.this, "Add Pet Details Sucessful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MyPets.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
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

                    if(PetNo.isChecked()){
                        desexed = "No";
                    }else if (PetYes.isChecked()){
                        desexed = "Yes";
                    }

                    //Convert Image to String, BLOB
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) Pet_image.getDrawable();
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                    byte[] bytes = byteArrayOutputStream.toByteArray();
                    String image = Base64.encodeToString(bytes, Base64.DEFAULT);

                    String username = getIntent().getStringExtra("USERNAME");

                    //Select last PetID
                    String LastID = "SELECT Pet_id FROM Pet ORDER BY Pet_id DESC LIMIT 1";
                    PreparedStatement preparedStatement = con.prepareStatement(LastID);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()){
                        //Generate New PetID
                        String PetIDL = resultSet.getString("Pet_id");
                        int number = Integer.parseInt(PetIDL);
                        int x = 1;
                        int y = number + x;
                        Integer PetID = new Integer(y);

                        String sql = "INSERT INTO Pet (Pet_id,Account_username,Pet_name,Pet_type,Pet_sex,Pet_dob,Pet_image,Pet_desexed,Pet_notes) VALUE ('"+ PetID.toString() +"','" + username.toString() + "','"+Pname+"','"+PType+"','"+gender+"','"+PDOB+"','"+image+"','"+desexed+"','"+PNotes+"')";
                        Statement statement = con.createStatement();
                        statement.executeUpdate(sql);
                    }else{
                        Toast.makeText(AddPet.this, z, Toast.LENGTH_SHORT).show();
                    }
                }
            }catch(Exception e){
                isSuccess = false;
                z = e.getMessage();
            }
            return z;
        }
    }

    //Validation Function

    //Validation Pet name
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
    private boolean validatePetnotes() {
        String val = Pet_Notes.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            Pet_Notes.setError("Field can not be empty");
            return false;
        } else if (val.length() > 100) {
            Pet_Notes.setError("Pet notes not more than 100 words");
            return false;
        } else {
            Pet_Notes.setError(null);
            Pet_Notes.setErrorEnabled(false);
            return true;
        }
    }
}