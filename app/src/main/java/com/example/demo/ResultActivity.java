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
    private String sendRecord;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setNavigationBarColor(getResources().getColor(R.color.purple_500));
            getWindow().setStatusBarColor(getResources().getColor(R.color.purple_500));
        }


        Serializable serializable = getIntent().getSerializableExtra("resultList");
        questionList = (ArrayList<Question>) serializable;  //?????????????????????

        questionType = getIntent().getStringExtra("type");
        sendRecord = getIntent().getStringExtra("recordUN");
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


        cardAdapter.setOnButtonClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(ResultActivity.this, QuestionActivity.class);
                intent.putExtra("position",position);
                intent.putExtra("resultList", questionList);
                intent.putExtra("type", questionType);
                intent.putExtra("recordUN",sendRecord);
                startActivity(intent);
                finish();
            }
        });

        Button btn_startNewCase = findViewById(R.id.btn_startNewCase);
        btn_startNewCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, Dss.class);
                intent.putExtra("recordUN",sendRecord);

                System.out.println("test for start new cae : username : " + sendRecord);
                startActivity(intent);
                finish();
            }
        });


    }


    private void initMenu() {
        DrawerLayout mDrawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);

        Toolbar toolbar = findViewById(R.id.drawer_layout_rl_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.line);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                        intent.putExtra("USERNAME",sendRecord);
                        startActivity(intent);
                        break;

                    case R.id.nav_vaccination:
                        Intent intent1 = new Intent(ResultActivity.this, vaccination.class);
                        intent1.putExtra("recordUN",sendRecord);
                        startActivity(intent1);
                        break;

                    case R.id.nav_medication:
                        Intent intent2 = new Intent(ResultActivity.this, Medication.class);
                        intent2.putExtra("recordUN",sendRecord);
                        startActivity(intent2);
                        break;

                    case R.id.nav_checkup:
                        Intent intent3 = new Intent(ResultActivity.this, CheckUps.class);
                        intent3.putExtra("recordUN",sendRecord);
                        startActivity(intent3);
                        break;

                    case R.id.nav_news:
                        Intent intent4 = new Intent(ResultActivity.this, News.class);
                        intent4.putExtra("recordUN",sendRecord);
                        startActivity(intent4);
                        break;

                    case R.id.nav_parasite:
                        Intent intent5 = new Intent(ResultActivity.this, ParasitePrevention.class);
                        intent5.putExtra("recordUN",sendRecord);
                        startActivity(intent5);
                        break;

                    case R.id.nav_subscr:
                        Intent intent6 = new Intent(ResultActivity.this, Subscription.class);
                        intent6.putExtra("recordUN",sendRecord);
                        startActivity(intent6);
                        break;

                    case R.id.nav_contact:
                        Intent intent7 = new Intent(ResultActivity.this, ContactUs.class);
                        intent7.putExtra("recordUN",sendRecord);
                        startActivity(intent7);
                        break;

                    case R.id.nav_setting:
                        Intent intent8 = new Intent(ResultActivity.this, AccountSetting.class);
                        intent8.putExtra("recordUN",sendRecord);
                        startActivity(intent8);
                        break;

                    case R.id.nav_support:
                        Intent intent9 = new Intent(ResultActivity.this, Support.class);
                        intent9.putExtra("recordUN",sendRecord);
                        startActivity(intent9);
                        break;

                    case  R.id.nav_home:
                        Intent intent10 = new Intent(ResultActivity.this, Homepage.class);
                        intent10.putExtra("recordUN",sendRecord);
                        startActivity(intent10);
                        break;

                    case R.id.nav_logout:
                        Intent intent11 = new Intent(ResultActivity.this,MainActivity.class);
                        intent11.putExtra("recordUN",sendRecord);
                        startActivity(intent11);
                        finish();
                        break;
                }
                return false;
            }
        });
    }
}
