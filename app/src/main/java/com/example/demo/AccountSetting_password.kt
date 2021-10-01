package com.example.demo

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_homepage.drawerLayout
import kotlinx.android.synthetic.main.activity_homepage.navView
import kotlinx.android.synthetic.main.account_setting_password.btnUpdate

class AccountSetting_password : AppCompatActivity()  {

    lateinit var toggle: ActionBarDrawerToggle

    val NewList: MutableList<AccountSetting_List> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?){

        super.onCreate(savedInstanceState)
        setContentView(R.layout.account_setting_password)

        //received id and user's password and save into local variable
        var Id = intent.getStringExtra("recordId")
        var password = intent.getStringExtra("recordPassword")

        //set the user's password into the textview
        val textView1 = findViewById<TextInputLayout>(R.id.tv_Password) as TextView
        textView1.text = password

        //prepare data and update
        //btnUpdate = findViewById<Button>(R.id.btnUpdate)
        //get data from textfield
        val getText = findViewById<TextInputLayout>(R.id.updatePassword)
        val Update_password = getText.getEditText()?.getText();

        //UPDATE Account SET Account_password = '444' WHERE Account_username = 'testuser';
        //val sql = "UPDATE Account SET Account_password = '$Update_password' WHERE Account_username = '$Id'";

        //select Account_password where Account_username = 'testuser';
        //val checkNew = "select Account_password where Account_username = '$Id'";

        btnUpdate.setOnClickListener(View.OnClickListener {
            val sql = "UPDATE Account SET Account_password = '$Update_password' WHERE Account_username = '$Id'";
            val checkNew = "select Account_password from Account where Account_username = '$Id'";

            println("The SQL about to execute : " + sql)
            Update(sql)
            Check(checkNew)

            println("The new password : " + NewList[0].password)
            val textView2 = findViewById<View>(R.id.tv_Password) as TextView
            textView2.text = NewList[0].password
            Toast.makeText(this,"Update success", Toast.LENGTH_LONG).show()
        })


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
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun Update(sql: String?) {
        //  open thread for execute SQL
        val t: Thread = object : Thread() {
            override fun run() {
                // get connection
                val connection = DBOpenHelper.getConn()
                // prepare sql statement
                val preparedStatement = connection!!.prepareStatement(sql)
                // run statement for Update data
                preparedStatement.executeUpdate()

                println(preparedStatement)
                // close connection == wait to be added function
                //DBOpenHelper().closeAll()
            }
        }
        // enable thread
        t.start()
        //enable join method，wait until thread T executed
        t.join()
    }

    fun Check(sql: String?) {
        //  open thread for execute SQL
        val t: Thread = object : Thread() {
            override fun run() {
                // get connection
                val connection = DBOpenHelper.getConn()
                // prepare sql statement
                val preparedStatement = connection!!.prepareStatement(sql)
                // run statement for Update data
                val resultSet = preparedStatement.executeQuery()
                while (resultSet.next()) {
                    var checkNew = AccountSetting_List()
                    //get new data
                    checkNew.password = resultSet.getString("Account_password")
                    //set data into textview
                    NewList.add(checkNew)
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
}