package com.example.demo

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_account_setting.*
import kotlinx.android.synthetic.main.activity_homepage.drawerLayout
import kotlinx.android.synthetic.main.activity_homepage.navView


class AccountSetting : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle

    //AccountSetting main page - for show the user account detail
    val AccountList: MutableList<AccountSetting_List> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_setting)

        var username = intent.getStringExtra("recordUN")
        val sendRecord = intent.getStringExtra("recordUN")

        val sql = "select Account_username,Account_password,Account_nameTitle,Account_fullname,Account_email,Account_address,Account_phone from Account where Account_username = '$username'";
        findAccount(sql)
        val checkNew = "select Account_fullname where Account_username = '$username'";
        println(AccountList) //print out for check the detail
        println(checkNew)

        val textView1 = findViewById<View>(R.id.tv_UserName) as TextView
        val textView2 = findViewById<View>(R.id.tv_email) as TextView
        val textView3 = findViewById<View>(R.id.tv_Phone) as TextView
        val textView4 = findViewById<View>(R.id.tv_address) as TextView

        textView1.text = AccountList[0].fullname
        textView2.text = AccountList[0].email
        textView3.text = AccountList[0].phone
        textView4.text = AccountList[0].address

        //for user name
        UserName.setOnClickListener {

            //sent ID and username into intent
            val sendUN = username
            val sentName = AccountList[0].fullname
            val intent = Intent(this, AccountSetting_fullname::class.java)
            intent.putExtra("recordId",sendUN)
            intent.putExtra("recordName",sentName)
            intent.putExtra("recordUN",sendRecord)
            startActivity(intent)
            //startActivity(Intent(this, vaccination::class.java))  //old
        }

        // for email address
        Email.setOnClickListener {

            //sent ID and email into intent
            val sendUN = username
            val sentEmail = AccountList[0].email
            val intent = Intent(this, AccountSetting_email::class.java)
            intent.putExtra("recordId",sendUN)
            intent.putExtra("recordEmail",sentEmail)
            intent.putExtra("recordUN",sendRecord)
            startActivity(intent)
            //startActivity(Intent(this, vaccination::class.java))  //old
        }

        //for phone number
        Phone.setOnClickListener {

            //sent ID and phone number into intent
            val sendUN = username
            val sentPhone = AccountList[0].phone
            val intent = Intent(this, AccountSetting_phone::class.java)
            intent.putExtra("recordId",sendUN)
            intent.putExtra("recordPhone",sentPhone)
            intent.putExtra("recordUN",sendRecord)
            startActivity(intent)
            //startActivity(Intent(this, vaccination::class.java))  //old
        }

        //for address
        Address.setOnClickListener {

            //sent ID and address into intent
            val sendUN = username
            val sentAddress = AccountList[0].address
            val intent = Intent(this, AccountSetting_address::class.java)
            intent.putExtra("recordId",sendUN)
            intent.putExtra("recordAddress",sentAddress)
            intent.putExtra("recordUN",sendRecord)
            startActivity(intent)
            //startActivity(Intent(this, vaccination::class.java))  //old
        }

        //for password
        Password.setOnClickListener {

            //sent ID and password into intent
            val sendUN = username
            val sentPassword = AccountList[0].password
            val intent = Intent(this, AccountSetting_password::class.java)
            intent.putExtra("recordId",sendUN)
            intent.putExtra("recordPassword",sentPassword)
            intent.putExtra("recordUN",sendRecord)
            startActivity(intent)
            //startActivity(Intent(this, vaccination::class.java))  //old
        }


        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when(it.itemId){

                R.id.miItem1 -> {
                    val sendRecord = intent.getStringExtra("recordUN")
                    val intent = Intent(this, MyPets::class.java)
                    intent.putExtra("USERNAME",sendRecord)
                    startActivity(intent)
                }
                R.id.miItem2 -> {
                    val sendRecord = intent.getStringExtra("recordUN")
                    val intent = Intent(this, vaccination::class.java)
                    intent.putExtra("recordUN",sendRecord)
                    startActivity(intent)
                }
                R.id.miItem3 -> {
                    val sendRecord = intent.getStringExtra("recordUN")
                    val intent = Intent(this, Medication::class.java)
                    intent.putExtra("recordUN",sendRecord)
                    startActivity(intent)
                }

                R.id.miItem4 -> {
                    val sendRecord = intent.getStringExtra("recordUN")
                    val intent = Intent(this, CheckUps::class.java)
                    intent.putExtra("recordUN",sendRecord)
                    startActivity(intent)
                }
                R.id.miItem5 -> startActivity(Intent(this, News::class.java))
                R.id.miItem6 -> {
                    val sendRecord = intent.getStringExtra("recordUN")
                    val intent = Intent(this, ParasitePrevention::class.java)
                    intent.putExtra("recordUN",sendRecord)
                    startActivity(intent)
                }
                R.id.miItem7 -> startActivity(Intent(this, Subscription::class.java))
                R.id.miItem8 -> startActivity(Intent(this, ContactUs::class.java))
                R.id.miItem9 -> {
                    val sendRecord = intent.getStringExtra("recordUN")
                    val intent = Intent(this, AccountSetting::class.java)
                    intent.putExtra("recordUN",sendRecord)
                    startActivity(intent)
                }
                R.id.miItem10 -> startActivity(Intent(this, Dss::class.java))
                R.id.miItem11 -> {
                    val sendRecord = intent.getStringExtra("recordUN")
                    val intent = Intent(this, Homepage::class.java)
                    intent.putExtra("recordUN",sendRecord)
                    startActivity(intent)
                }
                R.id.miItem12 -> startActivity(Intent(this, MainActivity::class.java))
            }
            true
        }

        //Toast - for show the message in the bottom
        /*
        UserName.setOnClickListener {
            Toast.makeText(this,"UserName",Toast.LENGTH_LONG).show()
        }
        Email.setOnClickListener {
            Toast.makeText(this,"Email",Toast.LENGTH_LONG).show()
        }
        Phone.setOnClickListener {
            Toast.makeText(this,"Phone",Toast.LENGTH_LONG).show()
        }
        Password.setOnClickListener {
            Toast.makeText(this,"Password",Toast.LENGTH_LONG).show()
        }*/
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun findAccount(sql: String?) {
        //  open thread for execute SQL
        val t: Thread = object : Thread() {
            override fun run() {
                // get connection
                val connection = DBOpenHelper.getConn()
                // prepare sql statement
                val preparedStatement = connection!!.prepareStatement(sql)
                // run statement for get data
                val resultSet = preparedStatement.executeQuery()
                while (resultSet.next()) {
                    var account = AccountSetting_List()
                    //select Account_username,Account_password,Account_nameTitle,Account_fullname,Account_email,Account_address,Account_phone from Account where Account_username = 'username';
                    //tv_UserName

                    //val textView1 = findViewById<View>(R.id.tv_UserName) as TextView
                    //textView1.text = resultSet.getString("Account_username")
                    account.username = resultSet.getString("Account_username")
                    account.password = resultSet.getString("Account_password")
                    account.nametitle = resultSet.getString("Account_nameTitle")
                    account.fullname = resultSet.getString("Account_fullname")
                    account.email = resultSet.getString("Account_email")
                    account.address = resultSet.getString("Account_address")
                    account.phone = resultSet.getString("Account_phone")
                    AccountList.add(account)

                }
                // close connection == wait to be added function
                //DBOpenHelper().closeAll()
            }
        }
        // enable thread
        t.start()
        //enable join method，wait until thread T executed
        t.join()
    }

    override fun onBackPressed() {
        val sendRecord = intent.getStringExtra("recordUN")
        val intent = Intent(this, Homepage::class.java)
        intent.putExtra("recordUN",sendRecord)
        intent.putExtra("USERNAME",sendRecord)
        startActivity(intent)
        //println("starting backpress and the user name is " + sendRecord)
        //overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
        //overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
}