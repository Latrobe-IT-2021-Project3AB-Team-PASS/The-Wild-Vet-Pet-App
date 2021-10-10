package com.example.demo;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import net.sourceforge.jtds.jdbc.Support;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MyPets extends AppCompatActivity {

    private ArrayList<GetListItem> itemArrayList;  //List items Array
    private MyAppAdapter myAppAdapter; //Array Adapter
    private ListView listView; // ListView
    private boolean success = false; // boolean

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;

    Button btnShow,btnAddPet,btnDeletePet;
    TextView PetName01,PetName02,PetName03,PetName04,PetName05,PetName06,
            PetName07,PetName08,PetName09,PetName10,userName,petName;

    private Connection connection = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pets);

        listView = (ListView) findViewById(R.id.petlistview); //ListView Declaration
        itemArrayList = new ArrayList<GetListItem>(); // Arraylist Initialization

        // Calling Async Task
        SyncData orderData = new SyncData();
        orderData.execute("");

        String username = getIntent().getStringExtra("USERNAME");

        //System.out.println("Test: the user name is : " + username);




        //Button Variable
        btnAddPet = findViewById(R.id.add_pet);
        /*btnDeletePet = findViewById(R.id.delete_pet);*/
        /*PetName01 = findViewById(R.id.PetName01);
        PetName02 = findViewById(R.id.PetName02);
        PetName03 = findViewById(R.id.PetName03);
        PetName04 = findViewById(R.id.PetName04);
        PetName05 = findViewById(R.id.PetName05);
        PetName06 = findViewById(R.id.PetName06);
        PetName07 = findViewById(R.id.PetName07);
        PetName08 = findViewById(R.id.PetName08);
        PetName09 = findViewById(R.id.PetName09);
        PetName10 = findViewById(R.id.PetName10);*/
        petName = findViewById(R.id.petName);


        btnAddPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = getIntent().getStringExtra("USERNAME");
                Intent addpet = new Intent(MyPets.this, AddPet.class);
                addpet.putExtra("USERNAME", username);
                startActivity(addpet);
            }
        });



        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        /*String z = "";
        Boolean inSuccess = false; //used to check whether the login fails or not

        try {
            connection = DBOpenHelper.getConn();
            if (connection == null) {
                z = "Check Your Internet Access!";
            } else {
                String sql = "SELECT Pet_name FROM Pet where Account_username = '" + username.toString() + "'";
                *//*String sql = "SELECT Pet_id FROM Pet ORDER BY Pet_id DESC LIMIT 1";*//*
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();
                ArrayList<String> list = new ArrayList<String>();

                while (resultSet.next()) {
                    list.add(resultSet.getString("Pet_name"));
                }
                String[] result = new String[list.size()];
                result = list.toArray(result);


                if (result.length > 0 && result[0] != null) {
                    PetName01.setVisibility(View.VISIBLE);
                    PetName01.setText(result[0]);
                } else {
                    PetName01.setVisibility(View.GONE);
                }
                if (result.length > 1 && result[1] != null) {
                    PetName02.setVisibility(View.VISIBLE);
                    PetName02.setText(result[1]);
                } else {
                    PetName02.setVisibility(View.GONE);
                }
                if (result.length > 2 && result[2] != null) {
                    PetName03.setVisibility(View.VISIBLE);
                    PetName03.setText(result[2]);
                } else {
                    PetName03.setVisibility(View.GONE);
                }
                if (result.length > 3 && result[3] != null) {
                    PetName04.setVisibility(View.VISIBLE);
                    PetName04.setText(result[3]);
                } else {
                    PetName04.setVisibility(View.GONE);
                }
                if (result.length > 4 && result[4] != null) {
                    PetName05.setVisibility(View.VISIBLE);
                    PetName05.setText(result[4]);
                } else {
                    PetName05.setVisibility(View.GONE);
                }
                if (result.length > 5 && result[5] != null) {
                    PetName06.setVisibility(View.VISIBLE);
                    PetName06.setText(result[5]);
                } else {
                    PetName06.setVisibility(View.GONE);
                }
                if (result.length > 6 && result[6] != null) {
                    PetName07.setVisibility(View.VISIBLE);
                    PetName07.setText(result[6]);
                } else {
                    PetName07.setVisibility(View.GONE);
                }
                if (result.length > 7 && result[7] != null) {
                    PetName08.setVisibility(View.VISIBLE);
                    PetName08.setText(result[7]);
                } else {
                    PetName08.setVisibility(View.GONE);
                }
                if (result.length > 8 && result[8] != null) {
                    PetName09.setVisibility(View.VISIBLE);
                    PetName09.setText(result[8]);
                } else {
                    PetName09.setVisibility(View.GONE);
                }
                if (result.length > 9 && result[9] != null) {
                    PetName10.setVisibility(View.VISIBLE);
                    PetName10.setText(result[9]);
                } else {
                    PetName10.setVisibility(View.GONE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }*/



        //setUpToolbar();
        navigationView = (NavigationView) findViewById(R.id.navigation_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.nav_pets:
                        Intent intent = new Intent(MyPets.this, MyPets.class);
                        startActivity(intent);
                        break;

                    case R.id.nav_vaccination:
                        Intent intent1 = new Intent(MyPets.this, vaccination.class);
                        startActivity(intent1);
                        break;

                    case R.id.nav_medication:
                        Intent intent2 = new Intent(MyPets.this, Medication.class);
                        startActivity(intent2);
                        break;

                    case R.id.nav_checkup:
                        Intent intent3 = new Intent(MyPets.this, CheckUps.class);
                        startActivity(intent3);
                        break;

                    case R.id.nav_news:
                        Intent intent4 = new Intent(MyPets.this, News.class);
                        startActivity(intent4);
                        break;

                    case R.id.nav_parasite:
                        Intent intent5 = new Intent(MyPets.this, ParasitePrevention.class);
                        startActivity(intent5);
                        break;

                    case R.id.nav_subscr:
                        Intent intent6 = new Intent(MyPets.this, Subscription.class);
                        startActivity(intent6);
                        break;

                    case R.id.nav_contact:
                        Intent intent7 = new Intent(MyPets.this, ContactUs.class);
                        startActivity(intent7);
                        break;

                    case R.id.nav_setting:
                        Intent intent8 = new Intent(MyPets.this, AccountSetting.class);
                        startActivity(intent8);
                        break;

                    case R.id.nav_support:
                        Intent intent9 = new Intent(MyPets.this, Support.class);
                        startActivity(intent9);
                        break;

                    case  R.id.nav_home:
                        Intent intent10 = new Intent(MyPets.this, Homepage.class);
                        startActivity(intent10);
                        break;

                    case R.id.nav_logout:
                        Intent intent11 = new Intent(MyPets.this,MainActivity.class);
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
        //setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }



    //Retrieve pet name from sql
    /*public void GetPetData(View view) {
        try {
            connection = DBOpenHelper.getConn();
            if (connection == null) {
                z = "Check Your Internet Access!";
            } else {
                String sql = "Select Pet_name From Pet";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    PetName01.setText(resultSet.getString(1));

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

    //go to pet details
    public void GotoPetDetail(View view){
        String petname=((TextView)view).getText().toString();
        /*String petname = petName.getText().toString();*/
        String username = getIntent().getStringExtra("USERNAME");
        Intent PetDetail = new Intent(MyPets.this,PetDetails.class);
        PetDetail.putExtra("PETNAME",petname);
        PetDetail.putExtra("USERNAME",username);
        startActivity(PetDetail);
    }


    /*public void GotoPetDetail01(View view){
        String petname = PetName01.getText().toString();
        String username = getIntent().getStringExtra("USERNAME");
        Intent PetDetail = new Intent(MyPets.this,PetDetails.class);
        PetDetail.putExtra("PETNAME",petname);
        PetDetail.putExtra("USERNAME",username);
        startActivity(PetDetail);
    }

    public void GotoPetDetail02(View view){
        String petname = PetName02.getText().toString();
        String username = getIntent().getStringExtra("USERNAME");
        Intent PetDetail = new Intent(MyPets.this,PetDetails.class);
        PetDetail.putExtra("PETNAME",petname);
        PetDetail.putExtra("USERNAME",username);
        startActivity(PetDetail);
    }

    public void GotoPetDetail03(View view){
        String petname = PetName03.getText().toString();
        String username = getIntent().getStringExtra("USERNAME");
        Intent PetDetail = new Intent(MyPets.this,PetDetails.class);
        PetDetail.putExtra("PETNAME",petname);
        PetDetail.putExtra("USERNAME",username);
        startActivity(PetDetail);
    }

    public void GotoPetDetail04(View view){
        String petname = PetName04.getText().toString();
        String username = getIntent().getStringExtra("USERNAME");
        Intent PetDetail = new Intent(MyPets.this,PetDetails.class);
        PetDetail.putExtra("PETNAME",petname);
        PetDetail.putExtra("USERNAME",username);
        startActivity(PetDetail);
    }

    public void GotoPetDetail05(View view){
        String petname = PetName05.getText().toString();
        String username = getIntent().getStringExtra("USERNAME");
        Intent PetDetail = new Intent(MyPets.this,PetDetails.class);
        PetDetail.putExtra("PETNAME",petname);
        PetDetail.putExtra("USERNAME",username);
        startActivity(PetDetail);
    }

    public void GotoPetDetail06(View view){
        String petname = PetName06.getText().toString();
        String username = getIntent().getStringExtra("USERNAME");
        Intent PetDetail = new Intent(MyPets.this,PetDetails.class);
        PetDetail.putExtra("PETNAME",petname);
        PetDetail.putExtra("USERNAME",username);
        startActivity(PetDetail);
    }

    public void GotoPetDetail07(View view){
        String petname = PetName07.getText().toString();
        String username = getIntent().getStringExtra("USERNAME");
        Intent PetDetail = new Intent(MyPets.this,PetDetails.class);
        PetDetail.putExtra("PETNAME",petname);
        PetDetail.putExtra("USERNAME",username);
        startActivity(PetDetail);
    }

    public void GotoPetDetail08(View view){
        String petname = PetName08.getText().toString();
        String username = getIntent().getStringExtra("USERNAME");
        Intent PetDetail = new Intent(MyPets.this,PetDetails.class);
        PetDetail.putExtra("PETNAME",petname);
        PetDetail.putExtra("USERNAME",username);
        startActivity(PetDetail);
    }

    public void GotoPetDetail09(View view){
        String petname = PetName09.getText().toString();
        String username = getIntent().getStringExtra("USERNAME");
        Intent PetDetail = new Intent(MyPets.this,PetDetails.class);
        PetDetail.putExtra("PETNAME",petname);
        PetDetail.putExtra("USERNAME",username);
        startActivity(PetDetail);
    }

    public void GotoPetDetail10(View view){
        String petname = PetName10.getText().toString();
        String username = getIntent().getStringExtra("USERNAME");
        Intent PetDetail = new Intent(MyPets.this,PetDetails.class);
        PetDetail.putExtra("PETNAME",petname);
        PetDetail.putExtra("USERNAME",username);
        startActivity(PetDetail);
    }*/

    // Async Task has three overrided methods,
    private class SyncData extends AsyncTask<String, String, String> {
        String msg = "Internet/DB_Credentials/Windows_FireWall_TurnOn Error, See Android Monitor in the bottom For details!";
        ProgressDialog progress;

        @Override
        protected void onPreExecute() //Starts the progress dailog
        {
            progress = ProgressDialog.show(MyPets.this, "Synchronising",
                    "ListView Loading! Please Wait...", true);
        }

        @Override
        protected String doInBackground(String... strings)  // Connect to the database, write query and add items to array list
        {
            String username = getIntent().getStringExtra("USERNAME");
            //System.out.println("Test: the user name is : " + username);

            try {
                connection = DBOpenHelper.getConn();
                if (connection == null) {
                    success = false;
                } else {
                    // Change below query according to your own database.
                    String query = "SELECT Pet_name FROM Pet WHERE Account_username = '"+ username.toString() +"'";
                    Statement stmt = connection.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs != null) // if resultset not null, I add items to itemArraylist using class created
                    {
                        while (rs.next()) {
                            try {
                                itemArrayList.add(new GetListItem(rs.getString("Pet_name")));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                        msg = "Found";
                        success = true;
                    } else {
                        msg = "No Data found!";
                        success = false;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Writer writer = new StringWriter();
                e.printStackTrace(new PrintWriter(writer));
                msg = writer.toString();
                success = false;
            }
            return msg;
        }

        @Override
        protected void onPostExecute(String msg) // disimissing progress dialoge, showing error and setting up my ListView
        {
            progress.dismiss();
            Toast.makeText(MyPets.this, msg + "", Toast.LENGTH_LONG).show();
            if (success == false) {
            } else {
                try {
                    myAppAdapter = new MyAppAdapter(itemArrayList, MyPets.this);
                    listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    listView.setAdapter(myAppAdapter);
                } catch (Exception ex) {

                }

            }
        }
    }

    public class MyAppAdapter extends BaseAdapter         //has a class viewholder which holds
    {
        public class ViewHolder {
            TextView textName;
        }

        public List<GetListItem> parkingList;

        public Context context;
        ArrayList<GetListItem> arraylist;

        private MyAppAdapter(List<GetListItem> apps, Context context) {
            this.parkingList = apps;
            this.context = context;
            arraylist = new ArrayList<GetListItem>();
            arraylist.addAll(parkingList);
        }

        @Override
        public int getCount() {
            return parkingList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) // inflating the layout and initializing widgets
        {

            View rowView = convertView;
            ViewHolder viewHolder = null;
            if (rowView == null) {
                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.petlist_layout, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.textName = (TextView) rowView.findViewById(R.id.petName);
                rowView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            // here setting up names and images
            viewHolder.textName.setText(parkingList.get(position).getName() + "");

            return rowView;
        }
    }
}
