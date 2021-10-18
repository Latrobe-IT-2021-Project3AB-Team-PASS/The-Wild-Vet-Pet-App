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

    private String sendRecord;

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

        sendRecord = getIntent().getStringExtra("recordUN");

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
                        intent.putExtra("USERNAME",sendRecord);
                        startActivity(intent);
                        break;

                    case R.id.nav_vaccination:
                        Intent intent1 = new Intent(QuestionActivity.this, vaccination.class);
                        intent1.putExtra("recordUN",sendRecord);
                        startActivity(intent1);
                        break;

                    case R.id.nav_medication:
                        Intent intent2 = new Intent(QuestionActivity.this, Medication.class);
                        intent2.putExtra("recordUN",sendRecord);
                        startActivity(intent2);
                        break;

                    case R.id.nav_checkup:
                        Intent intent3 = new Intent(QuestionActivity.this, CheckUps.class);
                        intent3.putExtra("recordUN",sendRecord);
                        startActivity(intent3);
                        break;

                    case R.id.nav_news:
                        Intent intent4 = new Intent(QuestionActivity.this, News.class);
                        intent4.putExtra("recordUN",sendRecord);
                        startActivity(intent4);
                        break;

                    case R.id.nav_parasite:
                        Intent intent5 = new Intent(QuestionActivity.this, ParasitePrevention.class);
                        intent5.putExtra("recordUN",sendRecord);
                        startActivity(intent5);
                        break;

                    case R.id.nav_subscr:
                        Intent intent6 = new Intent(QuestionActivity.this, Subscription.class);
                        intent6.putExtra("recordUN",sendRecord);
                        startActivity(intent6);
                        break;

                    case R.id.nav_contact:
                        Intent intent7 = new Intent(QuestionActivity.this, ContactUs.class);
                        intent7.putExtra("recordUN",sendRecord);
                        startActivity(intent7);
                        break;

                    case R.id.nav_setting:
                        Intent intent8 = new Intent(QuestionActivity.this, AccountSetting.class);
                        intent8.putExtra("recordUN",sendRecord);
                        startActivity(intent8);
                        break;

                    case R.id.nav_support:
                        Intent intent9 = new Intent(QuestionActivity.this, Support.class);
                        intent9.putExtra("recordUN",sendRecord);
                        startActivity(intent9);
                        break;

                    case  R.id.nav_home:
                        Intent intent10 = new Intent(QuestionActivity.this, Homepage.class);
                        intent10.putExtra("recordUN",sendRecord);
                        startActivity(intent10);
                        break;

                    case R.id.nav_logout:
                        Intent intent11 = new Intent(QuestionActivity.this,MainActivity.class);
                        intent11.putExtra("recordUN",sendRecord);
                        startActivity(intent11);
                        finish();
                        break;
                }
                return false;
            }
        });
    }


    private void initView() {

        num = 5;


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
        intent.putExtra("recordUN",sendRecord);
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
    }

    private void Vomiting_Diarrhoea() {
        questionList.clear();
        questionList = QuestionDBHelper.queryVomiting_QuestionDB(this);
    }
}

