package com.example.demo;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.bean.Question;
import com.google.android.material.navigation.NavigationView;

import net.sourceforge.jtds.jdbc.Support;

import java.io.Serializable;
import java.util.ArrayList;


public class RecommendActivity extends AppCompatActivity {
    private String title,date,time;
    private String recommendId = "";
    private String recommend = "";
    private TextView tvTitle,tv_recommend;
    private Button bt;
    NavigationView navigationView;
    private String questionType = "";
    private ArrayList<Question> questionList = new ArrayList<Question>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setNavigationBarColor(getResources().getColor(R.color.purple_500));
            getWindow().setStatusBarColor(getResources().getColor(R.color.purple_500));
        }

        Intent intent=getIntent();
        recommendId=intent.getStringExtra("recommendId");
        recommend=intent.getStringExtra("recommend");
        //获取Serializable 对象，并强转成 list集合
        Serializable serializable = getIntent().getSerializableExtra("resultList");
        questionList = (ArrayList<Question>) serializable;  //接收的时候强转
        questionType = getIntent().getStringExtra("type");
        initView();
        initMenu();
    }

    private void initView() {
        LinearLayout ll_bg = findViewById(R.id.ll_bg);
        tvTitle=findViewById(R.id.tv_title1);
        tv_recommend=findViewById(R.id.tv_recommend);
//        int size = questionList.size();
//        String id = questionList.get(size-1).getId();
//        Toast.makeText(this,recommendId,Toast.LENGTH_LONG).show();
        if(recommendId.equals("FA1") || recommendId.equals("FA3") || recommendId.equals("FA5")
                || recommendId.equals("FA7")|| recommendId.equals("FA8") || recommendId.equals("FA12")
                || recommendId.equals("FA13")|| recommendId.equals("FA21")|| recommendId.equals("FA23")
                || recommendId.equals("FA61")|| recommendId.equals("FA62")|| recommendId.equals("FA63")
                || recommendId.equals("FA64")|| recommendId.equals("FA69")|| recommendId.equals("FA70")
                || recommendId.equals("FA71")|| recommendId.equals("FA73")|| recommendId.equals("FA75")|| recommendId.equals("FA76")){
            ll_bg.setBackgroundColor(getResources().getColor(R.color.color_red));
        }else if(recommendId.equals("FA4")|| recommendId.equals("FA10")|| recommendId.equals("FA17")
                || recommendId.equals("FA20")|| recommendId.equals("FA22")|| recommendId.equals("FA24")
                || recommendId.equals("FA78")){
            ll_bg.setBackgroundColor(getResources().getColor(R.color.color_role1));
        }else if(recommendId.equals("FA6")|| recommendId.equals("FA11")|| recommendId.equals("FA14")
                || recommendId.equals("FA15")|| recommendId.equals("FA16")|| recommendId.equals("FA18")
                || recommendId.equals("FA19")|| recommendId.equals("FA65")|| recommendId.equals("FA66")
                || recommendId.equals("FA67")|| recommendId.equals("FA68")|| recommendId.equals("FA72")
                || recommendId.equals("FA74")|| recommendId.equals("FA77")){
            ll_bg.setBackgroundColor(getResources().getColor(R.color.ju_huang_se));
        }else if(recommendId.equals("FA9")){
            ll_bg.setBackgroundColor(getResources().getColor(R.color.btn_live_2));
        }
        tvTitle.setText("Recommend");
        tv_recommend.setText(recommend);
        tvTitle.setTextColor(getResources().getColor(R.color.black));
        tv_recommend.setTextColor(getResources().getColor(R.color.black));

        Button btn_startNewCase = findViewById(R.id.btn_startNewCase);
        btn_startNewCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecommendActivity.this, Dss.class);
                startActivity(intent);
                finish();
            }
        });

        Button btn_caseDetails = findViewById(R.id.btn_caseDetails);
        btn_caseDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecommendActivity.this, ResultActivity.class);
                intent.putExtra("resultList", questionList);
                intent.putExtra("type", questionType);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initMenu() {
        DrawerLayout mDrawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
//        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open,R.string.close);
//        mActionBarDrawerToggle.setDrawerIndicatorEnabled(true);
////        mActionBarDrawerToggle.setHomeAsUpIndicator(R.mipmap.ic_launcher);//channge the icon,改变图标
//        mActionBarDrawerToggle.syncState();
//        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);//关联 drawerlayout

        Toolbar toolbar = findViewById(R.id.drawer_layout_rl_toolbar);
        setSupportActionBar(toolbar);                   //传入ToolBar实例
        ActionBar actionBar = getSupportActionBar();    //得到ActionBar实例

        if (actionBar != null){
            //显示导航按钮
            actionBar.setDisplayHomeAsUpEnabled(true);
            //设置导航按钮图片
            actionBar.setHomeAsUpIndicator(R.drawable.line);
        }
        //设置toolbar的导航按钮监听事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //显示侧滑菜单
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
        navigationView = (NavigationView) findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.nav_pets:
                        Intent intent = new Intent(RecommendActivity.this, MyPets.class);
                        startActivity(intent);
                        break;

                    case R.id.nav_vaccination:
                        Intent intent1 = new Intent(RecommendActivity.this, vaccination.class);
                        startActivity(intent1);
                        break;

                    case R.id.nav_medication:
                        Intent intent2 = new Intent(RecommendActivity.this, Medication.class);
                        startActivity(intent2);
                        break;

                    case R.id.nav_checkup:
                        Intent intent3 = new Intent(RecommendActivity.this, CheckUps.class);
                        startActivity(intent3);
                        break;

                    case R.id.nav_news:
                        Intent intent4 = new Intent(RecommendActivity.this, News.class);
                        startActivity(intent4);
                        break;

                    case R.id.nav_parasite:
                        Intent intent5 = new Intent(RecommendActivity.this, ParasitePrevention.class);
                        startActivity(intent5);
                        break;

                    case R.id.nav_subscr:
                        Intent intent6 = new Intent(RecommendActivity.this, Subscription.class);
                        startActivity(intent6);
                        break;

                    case R.id.nav_contact:
                        Intent intent7 = new Intent(RecommendActivity.this, ContactUs.class);
                        startActivity(intent7);
                        break;

                    case R.id.nav_setting:
                        Intent intent8 = new Intent(RecommendActivity.this, AccountSetting.class);
                        startActivity(intent8);
                        break;

                    case R.id.nav_support:
                        Intent intent9 = new Intent(RecommendActivity.this, Support.class);
                        startActivity(intent9);
                        break;

                    case  R.id.nav_home:
                        Intent intent10 = new Intent(RecommendActivity.this, Homepage.class);
                        startActivity(intent10);
                        break;

                    case R.id.nav_logout:
                        Intent intent11 = new Intent(RecommendActivity.this,MainActivity.class);
                        startActivity(intent11);
                        finish();
                        break;
                }
                return false;
            }
        });
    }
}
