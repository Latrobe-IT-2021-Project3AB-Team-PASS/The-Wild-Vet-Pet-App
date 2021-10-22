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
import kotlinx.android.synthetic.main.activity_medication_detail.lvList


class MedicationDetail : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle

    val mediList: MutableList<Medication_petMediDetail> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medication_detail)


        val sendId = intent.getStringExtra("petid")
        val sentName = intent.getStringExtra("petname")
        val sendRecord = intent.getStringExtra("recordUN")

        val textView1 = findViewById<View>(R.id.PetTittle) as TextView
        textView1.text = sentName + "'s Medication"

        val sql = "select Pet_id,Medi_product, Medi_purchasedate,Account_username from Medication where Pet_id = '$sendId'";

        findPets(sql)
        println("petid=" + sentName)
        println(mediList)

        val MedicationListDetail: MedicationListDetail = MedicationListDetail(this,mediList)
        lvList.setAdapter(MedicationListDetail)


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
                    var medi = Medication_petMediDetail()
                    medi.id = resultSet.getString("Pet_id")
                    medi.mediproduct = resultSet.getString("Medi_product")
                    medi.medipurchasedate = resultSet.getDate("Medi_purchasedate")
                    medi.accountname = resultSet.getString("Account_username")
                    mediList.add(medi)
                }

            }
        }
        t.start()
        t.join()
    }
}