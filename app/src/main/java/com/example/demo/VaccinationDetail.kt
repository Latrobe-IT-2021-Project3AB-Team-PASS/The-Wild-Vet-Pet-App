package com.example.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_homepage.drawerLayout
import kotlinx.android.synthetic.main.activity_homepage.navView
import kotlinx.android.synthetic.main.activity_vaccination_detail.lvList

class VaccinationDetail : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    val petList: MutableList<Vaccination_petsVaccDetail> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_vaccination_detail)
        val sentid = intent.getStringExtra("petid")
        val sendRecord = intent.getStringExtra("recordUN")
        val sentName = intent.getStringExtra("name")

        val textView1 = findViewById<View>(R.id.PetTittle) as TextView
        textView1.text = sentName + "'s Vaccination"

        val sql = "select Pet_id,Vacc_type,Vacc_date,Vacc_productname,Vacc_name,Vacc_dueday,Account_username from Vaccination where Pet_id = '$sentid'";

        findPets(sql)
        println("petid=" + sentid)
        println(petList)

        val VaccinationListDetail: VaccinationListDetail = VaccinationListDetail(this,petList)
        lvList.setAdapter(VaccinationListDetail)
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
        val t: Thread = object : Thread() {
            override fun run() {
                val connection = DBOpenHelper.getConn()
                val preparedStatement = connection!!.prepareStatement(sql)
                val resultSet = preparedStatement.executeQuery()
                while (resultSet.next()) {
                    var pet = Vaccination_petsVaccDetail()
                    pet.id = resultSet.getString("Pet_id")
                    pet.vacctype = resultSet.getString("Vacc_type")
                    pet.vaccdate = resultSet.getDate("Vacc_date")
                    pet.vaccproductname = resultSet.getString("Vacc_productname")
                    pet.vaccname = resultSet.getString("Vacc_name")
                    pet.vaccdueday = resultSet.getDate("Vacc_dueday")
                    pet.accountname = resultSet.getString("Account_username")
                    petList.add(pet)
                }
            }
        }
        t.start()
        t.join()
    }
}