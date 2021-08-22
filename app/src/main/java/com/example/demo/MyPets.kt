package com.example.demo

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
<<<<<<< HEAD
=======
import android.widget.Button
import android.widget.TextView
>>>>>>> pr/3
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_homepage.*

class MyPets : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle

<<<<<<< HEAD
=======
    //Variables



>>>>>>> pr/3
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypets)

<<<<<<< HEAD
=======
        val btnAddPet = findViewById<Button>(R.id.add_pet)
        btnAddPet.setOnClickListener{
            startActivity(Intent(this,AddPet::class.java))
        }

        val petName1 = findViewById<TextView>(R.id.PetName01)
        petName1.setOnClickListener {
            startActivity(Intent(this, PetDetails::class.java))
        }

        val petName2 = findViewById<TextView>(R.id.PetName02)
        petName2.setOnClickListener {
            startActivity(Intent(this, PetDetails::class.java))
        }

        val petName3 = findViewById<TextView>(R.id.PetName03)
        petName2.setOnClickListener {
            startActivity(Intent(this, PetDetails::class.java))
        }

>>>>>>> pr/3
        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.miItem1 -> startActivity(Intent(this, MyPets::class.java))
<<<<<<< HEAD
                R.id.miItem2 -> Toast.makeText(applicationContext,
                    "Click Item 2", Toast.LENGTH_SHORT).show()
                R.id.miItem3 -> Toast.makeText(applicationContext,
                    "Click Item 3", Toast.LENGTH_SHORT).show()
=======
                R.id.miItem2 -> startActivity(Intent(this, vaccination::class.java))
                R.id.miItem3 -> startActivity(Intent(this, Medication::class.java))
                R.id.miItem4 -> startActivity(Intent(this, CheckUps::class.java))
                R.id.miItem5 -> startActivity(Intent(this, News::class.java))
                R.id.miItem6 -> startActivity(Intent(this, ParasitePrevention::class.java))
                R.id.miItem7 -> startActivity(Intent(this, Subscription::class.java))
                R.id.miItem8 -> startActivity(Intent(this, ContactUs::class.java))
                R.id.miItem9 -> startActivity(Intent(this, AccountSetting::class.java))
                R.id.miItem10 -> startActivity(Intent(this, Dss::class.java))
                R.id.miItem11 -> startActivity(Intent(this, Homepage::class.java))
                R.id.miItem12 -> startActivity(Intent(this, MainActivity::class.java))
>>>>>>> pr/3
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

<<<<<<< HEAD
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.searchview, menu);
        return true
    }
=======

>>>>>>> pr/3

}