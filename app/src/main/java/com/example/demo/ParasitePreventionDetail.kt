package com.example.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.View
import kotlinx.android.synthetic.main.activity_homepage.drawerLayout
import kotlinx.android.synthetic.main.activity_homepage.navView
import kotlinx.android.synthetic.main.activity_parasite_prevention_detail.lvList

class ParasitePreventionDetail : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle

    val PPList: MutableList<ParasitePrevention_petPPDetail> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parasite_prevention_detail)

        val sendId = intent.getStringExtra("petid")
        val sentName = intent.getStringExtra("petname")
        val sendRecord = intent.getStringExtra("recordUN")

        val textView1 = findViewById<View>(R.id.PetTittle) as TextView
        textView1.text = sentName + "'s Parasite Prevention"

        val sql = "select Pet_id,PP_date,PP_product,PP_fuequency,Account_username from ParasitePrevention where Pet_id = '$sendId'";

        findPets(sql)
        println("petid=" + sentName)
        println(PPList)

        val ParasitePreventionListDetail: ParasitePreventionListDetail = ParasitePreventionListDetail(this,PPList)
        lvList.setAdapter(ParasitePreventionListDetail)

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
        val t: Thread = object : Thread() {
            override fun run() {
                val connection = DBOpenHelper.getConn()
                val preparedStatement = connection!!.prepareStatement(sql)
                val resultSet = preparedStatement.executeQuery()
                while (resultSet.next()) {
                    var PP = ParasitePrevention_petPPDetail()
                    PP.id = resultSet.getString("Pet_id")
                    PP.ppdate = resultSet.getDate("PP_date")
                    PP.ppproduct = resultSet.getString("PP_product")
                    PP.ppfuequency = resultSet.getString("PP_fuequency")
                    PP.accountname = resultSet.getString("Account_username")
                    PPList.add(PP)
                }
            }
        }
        t.start()
        t.join()
    }
}