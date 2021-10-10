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

    //获取数据库数据  集合

    //Medication 二级页面 - 展示宠物的疫苗信息.
    val mediList: MutableList<Medication_petMediDetail> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medication_detail)


        val sendId = intent.getStringExtra("petid")
        val sentName = intent.getStringExtra("petname")
        val sendRecord = intent.getStringExtra("recordUN")

        val textView1 = findViewById<View>(R.id.PetTittle) as TextView
        textView1.text = sentName + "'s Medication"

        //select Pet_id,Medi_product, Medi_purchasedate from Medication where Pet_id = '$sendId'
        val sql = "select Pet_id,Medi_product, Medi_purchasedate,Account_username from Medication where Pet_id = '$sendId'";
        //val sql = "select * from testpet"
        //  数据库获取 数据
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
                    var medi = Medication_petMediDetail()
                    //select Pet_id,Medi_product, Medi_purchasedate from Medication where Pet_id = '$sendId'
                    //pet.id = resultSet.getInt("id")
                    medi.id = resultSet.getString("Pet_id")
                    medi.mediproduct = resultSet.getString("Medi_product")
                    medi.medipurchasedate = resultSet.getDate("Medi_purchasedate")
                    medi.accountname = resultSet.getString("Account_username")
                    //pet.sex = resultSet.getString("sex")
                    //pet.age = resultSet.getInt("age")
                    //pet.type = resultSet.getString("type")
                    //放入集合
                    mediList.add(medi)
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