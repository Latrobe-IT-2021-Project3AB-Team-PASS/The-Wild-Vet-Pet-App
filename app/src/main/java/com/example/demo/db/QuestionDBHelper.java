package com.example.demo.db;

import android.app.Activity;
import android.util.Log;

import com.example.bean.Question;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


public class QuestionDBHelper {

    private static ArrayList<Question> questionList = new ArrayList<Question>();

    /**
     * 创建题库数据表
     * @param
     */
    public static void create_BreathingQuestionDB(Activity activity) {
        //建表===========================================
        //表名
        String tabName1 = "BreathingQuestionTable";
            //表字段
        String[] tab_fields = {"question_id","title","answerNum","choiceA","choiceB","choiceC","choiceD","choiceE","choiceF"
        ,"preQuestion","nextQuestion_A","nextQuestion_B","nextQuestion_C","nextQuestion_D","nextQuestion_E","nextQuestion_F"};
        //创建表
        MysqlDBHelper.createTable(activity,tabName1, tab_fields);
    }

    /**
     * 创建题库数据表
     * @param
     */
    public static void create_VomitingQuestionDB(Activity activity) {
        //建表===========================================
        //表名
        String tabName2 = "VomitingQuestionTable";
        //表字段
        String[] tab_fields = {"question_id","title","answerNum","choiceA","choiceB","choiceC","choiceD","choiceE","choiceF"
                ,"preQuestion","nextQuestion_A","nextQuestion_B","nextQuestion_C","nextQuestion_D","nextQuestion_E","nextQuestion_F"};
        //创建表
        MysqlDBHelper.createTable(activity,tabName2, tab_fields);
    }

    /**
     * 添加题库数据
     * @param
     */
    public static void add_BreathingQuestionDB_Data(Activity activity) {
        //表名
        String tabName1 = "BreathingQuestionTable";
        //表字段
        String[] tab_fields = {"question_id","title","answerNum","choiceA","choiceB","choiceC","choiceD","choiceE","choiceF"
                ,"preQuestion","nextQuestion_A","nextQuestion_B","nextQuestion_C","nextQuestion_D","nextQuestion_E","nextQuestion_F"};

        //添加  Breathing  ===========================================
        String[] data_1 = {"1","Breathing","6","Sneezing","Nasal discharge","Panting","Choking","Coughing","Gasping/open mouth breathing, difficulty breathing"
                ,"0","2","3","4","5","6","FA13"};

        String[] data_2 = {"2","When did the sneezing start? ","3","Within the last day","1-3 days ago","More than 3 days","","",""
                ,"1","7","8","FA14","","",""};

        String[] data_FA14 = {"FA14","See vet for further investigation","","","","","","",""
                ,"","","","","","",""};

        MysqlDBHelper.insert(activity,tabName1, tab_fields, data_1);
        MysqlDBHelper.insert(activity,tabName1, tab_fields, data_2);

        MysqlDBHelper.insert(activity,tabName1, tab_fields, data_FA14);

    }

    /**
     * 添加题库数据
     * @param
     */
    public static void add_VomitingQuestionDB_Data(Activity activity) {
        //表名
        String tabName2 = "VomitingQuestionTable";
        //表字段
        String[] tab_fields = {"question_id","title","answerNum","choiceA","choiceB","choiceC","choiceD","choiceE","choiceF"
                ,"preQuestion","nextQuestion_A","nextQuestion_B","nextQuestion_C","nextQuestion_D","nextQuestion_E","nextQuestion_F"};

        //添加  Vomiting  ===========================================
        String[] data_1001 = {"1","Vomiting/Diarrhoea","3","Vomiting","Diarrhoea","Both","","",""
                ,"0","2","7","10","","",""};

        String[] data_1002 = {"2","How long for? ","3","Less than 1 day","24-48 hours","More than 48 hours","","",""
                ,"1","3","4","FA66","","",""};

        String[] data_FA66 = {"FA67","Monitor if well hydrated, BAR happy, bland diet","","","","","","",""
                ,"","","","","","",""};

        MysqlDBHelper.insert(activity,tabName2, tab_fields, data_1001);
        MysqlDBHelper.insert(activity,tabName2, tab_fields, data_1002);
        MysqlDBHelper.insert(activity,tabName2, tab_fields, data_FA66);
    }

    /**
     * 查询题库数据表
     * @param
     */
    public static ArrayList<Question> queryBreathing_QuestionDB(Activity activity) {
        //表名
        String tabName1 = "BreathingQuestionTable";
        //表字段
        String[] tab_fields = {"question_id","title","answerNum","choiceA","choiceB","choiceC","choiceD","choiceE","choiceF"
                ,"preQuestion","nextQuestion_A","nextQuestion_B","nextQuestion_C","nextQuestion_D","nextQuestion_E","nextQuestion_F"};

        questionList.clear();
        //查询=============================================
        final CountDownLatch downLatch = new CountDownLatch(1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                questionList = MysqlDBHelper.query(activity,tabName1, null, null, tab_fields);
                if(downLatch != null) {
                    downLatch.countDown();
                }
            }
        }).start();

        try {
            boolean ff = downLatch.await(60, TimeUnit.SECONDS);
            if(!ff) {
//                return "数据超时".getBytes();
                Log.i("","  数据超时 " );
                return null;
            }else{
                return questionList;
            }
        } catch (InterruptedException e) {
//            return "等待异常".getBytes();
            Log.i("","  等待异常 " );
            return null;
        }
//        return questionList;
    }

    /**
     * 查询题库数据表
     * @param
     */
    public static ArrayList<Question> queryVomiting_QuestionDB(Activity activity) {
        //表名
        String tabName2 = "VomitingQuestionTable";
        //表字段
        String[] tab_fields = {"question_id","title","answerNum","choiceA","choiceB","choiceC","choiceD","choiceE","choiceF"
                ,"preQuestion","nextQuestion_A","nextQuestion_B","nextQuestion_C","nextQuestion_D","nextQuestion_E","nextQuestion_F"};

        questionList.clear();
        //查询=============================================

        final CountDownLatch downLatch = new CountDownLatch(1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                questionList  = MysqlDBHelper.query(activity,tabName2, null, null, tab_fields);
                if(downLatch != null) {
                    downLatch.countDown();
                }
            }
        }).start();

        try {
            boolean ff = downLatch.await(60, TimeUnit.SECONDS);
            if(!ff) {
//                return "数据超时".getBytes();
                Log.i("","  数据超时 " );
                return null;
            }else{
                return questionList;
            }
        } catch (InterruptedException e) {
//            return "等待异常".getBytes();
            Log.i("","  等待异常 " );
            return null;
        }

//        return questionList;
    }
}


