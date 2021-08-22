package com.example.dummy;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class CardDummyContent  {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();


    public static void addItem(DummyItem item) {
        ITEMS.add(item);
    }

    public static DummyItem createDummyItem(String id,int answerNum, String title, String choiceA, String choiceB, String choiceC,
                                            String choiceD, String choiceE, String choiceF,String answer,
                                            String preQuestion,String NextQuestion_A,String NextQuestion_B,String NextQuestion_C,
                                            String NextQuestion_D,String NextQuestion_E,String NextQuestion_F) {
        return new DummyItem( id,answerNum, title, choiceA, choiceB, choiceC,
                choiceD, choiceE, choiceF,answer, preQuestion,NextQuestion_A,
                NextQuestion_B,NextQuestion_C,NextQuestion_D,NextQuestion_E,NextQuestion_F);
    }



    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public  String id;
        public  int answerNum;
        public  String title;
        public  String choiceA;
        public  String choiceB;
        public  String choiceC;
        public  String choiceD;
        public  String choiceE;
        public  String choiceF;
        public  String answer;
        public  String preQuestion;
        public  String NextQuestion_A;
        public  String NextQuestion_B ;
        public  String NextQuestion_C ;
        public  String NextQuestion_D;
        public  String NextQuestion_E ;
        public  String NextQuestion_F ;


        public DummyItem(String id,int answerNum, String title, String choiceA, String choiceB, String choiceC,
                         String choiceD, String choiceE, String choiceF,String answer,
                         String preQuestion,String NextQuestion_A,String NextQuestion_B,String NextQuestion_C,
                         String NextQuestion_D,String NextQuestion_E,String NextQuestion_F) {
            this.id = id;
            this.answerNum = answerNum;
            this.title = title;
            this.choiceA = choiceA;
            this.choiceB = choiceB;
            this.choiceC = choiceC;
            this.choiceD = choiceD;
            this.choiceE = choiceE;
            this.choiceF = choiceF;
            this.answer = answer;
            this.preQuestion = preQuestion;
            this.NextQuestion_A = NextQuestion_A;
            this.NextQuestion_B = NextQuestion_B;
            this.NextQuestion_C = NextQuestion_C;
            this.NextQuestion_D = NextQuestion_D;
            this.NextQuestion_E = NextQuestion_E;
            this.NextQuestion_F = NextQuestion_F;
        }


    }
}
