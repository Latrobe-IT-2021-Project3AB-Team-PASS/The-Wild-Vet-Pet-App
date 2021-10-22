package com.example.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import android.widget.*
import kotlinx.android.synthetic.main.activity_homepage.drawerLayout
import kotlinx.android.synthetic.main.activity_homepage.navView
import kotlinx.android.synthetic.main.activity_check_ups.lvList

class CheckUps : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle


    val petList: MutableList<CheckUps_pet> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_check_ups)
        val sendRecord = intent.getStringExtra("recordUN")

        //val sql = "select * from pet"
        val sql = "select Pet_id,Pet_image,Pet_name,Account_username from Pet where Account_username = '$sendRecord'";
        //val sql = "select * from testpet"


        findPets(sql)
        println("username=" + sendRecord)
        println(petList)

        val CheckUpsList: CheckUpsList = CheckUpsList(this,petList)
        lvList.setAdapter(CheckUpsList)
        lvList.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(this,"Click item" + position,Toast.LENGTH_SHORT).show()
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

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun findPets(sql: String?) {
        //  开辟子线程 数据查询
        val t: Thread = object : Thread() {
            override fun run() {
                val connection = DBOpenHelper.getConn()
                val preparedStatement = connection!!.prepareStatement(sql)
                val resultSet = preparedStatement.executeQuery()
                while (resultSet.next()) {
                    var pet = CheckUps_pet()
                    pet.id = resultSet.getString("Pet_id")
                    pet.image = resultSet.getString("Pet_image")
                    pet.name = resultSet.getString("Pet_name")
                    pet.accountname = resultSet.getString("Account_username")
                    petList.add(pet)
                }
            }
        }
        t.start()
        t.join()
    }
}