package com.example.demo

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.example.view.SwitchButton
import kotlinx.android.synthetic.main.activity_dss.*
import kotlinx.android.synthetic.main.activity_homepage.*
import kotlinx.android.synthetic.main.activity_homepage.drawerLayout
import kotlinx.android.synthetic.main.activity_homepage.navView
import kotlinx.android.synthetic.main.activity_subscription.*


class Dss : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("1")
        setContentView(R.layout.activity_dss)
        println("2")

        btnBreathing.setOnClickListener {
            var intent: Intent = Intent(this@Dss, QuestionActivity::class.java)
            intent.putExtra("type", "Breathing")
            startActivity(intent)
        }
        btn_Vomiting_Diarrhoea.setOnClickListener {
            var intent: Intent = Intent(this@Dss, QuestionActivity::class.java)
            intent.putExtra("type", "Vomiting")
            startActivity(intent)
        }
        var username = intent.getStringExtra("recordUN")
        println("3 :username = " + username)

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
        } //bugs -- need fo fix ASAP

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}