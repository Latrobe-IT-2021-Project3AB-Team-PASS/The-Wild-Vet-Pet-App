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
import kotlinx.android.synthetic.main.activity_check_ups_detail.lvList

class CheckUpsDetail : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle

    //获取数据库数据  集合

    //vaccination 二级页面 - 展示宠物的疫苗信息.
    val ChecList: MutableList<CheckUps_petCheckUpDetail> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //跳转页面
        setContentView(R.layout.activity_check_ups_detail)

        val sendRecord = intent.getStringExtra("petid")
        val sentName = intent.getStringExtra("name")

        val textView1 = findViewById<View>(R.id.PetTittle) as TextView
        textView1.text = sentName + "'s Check-ups"

        //val sql = "select * from pet"
        //val sql = "select Pet_id,Pet_image,Pet_name from Pet where Account_username = '$sendRecord'";
        val sql = "select Pet_id,Vet_fullname,Checkups_type,Checkups_date,Checkups_notes from Checkups where Pet_id = '$sendRecord'";
        //val sql = "select * from testpet"
        //  数据库获取 数据
        findPets(sql)
        println("petid=" + sendRecord)
        println(ChecList)


        val CheckUpsListDetail: CheckUpsListDetail = CheckUpsListDetail(this,ChecList)
        lvList.setAdapter(CheckUpsListDetail)
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
                // 获取 连接
                val connection = DBOpenHelper.getConn()
                // 预 执行 sql
                val preparedStatement = connection!!.prepareStatement(sql)
                // 执行 查询 获取 数据集合
                val resultSet = preparedStatement.executeQuery()
                while (resultSet.next()) {
                    var checku = CheckUps_petCheckUpDetail()
                    //"Pet_id,Vet_fullname,Checkups_type,Checkups_date,Checkups_notes from Checkups where Pet_id = '$sendRecord'";
                    checku.id = resultSet.getString("Pet_id")
                    checku.vetfullname = resultSet.getString("Vet_fullname")
                    checku.checkupstype = resultSet.getString("Checkups_type")
                    checku.checkupsdate = resultSet.getDate("Checkups_date")
                    checku.checkupsnotes = resultSet.getString("Checkups_notes")
                    //pet.sex = resultSet.getString("sex")
                    //pet.age = resultSet.getInt("age")
                    //pet.type = resultSet.getString("type")
                    //放入集合
                    ChecList.add(checku)
                }
                // 关闭连接
                //DBOpenHelper().closeAll()
            }
        }
        // 启动线程
        t.start()
        //调用join方法，等待线程t执行完毕
        t.join()
    }
}