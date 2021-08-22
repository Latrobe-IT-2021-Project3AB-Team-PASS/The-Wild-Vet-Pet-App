package com.example.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bean.Question;

import java.io.Serializable;
import java.util.ArrayList;


public class RecommendActivity extends AppCompatActivity {
    private String title,date,time;
    private String recommend = "";
    private TextView tvTitle,tv_recommend;
    private Button bt;

    private ArrayList<Question> questionList = new ArrayList<Question>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        Intent intent=getIntent();
        recommend=intent.getStringExtra("recommend");
        //获取Serializable 对象，并强转成 list集合
        Serializable serializable = getIntent().getSerializableExtra("resultList");
        questionList = (ArrayList<Question>) serializable;  //接收的时候强转

        initView();

    }

    private void initView() {
        tvTitle=findViewById(R.id.tv_title1);
        tv_recommend=findViewById(R.id.tv_recommend);
        tvTitle.setText("Recommend");
        tv_recommend.setText(recommend);

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
                startActivity(intent);
                finish();
            }
        });
    }
}
