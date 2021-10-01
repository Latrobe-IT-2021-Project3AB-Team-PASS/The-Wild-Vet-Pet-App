package com.example.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adapter.CardAdapter;
import com.example.bean.Question;
import com.example.dummy.CardDummyContent;
import com.example.dummy.OnItemClickListener;

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

    private ArrayList<Question> questionList = new ArrayList<Question>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //获取Serializable 对象，并强转成 list集合
        Serializable serializable = getIntent().getSerializableExtra("resultList");
        questionList = (ArrayList<Question>) serializable;  //接收的时候强转

        initView();
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
}
