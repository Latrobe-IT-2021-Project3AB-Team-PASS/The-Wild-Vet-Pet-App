package com.example.demo;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adapter.CardAdapter;
import com.example.bean.Question;
import com.example.dummy.CardDummyContent;
import com.example.dummy.OnItemClickListener;
import com.google.android.material.navigation.NavigationView;

import net.sourceforge.jtds.jdbc.Support;

import java.io.Serializable;
import java.util.ArrayList;


public class ResultActivity extends AppCompatActivity {
    private int mColumnCount = 1;
    private String title,date,time;
    //    private int score;
    private TextView tvTitle,tvScore,tvDate,tvTime;
    private Button bt;
    private CardAdapter cardAdapter = null;
    private RecyclerView recyclerView = null;
    NavigationView navigationView;
    private String questionType = "";
    private ArrayList<Question> questionList = new ArrayList<Question>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setNavigationBarColor(getResources().getColor(R.color.purple_500));
            getWindow().setStatusBarColor(getResources().getColor(R.color.purple_500));
        }

        //获取Serializable 对象，并强转成 list集合
        Serializable serializable = getIntent().getSerializableExtra("resultList");
        questionList = (ArrayList<Question>) serializable;  //接收的时候强转

        questionType = getIntent().getStringExtra("type");

        initView();
        initMenu();
    }

    private void initView() {
        tvTitle=findViewById(R.id.tv_title1);
        tvTitle.setText("Case detail");

        String result = "";
        int size = questionList.size();
        CardDummyContent.ITEMS.clear();
        for(int i= 0; i < size; i++){
            String id = questionList.get(i).getId();
            int answerNum = questionList.get(i).getAnswerNum();
            String title = questionList.get(i).getTitle();
            String choiceA = questionList.get(i).getChoiceA();
            String choiceB = questionList.get(i).getChoiceB();
            String choiceC = questionList.get(i).getChoiceC();
            String choiceD = questionList.get(i).getChoiceD();
            String choiceE = questionList.get(i).getChoiceE();
            String choiceF = questionList.get(i).getChoiceF();
            String answer = questionList.get(i).getAnswer();
            String preQuestion = questionList.get(i).getPreQuestion();
            String NextQuestion_A = questionList.get(i).getNextQuestion_A();
            String NextQuestion_B = questionList.get(i).getNextQuestion_B();
            String NextQuestion_C = questionList.get(i).getNextQuestion_C();
            String NextQuestion_D = questionList.get(i).getNextQuestion_D();
            String NextQuestion_E = questionList.get(i).getNextQuestion_E();
            String NextQuestion_F = questionList.get(i).getNextQuestion_F();
            CardDummyContent.addItem(CardDummyContent.createDummyItem(id,answerNum, title,
                    choiceA, choiceB, choiceC,choiceD, choiceE, choiceF,answer,
                    preQuestion,NextQuestion_A,NextQuestion_B,NextQuestion_C,
                    NextQuestion_D,NextQuestion_E,NextQuestion_F));
        }
        cardAdapter = new CardAdapter(CardDummyContent.ITEMS, this);
        recyclerView = (RecyclerView) findViewById(R.id.list);
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, mColumnCount));
        }
        recyclerView.setAdapter(cardAdapter);
        cardAdapter.notifyDataSetChanged();

//        cardAdapter.setOnItemClickListener(new OnItemClickListener() {
//            public void onItemClick(View view, int position) {
//                Intent intent = new Intent(ResultActivity.this, QuestionActivity.class);
//                intent.putExtra("position",position);
//                startActivity(intent);
//                finish();
//            }
//        });

        cardAdapter.setOnButtonClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(ResultActivity.this, QuestionActivity.class);
                intent.putExtra("position",position);
                intent.putExtra("resultList", questionList);
                intent.putExtra("type", questionType);
                startActivity(intent);
                finish();
            }
        });

        Button btn_startNewCase = findViewById(R.id.btn_startNewCase);
        btn_startNewCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, Dss.class);
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
                        Intent intent = new Intent(ResultActivity.this, MyPets.class);
                        startActivity(intent);
                        break;

                    case R.id.nav_vaccination:
                        Intent intent1 = new Intent(ResultActivity.this, vaccination.class);
                        startActivity(intent1);
                        break;

                    case R.id.nav_medication:
                        Intent intent2 = new Intent(ResultActivity.this, Medication.class);
                        startActivity(intent2);
                        break;

                    case R.id.nav_checkup:
                        Intent intent3 = new Intent(ResultActivity.this, CheckUps.class);
                        startActivity(intent3);
                        break;

                    case R.id.nav_news:
                        Intent intent4 = new Intent(ResultActivity.this, News.class);
                        startActivity(intent4);
                        break;

                    case R.id.nav_parasite:
                        Intent intent5 = new Intent(ResultActivity.this, ParasitePrevention.class);
                        startActivity(intent5);
                        break;

                    case R.id.nav_subscr:
                        Intent intent6 = new Intent(ResultActivity.this, Subscription.class);
                        startActivity(intent6);
                        break;

                    case R.id.nav_contact:
                        Intent intent7 = new Intent(ResultActivity.this, ContactUs.class);
                        startActivity(intent7);
                        break;

                    case R.id.nav_setting:
                        Intent intent8 = new Intent(ResultActivity.this, AccountSetting.class);
                        startActivity(intent8);
                        break;

                    case R.id.nav_support:
                        Intent intent9 = new Intent(ResultActivity.this, Support.class);
                        startActivity(intent9);
                        break;

                    case  R.id.nav_home:
                        Intent intent10 = new Intent(ResultActivity.this, Homepage.class);
                        startActivity(intent10);
                        break;

                    case R.id.nav_logout:
                        Intent intent11 = new Intent(ResultActivity.this,MainActivity.class);
                        startActivity(intent11);
                        finish();
                        break;
                }
                return false;
            }
        });
    }
}
