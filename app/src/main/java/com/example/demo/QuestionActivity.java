package com.example.demo;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterViewFlipper;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import com.example.bean.Question;
import com.example.demo.db.QuestionDBHelper;
import com.google.android.material.navigation.NavigationView;

import net.sourceforge.jtds.jdbc.Support;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class QuestionActivity extends AppCompatActivity implements View.OnClickListener {
    private int tab;
    private String table,content;
    private TextView tvTitle, tvScore;
    private Chronometer chronometer;
    private Cursor cursor;
    private boolean isCollect=false,isFirst=false,isLast= false;
    private int num,answerNum;
    private int score = 0,index=0;
    public static List<String> anList;
    private String source;
    private String recommendId;
    private String recommend;
    private String qid, type, que, A, B, C, D,E,F, answer, detail;
    private Button btnPre;
    private Button btnNext;
    private AdapterViewFlipper vf;
    private BaseAdapter adapter;
    private ProgressBar pb;
    private View root;
    private TextView tvQue, tvDetail, tvAns, tvYou;
    private CheckBox cb1, cb2, cb3, cb4, cb5, cb6;
    private ImageView imgCollect,imgCard;
    private int aaaa = 0;
    private String questionType = "";
    private int editPos = -1;

    private ArrayList<Question> questionList = new ArrayList<Question>();
    private ArrayList<Question> answerList = new ArrayList<Question>();

    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mActionBarDrawerToggle;
    NavigationView navigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setNavigationBarColor(getResources().getColor(R.color.purple_500));
            getWindow().setStatusBarColor(getResources().getColor(R.color.purple_500));
        }

        initData();
        initView();
        initMenu();
    }

    private void initMenu() {

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
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

        navigationView = (NavigationView) findViewById(R.id.nav_View);
//        View headView = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int d = menuItem.getItemId();
                int f = R.id.nav_pets;
                switch (menuItem.getItemId())
                {
                    case R.id.nav_pets:
                        Intent intent = new Intent(QuestionActivity.this, MyPets.class);
                        startActivity(intent);
                        break;

                    case R.id.nav_vaccination:
                        Intent intent1 = new Intent(QuestionActivity.this, vaccination.class);
                        startActivity(intent1);
                        break;

                    case R.id.nav_medication:
                        Intent intent2 = new Intent(QuestionActivity.this, Medication.class);
                        startActivity(intent2);
                        break;

                    case R.id.nav_checkup:
                        Intent intent3 = new Intent(QuestionActivity.this, CheckUps.class);
                        startActivity(intent3);
                        break;

                    case R.id.nav_news:
                        Intent intent4 = new Intent(QuestionActivity.this, News.class);
                        startActivity(intent4);
                        break;

                    case R.id.nav_parasite:
                        Intent intent5 = new Intent(QuestionActivity.this, ParasitePrevention.class);
                        startActivity(intent5);
                        break;

                    case R.id.nav_subscr:
                        Intent intent6 = new Intent(QuestionActivity.this, Subscription.class);
                        startActivity(intent6);
                        break;

                    case R.id.nav_contact:
                        Intent intent7 = new Intent(QuestionActivity.this, ContactUs.class);
                        startActivity(intent7);
                        break;

                    case R.id.nav_setting:
                        Intent intent8 = new Intent(QuestionActivity.this, AccountSetting.class);
                        startActivity(intent8);
                        break;

                    case R.id.nav_support:
                        Intent intent9 = new Intent(QuestionActivity.this, Support.class);
                        startActivity(intent9);
                        break;

                    case  R.id.nav_home:
                        Intent intent10 = new Intent(QuestionActivity.this, Homepage.class);
                        startActivity(intent10);
                        break;

                    case R.id.nav_logout:
                        Intent intent11 = new Intent(QuestionActivity.this,MainActivity.class);
                        startActivity(intent11);
                        finish();
                        break;
                }
                return false;
            }
        });
    }


    private void initView() {
//        tvTitle = findViewById(R.id.tv_title);
//        tvTitle.setText("1 of 5");
        num = 5;
//        anList = new ArrayList<>();
//        for (int i = 0; i < num; i++) {
//            anList.add("");
//        }

        editPos = getIntent().getIntExtra("position",-1);
        pb = findViewById(R.id.pb);
        pb.setMax(num-1);
        pb.setProgress(0);
        btnPre = findViewById(R.id.btn_pre);
        btnPre.setOnClickListener(this);
        btnNext = findViewById(R.id.btn_next);
        btnNext.setOnClickListener(this);
        vf=findViewById(R.id.vf);
        adapter=new BaseAdapter() {
            @Override
            public int getCount() {
                return num;
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
//                index=position;
                createView(position);
                return root;
            }
        };
        vf.setAdapter(adapter);
    }

    private void createView(int pos) {
        root = LayoutInflater.from(QuestionActivity.this).inflate(R.layout.queitem, null);
        tvQue = root.findViewById(R.id.tv_que1);
        cb1 = root.findViewById(R.id.cb_choice1);
        cb2 = root.findViewById(R.id.cb_choice2);
        cb3 = root.findViewById(R.id.cb_choice3);
        cb4 = root.findViewById(R.id.cb_choice4);
        cb5 = root.findViewById(R.id.cb_choice5);
        cb6 = root.findViewById(R.id.cb_choice6);

        int count = pos + 1;
//        tvTitle.setText(count + " of 5");
        //修改答案
        if(editPos != -1){
            //获取Serializable 对象，并强转成 list集合
            Serializable serializable = getIntent().getSerializableExtra("resultList");
            answerList = (ArrayList<Question>) serializable;  //接收的时候强转

            String editId = answerList.get(editPos).getId();

            int answerSize = answerList.size();
            for(int i = answerSize-1; i > editPos-1; i--){
                answerList.remove(i);
            }

            int size = questionList.size();
            for(int i = 0; i < size; i++){
                String bb = questionList.get(i).getId();
                if(bb.equals(editId)){
                    index = i;
                    editPos = -1;
                    break;
                }
            }

        }


        que = questionList.get(index).getTitle();
        tvQue.setText( que);
        answerNum = questionList.get(index).getAnswerNum();
        if(answerNum > 1){
            cb1.setVisibility(View.VISIBLE);
            cb2.setVisibility(View.VISIBLE);
            A = questionList.get(index).getChoiceA();
            B = questionList.get(index).getChoiceB();
            cb1.setText(A);
            cb2.setText(B);
            cb1.setButtonDrawable(R.drawable.cb);
            cb2.setButtonDrawable(R.drawable.cb);
            cb1.setEnabled(true);
            cb2.setEnabled(true);
            cb1.setChecked(false);
            cb2.setChecked(false);

            if(answerNum > 2){
                cb3.setVisibility(View.VISIBLE);
                C = questionList.get(index).getChoiceC();
                cb3.setText(C);
                cb3.setButtonDrawable(R.drawable.cb);
                cb3.setEnabled(true);
                cb3.setChecked(false);
                if(answerNum > 3){
                    cb4.setVisibility(View.VISIBLE);
                    D = questionList.get(index).getChoiceD();
                    cb4.setText(D);
                    cb4.setButtonDrawable(R.drawable.cb);
                    cb4.setEnabled(true);
                    cb4.setChecked(false);
                    if(answerNum > 4){
                        cb5.setVisibility(View.VISIBLE);
                        E = questionList.get(index).getChoiceE();
                        cb5.setText(E);
                        cb5.setButtonDrawable(R.drawable.cb);
                        cb5.setEnabled(true);
                        cb5.setChecked(false);

                        if(answerNum > 5){
                            cb6.setVisibility(View.VISIBLE);
                            F = questionList.get(index).getChoiceF();
                            cb6.setText(F);
                            cb6.setButtonDrawable(R.drawable.cb);
                            cb6.setEnabled(true);
                            cb6.setChecked(false);

                        }else {
                            cb6.setVisibility(View.GONE);
                        }
                    }else{
                        cb5.setVisibility(View.GONE);
                        cb6.setVisibility(View.GONE);
                    }

                }else{
                    cb4.setVisibility(View.GONE);
                    cb5.setVisibility(View.GONE);
                    cb6.setVisibility(View.GONE);
                }

            }else{
                cb3.setVisibility(View.GONE);
                cb4.setVisibility(View.GONE);
                cb5.setVisibility(View.GONE);
                cb6.setVisibility(View.GONE);
            }

        }else{
            cb2.setVisibility(View.GONE);
            cb3.setVisibility(View.GONE);
            cb4.setVisibility(View.GONE);
            cb5.setVisibility(View.GONE);
            cb6.setVisibility(View.GONE);
        }


//        answer = questionList.get(index).getAnswer();

//        pb.setProgress(pos);

        answerList.add(questionList.get(index));

        cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    cb1.setChecked(true);
                    cb2.setChecked(false);
                    if(cb3.getVisibility() == View.VISIBLE){
                        cb3.setChecked(false);
                    }
                    if(cb4.getVisibility() == View.VISIBLE){
                        cb4.setChecked(false);
                    }
                    if(cb5.getVisibility() == View.VISIBLE){
                        cb5.setChecked(false);
                    }
                    if(cb6.getVisibility() == View.VISIBLE){
                        cb6.setChecked(false);
                    }
                }else {
                    cb1.setChecked(false);
                }
            }
        });
        cb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    cb2.setChecked(true);
                    cb1.setChecked(false);
                    if(cb3.getVisibility() == View.VISIBLE){
                        cb3.setChecked(false);
                    }
                    if(cb4.getVisibility() == View.VISIBLE){
                        cb4.setChecked(false);
                    }
                    if(cb5.getVisibility() == View.VISIBLE){
                        cb5.setChecked(false);
                    }
                    if(cb6.getVisibility() == View.VISIBLE){
                        cb6.setChecked(false);
                    }
                }else {
                    cb2.setChecked(false);
                }
            }
        });
        if(cb3.getVisibility() == View.VISIBLE){
            cb3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        cb3.setChecked(true);
                        cb1.setChecked(false);
                        cb2.setChecked(false);
                        if(cb4.getVisibility() == View.VISIBLE){
                            cb4.setChecked(false);
                        }
                        if(cb5.getVisibility() == View.VISIBLE){
                            cb5.setChecked(false);
                        }
                        if(cb6.getVisibility() == View.VISIBLE){
                            cb6.setChecked(false);
                        }
                    }else {
                        cb3.setChecked(false);
                    }
                }
            });
        }
        if(cb4.getVisibility() == View.VISIBLE){
            cb4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        cb4.setChecked(true);
                        cb1.setChecked(false);
                        cb2.setChecked(false);
                        if(cb3.getVisibility() == View.VISIBLE){
                            cb3.setChecked(false);
                        }
                        if(cb5.getVisibility() == View.VISIBLE){
                            cb5.setChecked(false);
                        }
                        if(cb6.getVisibility() == View.VISIBLE){
                            cb6.setChecked(false);
                        }
                    }else {
                        cb4.setChecked(false);
                    }
                }
            });
        }
        if(cb5.getVisibility() == View.VISIBLE){
            cb5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        cb5.setChecked(true);
                        cb1.setChecked(false);
                        cb2.setChecked(false);
                        if(cb3.getVisibility() == View.VISIBLE){
                            cb3.setChecked(false);
                        }
                        if(cb4.getVisibility() == View.VISIBLE){
                            cb4.setChecked(false);
                        }
                        if(cb6.getVisibility() == View.VISIBLE){
                            cb6.setChecked(false);
                        }
                    }else {
                        cb5.setChecked(false);
                    }
                }
            });
        }
        if(cb6.getVisibility() == View.VISIBLE){
            cb6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        cb6.setChecked(true);
                        cb1.setChecked(false);
                        cb2.setChecked(false);
                        if(cb3.getVisibility() == View.VISIBLE){
                            cb3.setChecked(false);
                        }
                        if(cb4.getVisibility() == View.VISIBLE){
                            cb4.setChecked(false);
                        }
                        if(cb5.getVisibility() == View.VISIBLE){
                            cb5.setChecked(false);
                        }
                    }else {
                        cb6.setChecked(false);
                    }
                }
            });
        }


        //手滑
       /* root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float startX=v.getWidth()/2,endX=v.getWidth()/2,min=100;
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        startX=event.getX();
                    case MotionEvent.ACTION_UP:
                        endX=event.getX();
                        break;
                }
                    if (startX - endX > min) {
                        boolean goNext = isAnswerTrue(index);
                        if (isLast) {
                            saveExam();
                        }else if(goNext){
                            vf.showNext();
                        }
                    }else if (endX - startX > min) {
                        int curPos = index;
                        boolean goPre = JumpPre(curPos);
                        if(goPre){
                            if(answerList.size() > 0 && curPos > 0){
                                answerList.remove(curPos);
                                answerList.remove(curPos-1);
                            }

                            vf.showPrevious();
                        }
                }
                return true;
            }
        });*/

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_pre:
                int curPos = index;
                boolean goPre = JumpPre(curPos);
                int answerSize = answerList.size() - 1;
                if(answerSize > 0 ){
                    answerList.remove(answerSize);
                    answerList.remove(answerSize-1);
                }else if(answerSize == 0 ){
                    answerList.remove(answerSize);
                }
                if(goPre){
                    vf.showPrevious();
                }
                break;
            case R.id.btn_next:
                boolean goNext = isAnswerTrue(index);
                if (isLast) {
                    saveExam();
                }else if(goNext){
                    vf.showNext();
                }
                break;
        }
    }

    private boolean JumpPre(int pos) {
        boolean hasPre = false;
        String prePos = questionList.get(pos).getPreQuestion();
        if(!prePos.equals("0")){
            int size = questionList.size();
            for(int i = 0; i < size; i++){
                String bb = questionList.get(i).getId();
                if(bb.equals(prePos)){
                    index = i;
                    hasPre = true;
                    break;
                }
            }
        }

        return hasPre;
    }

    private boolean isAnswerTrue(int pos) {
        String nextPos = "";
        boolean hasNext = false;
        if ((cb1.getVisibility() == View.VISIBLE && cb1.isChecked())
                || (cb2.getVisibility() == View.VISIBLE && cb2.isChecked())
                || (cb3.getVisibility() == View.VISIBLE && cb3.isChecked())
                || (cb4.getVisibility() == View.VISIBLE && cb4.isChecked())
                || (cb5.getVisibility() == View.VISIBLE && cb5.isChecked())
                || (cb6.getVisibility() == View.VISIBLE && cb6.isChecked())) {
            int answerPos = answerList.size() - 1;
            if (cb1.isChecked()) {
                String aa = questionList.get(pos).getChoiceA();
                answerList.get(answerPos).setAnswer(aa);//选择的项，保存下来，用于结果显示
                nextPos = questionList.get(pos).getNextQuestion_A();
            }else if (cb2.isChecked()) {
                String aa = questionList.get(pos).getChoiceB();
                answerList.get(answerPos).setAnswer(aa);
                nextPos = questionList.get(pos).getNextQuestion_B();
            }else if (cb3.isChecked()) {
                String aa = questionList.get(pos).getChoiceC();
                answerList.get(answerPos).setAnswer(aa);
                nextPos = questionList.get(pos).getNextQuestion_C();
            }else if (cb4.isChecked()) {
                String aa = questionList.get(pos).getChoiceD();
                answerList.get(answerPos).setAnswer(aa);
                nextPos = questionList.get(pos).getNextQuestion_D();
            }else if (cb5.isChecked()) {
                String aa = questionList.get(pos).getChoiceE();
                answerList.get(answerPos).setAnswer(aa);
                nextPos = questionList.get(pos).getNextQuestion_E();
            }else if (cb6.isChecked()) {
                String aa = questionList.get(pos).getChoiceF();
                answerList.get(answerPos).setAnswer(aa);
                nextPos = questionList.get(pos).getNextQuestion_F();
            }

            int size = questionList.size();
            for(int i = 0; i < size; i++){
                String bb = questionList.get(i).getId();
                if(bb.equals(nextPos)){
                    if(nextPos.contains("FA")){
                        isLast = true;
                        recommendId = nextPos;
                        recommend = questionList.get(i).getTitle();
                    }else {
                        index = i;
                        hasNext = true;
                    }
                    break;
                }
            }
        }else {
            Toast.makeText(QuestionActivity.this, "please select answer", Toast.LENGTH_SHORT).show();
        }
        return hasNext;
    }


    private void saveExam() {
        Intent intent = new Intent(this, RecommendActivity.class);
        intent.putExtra("recommendId", recommendId);
        intent.putExtra("recommend", recommend);
        intent.putExtra("resultList", answerList);
        intent.putExtra("type", questionType);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.que, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void moveToItem(int t) {
        if (t != index) {
            if(t>index) {
                int d= t-index;
                for (int i = 0; i < d + 1; i++)
                    vf.showNext();
            }else if(t<index){
                int p=index-t;
                for (int i = 0; i < p + 1; i++)
                    vf.showPrevious();
            }
        }
    }



    private void initData() {
        questionList.clear();
        questionType = getIntent().getStringExtra("type");
        if(questionType != null && questionType.equals("Vomiting")){
            Vomiting_Diarrhoea();
        }else{
            Breathing();
        }
    }



    private void Breathing() {
        questionList.clear();
        questionList = QuestionDBHelper.queryBreathing_QuestionDB(this);

        /*Question question1 = new Question();
        question1.setId("1");// 序号，题库第几题
        question1.setTitle("Breathing");//题目
        question1.setAnswerNum(6);// 选项个数
        question1.setChoiceA("Sneezing");// 选项 A
        question1.setChoiceB("Nasal discharge");// 选项 B
        question1.setChoiceC("Panting");// 选项 c
        question1.setChoiceD("Choking");// 选项 d
        question1.setChoiceE("Coughing");// 选项 e
        question1.setChoiceF("Gasping/open mouth breathing, difficulty breathing");
        question1.setPreQuestion("0");// 上一题序号 PREVIOUS QUESTION
        question1.setNextQuestion_A("2");// A选项的下一题序号 NEXT QUESTION FOR OPTION_A
        question1.setNextQuestion_B("3");
        question1.setNextQuestion_C("4");
        question1.setNextQuestion_D("5");
        question1.setNextQuestion_E("6");
        question1.setNextQuestion_F("FA13");
        questionList.add(question1);

        Question question2 = new Question();
        question2.setId("2");
        question2.setTitle("When did the sneezing start?");
        question2.setAnswerNum(3);
        question2.setChoiceA("Within the last day");
        question2.setChoiceB("1-3 days ago");
        question2.setChoiceC("More than 3 days");
        question2.setChoiceD("");
        question2.setChoiceE("");
        question2.setChoiceF("");
        question2.setPreQuestion("1");
        question2.setNextQuestion_A("7");
        question2.setNextQuestion_B("8");
        question2.setNextQuestion_C("FA14");
        question2.setNextQuestion_D("");
        question2.setNextQuestion_E("");
        question2.setNextQuestion_F("");
        questionList.add(question2);

        Question question3 = new Question();
        question3.setId("3");
        question3.setAnswerNum(2);
        question3.setTitle("Nasal discharge:");
        question3.setChoiceA("Both nostrils");
        question3.setChoiceB("One nostril");
        question3.setChoiceC("");
        question3.setChoiceD("");
        question3.setChoiceE("");
        question3.setChoiceF("");
        question3.setPreQuestion("1");
        question3.setNextQuestion_A("FA19");
        question3.setNextQuestion_B("10");
        question3.setNextQuestion_C("");
        question3.setNextQuestion_D("");
        question3.setNextQuestion_E("");
        question3.setNextQuestion_F("");
        questionList.add(question3);


        Question question4 = new Question();
        question4.setId("4");
        question4.setTitle("Is there any possibility of heat stroke? (Panting excessively, red gums, collapsed, hot to touch)  ");
        question4.setAnswerNum(3);
        question4.setChoiceA("Yes");
        question4.setChoiceB("No");
        question4.setChoiceC("Not sure ");
        question4.setChoiceD("");
        question4.setChoiceE("");
        question4.setChoiceF("");
        question4.setPreQuestion("1");
        question4.setNextQuestion_A("FA1");
        question4.setNextQuestion_B("FA4");
        question4.setNextQuestion_C("FA1");
        question4.setNextQuestion_D("");
        question4.setNextQuestion_E("");
        question4.setNextQuestion_F("");
        questionList.add(question4);



        Question question5 = new Question();
        question5.setId("5");
        question5.setTitle("Can they breathe properly? ");
        question5.setAnswerNum(3);
        question5.setChoiceA("Yes");
        question5.setChoiceB("No");
        question5.setChoiceC("Not sure ");
        question5.setChoiceD("");
        question5.setChoiceE("");
        question5.setChoiceF("");
        question5.setPreQuestion("1");
        question5.setNextQuestion_A("11");
        question5.setNextQuestion_B("FA5");
        question5.setNextQuestion_C("12");
        question5.setNextQuestion_D("");
        question5.setNextQuestion_E("");
        question5.setNextQuestion_F("");
        questionList.add(question5);


        Question question6 = new Question();
        question6.setId("6");
        question6.setTitle("Can they breathe properly? ");
        question6.setAnswerNum(3);
        question6.setChoiceA("Yes");
        question6.setChoiceB("No");
        question6.setChoiceC("Not sure ");
        question6.setChoiceD("");
        question6.setChoiceE("");
        question6.setChoiceF("");
        question6.setPreQuestion("1");
        question6.setNextQuestion_A("13");
        question6.setNextQuestion_B("FA5");
        question6.setNextQuestion_C("FA8");
        question6.setNextQuestion_D("");
        question6.setNextQuestion_E("");
        question6.setNextQuestion_F("");
        questionList.add(question6);


        Question question7 = new Question();
        question7.setId("7");
        question7.setTitle("How many sneezing episodes per day?  ");
        question7.setAnswerNum(3);
        question7.setChoiceA("1-5");
        question7.setChoiceB("5-10");
        question7.setChoiceC(">10 ");
        question7.setChoiceD("");
        question7.setChoiceE("");
        question7.setChoiceF("");
        question7.setPreQuestion("2");
        question7.setNextQuestion_A("FA24");
        question7.setNextQuestion_B("FA15");
        question7.setNextQuestion_C("FA15");
        question7.setNextQuestion_D("");
        question7.setNextQuestion_E("");
        question7.setNextQuestion_F("");
        questionList.add(question7);


        Question question8 = new Question();
        question8.setId("8");
        question8.setTitle("Is there nasal discharge also? ");
        question8.setAnswerNum(2);
        question8.setChoiceA("Yes");
        question8.setChoiceB("No");
        question8.setChoiceD("");
        question8.setChoiceE("");
        question8.setChoiceF("");
        question8.setPreQuestion("2");
        question8.setNextQuestion_A("FA16");
        question8.setNextQuestion_B("9");
        question8.setNextQuestion_C("");
        question8.setNextQuestion_D("");
        question8.setNextQuestion_E("");
        question8.setNextQuestion_F("");
        questionList.add(question8);

        Question question9 = new Question();
        question9.setId("9");
        question9.setTitle("How many episodes of sneezing are there per day? ");
        question9.setAnswerNum(3);
        question9.setChoiceA("Only happening occasionally ");
        question9.setChoiceB("1-5");
        question9.setChoiceC("More than 5 ");
        question9.setChoiceD("");
        question9.setChoiceE("");
        question9.setChoiceF("");
        question9.setPreQuestion("8");
        question9.setNextQuestion_A("FA17");
        question9.setNextQuestion_B("FA18");
        question9.setNextQuestion_C("FA18");
        question9.setNextQuestion_D("");
        question9.setNextQuestion_E("");
        question9.setNextQuestion_F("");
        questionList.add(question9);

        Question question10 = new Question();
        question10.setId("10");
        question10.setTitle("What colour is the discharge?  ");
        question10.setAnswerNum(3);
        question10.setChoiceA("White/green/yellow");
        question10.setChoiceB("Clear/watery");
        question10.setChoiceC("Red/brown");
        question10.setChoiceD("");
        question10.setChoiceE("");
        question10.setChoiceF("");
        question10.setPreQuestion("3");
        question10.setNextQuestion_A("FA21");
        question10.setNextQuestion_B("FA22");
        question10.setNextQuestion_C("FA23");
        question10.setNextQuestion_D("");
        question10.setNextQuestion_E("");
        question10.setNextQuestion_F("");
        questionList.add(question10);

        Question question11 = new Question();
        question11.setId("11");
        question11.setTitle("Can you remove the object causing the obstruction? (Be careful not to get bitten)  ");
        question11.setAnswerNum(2);
        question11.setChoiceA("Yes");
        question11.setChoiceB("No");
        question11.setChoiceC("");
        question11.setChoiceD("");
        question11.setChoiceE("");
        question11.setChoiceF("");
        question11.setPreQuestion("5");
        question11.setNextQuestion_A("FA20");
        question11.setNextQuestion_B("FA3");
        question11.setNextQuestion_C("");
        question11.setNextQuestion_D("");
        question11.setNextQuestion_E("");
        question11.setNextQuestion_F("");
        questionList.add(question11);


        Question question12 = new Question();
        question12.setId("12");
        question12.setTitle("Lift up the top lip and check the gums. What colour are they? ");
        question12.setAnswerNum(4);
        question12.setChoiceA("Salmon pink ");
        question12.setChoiceB("Pale pink");
        question12.setChoiceC("Blue");
        question12.setChoiceD("White");
        question12.setChoiceE("");
        question12.setChoiceF("");
        question12.setPreQuestion("5");
        question12.setNextQuestion_A("FA6");
        question12.setNextQuestion_B("FA7");
        question12.setNextQuestion_C("FA8");
        question12.setNextQuestion_D("FA8");
        question12.setNextQuestion_E("");
        question12.setNextQuestion_F("");
        questionList.add(question12);


        Question question13 = new Question();
        question13.setId("13");
        question13.setTitle("How long have they been coughing for? ");
        question13.setAnswerNum(3);
        question13.setChoiceA("Less than 24 hours");
        question13.setChoiceB("1-7 days");
        question13.setChoiceC("More than 1 week");
        question13.setChoiceD("");
        question13.setChoiceE("");
        question13.setChoiceF("");
        question13.setPreQuestion("6");
        question13.setNextQuestion_A("14");
        question13.setNextQuestion_B("FA12");
        question13.setNextQuestion_C("FA12");
        question13.setNextQuestion_D("");
        question13.setNextQuestion_E("");
        question13.setNextQuestion_F("");
        questionList.add(question13);


        Question question14 = new Question();
        question14.setId("14");
        question14.setTitle("How often are the coughing episodes? ");
        question14.setAnswerNum(3);
        question14.setChoiceA("Occasionally ");
        question14.setChoiceB("Every couple of hours");
        question14.setChoiceC("Multiple times an hour");
        question14.setChoiceD("");
        question14.setChoiceE("");
        question14.setChoiceF("");
        question14.setPreQuestion("13");
        question14.setNextQuestion_A("15");
        question14.setNextQuestion_B("16");
        question14.setNextQuestion_C("FA12");
        question14.setNextQuestion_D("");
        question14.setNextQuestion_E("");
        question14.setNextQuestion_F("");
        questionList.add(question14);

        Question question15 = new Question();
        question15.setId("15");
        question15.setTitle("How severe are the coughing episodes?");
        question15.setAnswerNum(3);
        question15.setChoiceA("Mild ");
        question15.setChoiceB("Moderate");
        question15.setChoiceC("Severe");
        question15.setChoiceD("");
        question15.setChoiceE("");
        question15.setChoiceF("");
        question15.setPreQuestion("14");
        question15.setNextQuestion_A("FA9");
        question15.setNextQuestion_B("FA10");
        question15.setNextQuestion_C("FA11");
        question15.setNextQuestion_D("");
        question15.setNextQuestion_E("");
        question15.setNextQuestion_F("");
        questionList.add(question15);

        Question question16 = new Question();
        question16.setId("16");
        question16.setTitle("How severe are the coughing episodes?");
        question16.setAnswerNum(3);
        question16.setChoiceA("Mild ");
        question16.setChoiceB("Moderate");
        question16.setChoiceC("Severe");
        question16.setChoiceD("");
        question16.setChoiceE("");
        question16.setChoiceF("");
        question16.setPreQuestion("14");
        question16.setNextQuestion_A("FA10");
        question16.setNextQuestion_B("FA12");
        question16.setNextQuestion_C("FA12");
        question16.setNextQuestion_D("");
        question16.setNextQuestion_E("");
        question16.setNextQuestion_F("");
        questionList.add(question16);



        Question question_FA1 = new Question();
        question_FA1.setId("FA1");
        question_FA1.setTitle(" Category: emergency - urgent veterinary attention required. \n" +
                "Heat stroke can be fatal if not treated immediately. Wetting your pet with room temperature water will assist with cooling " +
                "down while you transport them to the vet. Do not wrap in wet towels as this may trap heat and make the heat stroke worse");
        questionList.add(question_FA1);



        Question question_FA3 = new Question();
        question_FA3.setId("FA3");
        question_FA3.setTitle("3.");
        questionList.add(question_FA3);



        Question question_FA4 = new Question();
        question_FA4.setId("FA4");
        question_FA4.setTitle("Category: non-urgent, veterinary advice suggested. \n" +
                "While panting is not necessarily an emergency, it can be an indicator of underlying respiratory disease. Book an appointment with your vet at your earliest convenience if your pet seems stable (comfortable, gum colour is nice and pink, doesn’t appear to be struggling to breathe); otherwise take them straight to the vet");
        questionList.add(question_FA4);



        Question question_FA5 = new Question();
        question_FA5.setId("FA5");
        question_FA5.setTitle("Category: urgent vet attention required.\n" +
                "Your pet may suffer permanent brain damage within minutes if unable to breathe properly");
        questionList.add(question_FA5);



        Question question_FA6 = new Question();
        question_FA6.setId("FA6");
        question_FA6.setTitle("Category: contact you veterinarian.\n" +
                "Salmon pink gums indicate that your pet is oxygenating well, but if you still suspect that they are choking on something, a veterinarian should be seen. ");
        questionList.add(question_FA6);


        Question question_FA7 = new Question();
        question_FA7.setId("FA7");
        question_FA7.setTitle(" Category: urgent vet attention required. \n" +
                "Pale gums suggest shock or decreased circulating oxygen. Your pet needs to be assessed by a veterinarian as soon as possible. ");
        questionList.add(question_FA7);

        Question question_FA8 = new Question();
        question_FA8.setId("FA8");
        question_FA8.setTitle(" Category: life-threatening emergency.  \n" +
                "Blue or white gums suggest a critical lack of oxygen to the brain. Urgent veterinary attention is needed. Commence CPR if indicated (link)");
        questionList.add(question_FA8);

        Question question_FA9 = new Question();
        question_FA9.setId("FA9");
        question_FA9.setTitle(" Monitor cough, seek vet attention if cough persists. \n" +
                "Cats: \n"+
                "Dogs: \n");
        questionList.add(question_FA9);


        Question question_FA10 = new Question();
        question_FA10.setId("FA10");
        question_FA10.setTitle(" Should be ok to monitor for the next 24 hours. Seek vet advice if coughing doesn't resolve in next 24 hours. If pet has trouble breathing at any point, or appears to be in respiratory distress, seek vet.\n" +
                "Cats: open mouth breathing \n"+
                "Dogs:");
        questionList.add(question_FA10);

        Question question_FA11 = new Question();
        question_FA11.setId("FA11");
        question_FA11.setTitle(" See vet for medication/further investigation");
        questionList.add(question_FA11);

        Question question_FA12 = new Question();
        question_FA12.setId("FA12");
        question_FA12.setTitle(" See vet ASAP for medication/further investigation");
        questionList.add(question_FA12);

        Question question_FA13 = new Question();
        question_FA13.setId("FA13");
        question_FA13.setTitle(" Emergency");
        questionList.add(question_FA13);


        Question question_FA14 = new Question();
        question_FA14.setId("FA14");
        question_FA14.setTitle(" See vet for further investigation");
        questionList.add(question_FA14);

        Question question_FA15 = new Question();
        question_FA15.setId("FA15");
        question_FA15.setTitle("See vet for further investigation. FB etc");
        questionList.add(question_FA15);

        Question question_FA16 = new Question();
        question_FA16.setId("FA16");
        question_FA16.setTitle(" See vet for further investigation");
        questionList.add(question_FA16);

        Question question_FA17 = new Question();
        question_FA17.setId("FA17");
        question_FA17.setTitle(" See vet for further investigation.\n"+
                "Dog: allergies\n"+
                "Cat: cat flu"
        );
        questionList.add(question_FA17);

        Question question_FA18 = new Question();
        question_FA18.setId("FA18");
        question_FA18.setTitle(" Category: moderate\n“+”Make an appointment......");
        questionList.add(question_FA18);

        Question question_FA19 = new Question();
        question_FA19.setId("FA19");
        question_FA19.setTitle("19.");
        questionList.add(question_FA19);

        Question question_FA20 = new Question();
        question_FA20.setId("FA20");
        question_FA20.setTitle(" Gently remove the object if it is safe to do so. It is advisable to have a vet check for any airway damage caused by the object.");
        questionList.add(question_FA20);

        Question question_FA21 = new Question();
        question_FA21.setId("FA21");
        question_FA21.setTitle(" 21.");
        questionList.add(question_FA21);

        Question question_FA22 = new Question();
        question_FA22.setId("FA22");
        question_FA22.setTitle(" 22.");
        questionList.add(question_FA22);

        Question question_FA23 = new Question();
        question_FA23.setId("FA23");
        question_FA23.setTitle(" 23.");
        questionList.add(question_FA23);

        Question question_FA24 = new Question();
        question_FA24.setId("FA24");
        question_FA24.setTitle(" 24.");
        questionList.add(question_FA24);*/

    }

    private void Vomiting_Diarrhoea() {
        questionList.clear();
        questionList = QuestionDBHelper.queryVomiting_QuestionDB(this);

        /*Question question1 = new Question();
        question1.setId("1");
        question1.setTitle("Vomiting/Diarrhoea");
        question1.setAnswerNum(3);
        question1.setChoiceA("Vomiting");
        question1.setChoiceB("Diarrhoea");
        question1.setChoiceC("Both");
        question1.setPreQuestion("0");
        question1.setNextQuestion_A("2");
        question1.setNextQuestion_B("7");
        question1.setNextQuestion_C("10");
        questionList.add(question1);

        Question question2 = new Question();
        question2.setId("2");
        question2.setTitle("How long for? ");
        question2.setAnswerNum(3);
        question2.setChoiceA("Less than 1 day");
        question2.setChoiceB("24-48 hours");
        question2.setChoiceC("More than 48 hours");
        question2.setPreQuestion("1");
        question2.setNextQuestion_A("3");
        question2.setNextQuestion_B("4");
        question2.setNextQuestion_C("FA66");
        questionList.add(question2);

        Question question3 = new Question();
        question3.setId("3");
        question3.setTitle("How many times?");
        question3.setAnswerNum(3);
        question3.setChoiceA("Occasionally, only a couple of vomits");
        question3.setChoiceB("Many episodes ");
        question3.setChoiceC("Constantly");
        question3.setPreQuestion("2");
        question3.setNextQuestion_A("FA65");
        question3.setNextQuestion_B("FA64");
        question3.setNextQuestion_C("FA64");
        questionList.add(question3);

        Question question4 = new Question();
        question4.setId("4");
        question4.setTitle("How many times? ");
        question4.setAnswerNum(3);
        question4.setChoiceA("Occasionally, only a couple of vomits");
        question4.setChoiceB("Many episodes");
        question4.setChoiceC("Constantly ");
        question4.setPreQuestion("2");
        question4.setNextQuestion_A("FA65");
        question4.setNextQuestion_B("5");
        question4.setNextQuestion_C("6");
        questionList.add(question4);


        Question question5 = new Question();
        question5.setId("5");
        question5.setTitle("Has there been a recent change in diet? ");
        question5.setAnswerNum(2);
        question5.setChoiceA("Yes");
        question5.setChoiceB("No");
        question5.setPreQuestion("4");
        question5.setNextQuestion_A("FA63");
        question5.setNextQuestion_B("FA64");
        questionList.add(question5);



        Question question6 = new Question();
        question6.setId("6");
        question6.setTitle("Could my pet have eaten a foreign object (e.g. bones, toys)? ");
        question6.setAnswerNum(3);
        question6.setChoiceA("Yes");
        question6.setChoiceB("No");
        question6.setChoiceC("Not sure ");
        question6.setPreQuestion("4");
        question6.setNextQuestion_A("FA62");
        question6.setNextQuestion_B("FA61");
        question6.setNextQuestion_C("FA62");
        questionList.add(question6);


        Question question7 = new Question();
        question7.setId("7");
        question7.setTitle("How long for? ");
        question7.setAnswerNum(3);
        question7.setChoiceA("Less than 24 hours");
        question7.setChoiceB("24-48 hours");
        question7.setChoiceC("More than 48 hours ");
        question7.setPreQuestion("1");
        question7.setNextQuestion_A("8");
        question7.setNextQuestion_B("9");
        question7.setNextQuestion_C("FA73");
        questionList.add(question7);


        Question question8 = new Question();
        question8.setId("8");
        question8.setTitle("How many episodes of diarrhoea? ");
        question8.setAnswerNum(3);
        question8.setChoiceA("1-2");
        question8.setChoiceB("3-5");
        question8.setChoiceC("More than 5");
        question8.setPreQuestion("7");
        question8.setNextQuestion_A("FA78");
        question8.setNextQuestion_B("FA77");
        question8.setNextQuestion_C("FA76");
        questionList.add(question8);

        Question question9 = new Question();
        question9.setId("9");
        question9.setTitle("Is it diarrhoea every time, or are some solid stools being passed too? ");
        question9.setAnswerNum(2);
        question9.setChoiceA("Some solid stools");
        question9.setChoiceB("Every time");
        question9.setPreQuestion("7");
        question9.setNextQuestion_A("FA74");
        question9.setNextQuestion_B("FA75");
        questionList.add(question9);


        Question question10 = new Question();
        question10.setId("10");
        question10.setTitle("How long for?  ");
        question10.setAnswerNum(3);
        question10.setChoiceA("Less than 24 hours");
        question10.setChoiceB("24-48 hours");
        question10.setChoiceC("More than 48 hours");
        question10.setPreQuestion("1");
        question10.setNextQuestion_A("11");
        question10.setNextQuestion_B("13");
        question10.setNextQuestion_C("FA70");
        questionList.add(question10);

        Question question11 = new Question();
        question11.setId("11");
        question11.setTitle("How many episodes? ");
        question11.setAnswerNum(2);
        question11.setChoiceA("Only a couple");
        question11.setChoiceB("Multiple ");
        question11.setPreQuestion("10");
        question11.setNextQuestion_A("12");
        question11.setNextQuestion_B(" FA69");
        questionList.add(question11);


        Question question12 = new Question();
        question12.setId("12");
        question12.setTitle("Has there been a recent change in diet? ");
        question12.setAnswerNum(2);
        question12.setChoiceA("Yes");
        question12.setChoiceB("No");
        question12.setPreQuestion("11");
        question12.setNextQuestion_A("FA67");
        question12.setNextQuestion_B("FA68");
        questionList.add(question12);

        Question question13 = new Question();
        question13.setId("13");
        question13.setTitle("How many episodes? ");
        question13.setAnswerNum(2);
        question13.setChoiceA("Less than 3");
        question13.setChoiceB("Multiple ");
        question13.setPreQuestion("10");
        question13.setNextQuestion_A("FA72");
        question13.setNextQuestion_B(" FA71");
        questionList.add(question13);

        Question question_FA61 = new Question();
        question_FA61.setId("FA61");
        question_FA61.setTitle(" Pancreatitis etc");
        questionList.add(question_FA61);

        Question question_FA62 = new Question();
        question_FA62.setId("FA62");
        question_FA62.setTitle("Pancreatitis, FB");
        questionList.add(question_FA62);

        Question question_FA63 = new Question();
        question_FA63.setId("FA63");
        question_FA63.setTitle("Likely diet related but see vet to check e.g. in case of FB, In future gradually change diet");
        questionList.add(question_FA63);


        Question question_FA64 = new Question();
        question_FA64.setId("FA64");
        question_FA64.setTitle("Highly suspicious for FB/pancreatitis");
        questionList.add(question_FA64);

        Question question_FA65 = new Question();
        question_FA65.setId("FA65");
        question_FA65.setTitle("Diet change, monitor if safe to do so");
        questionList.add(question_FA65);

        Question question_FA66 = new Question();
        question_FA66.setId("FA66");
        question_FA66.setTitle("More than 48 hours warrants trip to vet for exam");
        questionList.add(question_FA66);

        Question question_FA67 = new Question();
        question_FA67.setId("FA67");
        question_FA67.setTitle("Monitor if well hydrated, BAR happy, bland diet");
        questionList.add(question_FA67);

        Question question_FA68 = new Question();
        question_FA68.setId("FA68");
        question_FA68.setTitle("Monitor if BAR");
        questionList.add(question_FA68);

        Question question_FA69 = new Question();
        question_FA69.setId("FA69");
        question_FA69.setTitle("Vet, dehydration");
        questionList.add(question_FA69);

        Question question_FA70 = new Question();
        question_FA70.setId("FA70");
        question_FA70.setTitle("Vet");
        questionList.add(question_FA70);

        Question question_FA71 = new Question();
        question_FA71.setId("FA71");
        question_FA71.setTitle("Vet, serious");
        questionList.add(question_FA71);

        Question question_FA72 = new Question();
        question_FA72.setId("FA72");
        question_FA72.setTitle("Vet, but not as serious");
        questionList.add(question_FA72);

        Question question_FA73 = new Question();
        question_FA73.setId("FA73");
        question_FA73.setTitle("Vet, urgent");
        questionList.add(question_FA73);


        Question question_FA74 = new Question();
        question_FA74.setId("FA74");
        question_FA74.setTitle("Vet, not as urgent.");
        questionList.add(question_FA74);


        Question question_FA75 = new Question();
        question_FA75.setId("FA75");
        question_FA75.setTitle(" Urgent");
        questionList.add(question_FA75);


        Question question_FA76 = new Question();
        question_FA76.setId("FA76");
        question_FA76.setTitle("Urgent, vet");
        questionList.add(question_FA76);

        Question question_FA77 = new Question();
        question_FA77.setId("FA77");
        question_FA77.setTitle("Not urgent, vet");
        questionList.add(question_FA77);

        Question question_FA78 = new Question();
        question_FA78.setId("FA78");
        question_FA78.setTitle("Monitor if safe to do so");
        questionList.add(question_FA78);*/

    }
}

