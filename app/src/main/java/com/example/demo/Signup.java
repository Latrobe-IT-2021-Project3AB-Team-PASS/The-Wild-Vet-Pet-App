package com.example.demo;

import android.accounts.Account;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputLayout;

public class Signup extends AppCompatActivity {

    //Variables
    Button btnRegSubmit;
    TextView signup_description, registerTitle;

    //Get Data Variable
    TextInputLayout Account_username, Account_password, Account_fullname, Account_email, Account_address, Account_phone;
    RadioGroup Account_nametitle;
    RadioButton selectedNametitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);

        //Hooks
        btnRegSubmit = findViewById(R.id.btnRegSubmit);
        signup_description = findViewById(R.id.signup_description);
        registerTitle = findViewById(R.id.registerTitle);


        //Hooks for geeting data
        Account_username = findViewById(R.id.regUsername);
        Account_password = findViewById(R.id.regPassword);
        Account_nametitle = findViewById(R.id.nameTitle);
        Account_fullname = findViewById(R.id.regFullname);
        Account_email = findViewById(R.id.regEmail);
        Account_address = findViewById(R.id.regAddress);
        Account_phone = findViewById(R.id.regPhone);


    }

    public void SubmitSignupForm(View view){

        if (!validateUsername() | !validatePassword() | !validateNametitle() | !validateFullname() | !validateEmail() | !validateAddress() | !validatePhone()) {
            return;
        }

        selectedNametitle = findViewById(Account_nametitle.getCheckedRadioButtonId());
        String _nameTitle = selectedNametitle.getText().toString();

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        //Add Transition
        Pair[] pairs = new Pair[3];

        pairs[0] = new Pair<View,String>(btnRegSubmit,"transition_regsubmit_btn");
        pairs[1] = new Pair<View,String>(registerTitle,"transition_title_text");
        pairs[2] = new Pair<View,String>(signup_description,"transition_desc_text");

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Signup.this,pairs);
            startActivity(intent,options.toBundle());
        }else{
            startActivity(intent);
        }

    }


    /* Validation Function*/

    private boolean validateUsername(){
        String val = Account_username.getEditText().getText().toString().trim();
        String checkspaces = "(/^\\s+$/)";

        if(val.isEmpty()){
            Account_username.setError("Field can not be empty");
            return false;
        }else if(val.length()>20){
            Account_username.setError("Username is too long!");
            return false;
        }
        else if (val.matches(checkspaces)){
            Account_username.setError("No white spaces are allowed!");
            return false;
        }
        else{
            Account_username.setError(null);
            Account_username.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword(){
        String val = Account_password.getEditText().getText().toString().trim();
        String checkPassword = "^(?=.*\\\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$";
                //"^"+
                //"(?=.*[a-zA-Z])" +              //any letter
                //"(?=\\S+$)" +                   //no white spaces
                //"(.{4,})" +                     //at least 4 characters
                //"$";

        if(val.isEmpty()){
            Account_password.setError("Field can not be empty");
            return false;
        }
        else if (val.matches(checkPassword)){
            Account_password.setError("Password Should contain 4 characters");
            return false;
        }
        else{
            Account_password.setError(null);
            Account_password.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateNametitle(){
        if (Account_nametitle.getCheckedRadioButtonId()==-1){
            Toast.makeText(this,"Please Select Name Title", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }

    private boolean validateFullname(){
        String val = Account_fullname.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            Account_fullname.setError("Field can not be empty");
            return false;
        }
        else{
            Account_fullname.setError(null);
            Account_fullname.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail(){
        String val = Account_email.getEditText().getText().toString().trim();
        String checkEmail = "/^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$/+";

        if(val.isEmpty()){
            Account_email.setError("Field can not be empty");
            return false;
        }
        else if (val.matches(checkEmail)){
            Account_email.setError("Invalid Email!");
            return false;
        }
        else{
            Account_email.setError(null);
            Account_email.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateAddress(){
        String val = Account_address.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            Account_address.setError("Field can not be empty");
            return false;
        }else{
            Account_address.setError(null);
            Account_address.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePhone(){
        String val = Account_phone.getEditText().getText().toString().trim();
        String checkPhone = "(?:\\(0\\)[23478]|\\(?0?[23478]\\)?)\\d{10}";

        if (val.isEmpty()){
            Account_phone.setError("Field can not be empty");
            return false;
        }
        else if(val.matches(checkPhone)){
            Account_phone.setError("Invalid Phone Number!");
            return false;
        }
        else{
            Account_phone.setError(null);
            Account_phone.setErrorEnabled(false);
            return true;
        }

    }






}
