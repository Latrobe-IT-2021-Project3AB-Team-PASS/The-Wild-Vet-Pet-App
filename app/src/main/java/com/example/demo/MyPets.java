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



        //Button Variable
        btnAddPet = findViewById(R.id.add_pet);
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


        String recordUserName = getIntent().getStringExtra("USERNAME");


        setUpToolbar();
        navigationView = (NavigationView) findViewById(R.id.navigation_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.nav_pets:
                        Intent intent = new Intent(MyPets.this, MyPets.class);
                        intent.putExtra("USERNAME",recordUserName);
                        startActivity(intent);
                        break;

                    case R.id.nav_vaccination:
                        Intent intent1 = new Intent(MyPets.this, vaccination.class);
                        intent1.putExtra("recordUN",recordUserName);
                        startActivity(intent1);
                        break;

                    case R.id.nav_medication:
                        Intent intent2 = new Intent(MyPets.this, Medication.class);
                        intent2.putExtra("recordUN",recordUserName);
                        startActivity(intent2);
                        break;

                    case R.id.nav_checkup:
                        Intent intent3 = new Intent(MyPets.this, CheckUps.class);
                        intent3.putExtra("recordUN",recordUserName);
                        startActivity(intent3);
                        break;

                    case R.id.nav_news:
                        Intent intent4 = new Intent(MyPets.this, News.class);
                        intent4.putExtra("recordUN",recordUserName);
                        startActivity(intent4);
                        break;

                    case R.id.nav_parasite:
                        Intent intent5 = new Intent(MyPets.this, ParasitePrevention.class);
                        intent5.putExtra("recordUN",recordUserName);
                        startActivity(intent5);
                        break;

                    case R.id.nav_subscr:
                        Intent intent6 = new Intent(MyPets.this, Subscription.class);
                        intent6.putExtra("recordUN",recordUserName);
                        startActivity(intent6);
                        break;

                    case R.id.nav_contact:
                        Intent intent7 = new Intent(MyPets.this, ContactUs.class);
                        intent7.putExtra("recordUN",recordUserName);
                        startActivity(intent7);
                        break;

                    case R.id.nav_setting:
                        Intent intent8 = new Intent(MyPets.this, AccountSetting.class);
                        intent8.putExtra("recordUN",recordUserName);
                        startActivity(intent8);
                        break;

                    case R.id.nav_support:
                        Intent intent9 = new Intent(MyPets.this, Support.class);
                        intent9.putExtra("recordUN",recordUserName);
                        startActivity(intent9);
                        break;

                    case  R.id.nav_home:
                        Intent intent10 = new Intent(MyPets.this, Homepage.class);
                        intent10.putExtra("recordUN",recordUserName);
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
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }



    //Retrieve pet name from sql


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

    @Override
    public void onBackPressed() {
        String username = getIntent().getStringExtra("USERNAME");
        Intent back = new Intent(MyPets.this,Homepage.class);
        back.putExtra("USERNAME", username);
        back.putExtra("recordUN",username);
        startActivity(back);
    }

}
