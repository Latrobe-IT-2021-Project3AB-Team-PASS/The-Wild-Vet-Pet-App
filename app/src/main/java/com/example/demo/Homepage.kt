package com.example.demo

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_homepage.*
import androidx.annotation.NonNull
import com.google.android.material.navigation.NavigationView
import android.view.View
import android.widget.Button
import androidx.appcompat.widget.Toolbar


class Homepage : AppCompatActivity(){

    lateinit var toggle: ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)
        val testRecord = findViewById<TextView>(R.id.search_field);
        testRecord.setText("Welcome to The Wild Vet, " + intent.getStringExtra("recordUN"));


        btnMyPets.setOnClickListener {
            val sendRecord = intent.getStringExtra("recordUN")
            val intent = Intent(this, MyPets::class.java)
            intent.putExtra("USERNAME",sendRecord)
            startActivity(intent)
        }

        btnVaccination.setOnClickListener {

            val sendRecord = intent.getStringExtra("recordUN")
            val intent = Intent(this, vaccination::class.java)
            intent.putExtra("recordUN",sendRecord)
            startActivity(intent)


        }

        btnMedication.setOnClickListener {
            val sendRecord = intent.getStringExtra("recordUN")
            val intent = Intent(this, Medication::class.java)
            intent.putExtra("recordUN",sendRecord)
            startActivity(intent)

        }

        btnParasite.setOnClickListener {
            val sendRecord = intent.getStringExtra("recordUN")
            val intent = Intent(this, ParasitePrevention::class.java)
            intent.putExtra("recordUN",sendRecord)
            startActivity(intent)

        }

        btnCheckUps.setOnClickListener {
            val sendRecord = intent.getStringExtra("recordUN")
            val intent = Intent(this, CheckUps::class.java)
            intent.putExtra("recordUN",sendRecord)
            startActivity(intent)

        }

        btnNews.setOnClickListener {
            startActivity(Intent(this, News::class.java))
        }

        btnSubscrip.setOnClickListener {
            startActivity(Intent(this, Subscription::class.java))
        }

        btnContactUs.setOnClickListener {
            startActivity(Intent(this, ContactUs::class.java))
        }

        btnAccountSetting.setOnClickListener {

            val sendRecord = intent.getStringExtra("recordUN")
            val intent = Intent(this, AccountSetting::class.java)
            intent.putExtra("recordUN",sendRecord)
            startActivity(intent)

        }

        btnSupport.setOnClickListener {
            val sendRecord = intent.getStringExtra("recordUN")
            val intent = Intent(this, Dss::class.java)
            intent.putExtra("recordUN",sendRecord)
            startActivity(intent)

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

    override fun onBackPressed() {
        val back = Intent(this@Homepage, MainActivity::class.java)
        startActivity(back)
        finish()
    }

}


