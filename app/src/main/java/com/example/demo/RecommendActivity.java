package com.example.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bean.Question;

import java.io.Serializable;
import java.util.ArrayList;


public class RecommendActivity extends AppCompatActivity {
    private String title,date,time;
    private String recommendId = "";
    private String recommend = "";
    private TextView tvTitle,tv_recommend;
    private Button bt;

    private ArrayList<Question> questionList = new ArrayList<Question>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        Intent intent=getIntent();
        recommendId=intent.getStringExtra("recommendId");
        recommend=intent.getStringExtra("recommend");
        //获取Serializable 对象，并强转成 list集合
        Serializable serializable = getIntent().getSerializableExtra("resultList");
        questionList = (ArrayList<Question>) serializable;  //接收的时候强转

        initView();

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
                startActivity(intent);
                finish();
            }
        });
    }
}
