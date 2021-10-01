package com.example.demo.db;

import android.app.Activity;
import android.content.Context;

import com.example.bean.Question;
import com.example.demo.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class MysqlDBHelper {

    //配置文件 读取jdbc的配置文件  数据库的配置文件 db.properties
    private static Connection conn;
    private static PreparedStatement ps;
    private Context mContext;

    /**
     * 创建表
     * @param tabName 表名称
     * @param tab_fields 表字段
     */
    public static void createTable(Activity activity,String tabName,String[] tab_fields) {
        conn = getConnection(activity);  // 首先要获取连接，即连接到数据库
        try {
            String sql = "create table "+tabName+"(id int auto_increment primary key not null";
            if(tab_fields!=null&&tab_fields.length>0){
                sql+=",";
                int length = tab_fields.length;
                for(int i =0 ;i<length;i++){
                    //添加字段
                    sql+=tab_fields[i].trim()+" varchar(50)";
                    //防止最后一个,
                    if(i<length-1){
                        sql+=",";
                    }
                }
            }
            //拼凑完 建表语句 设置默认字符集
            sql+=")DEFAULT CHARSET=utf8;";
            System.out.println("建表语句是："+sql);
            ps = conn.prepareStatement(sql);
            ps.executeUpdate(sql);
            ps.close();
            conn.close();  //关闭数据库连接
        } catch (SQLException e) {
            System.out.println("建表失败" + e.getMessage());
        }
    }


    /**
     * 添加数据
     * @param tabName 表名
     * @param fields 参数字段
     * @param data 参数字段数据
     */
    public static void insert(Activity activity,String tabName,String[] fields,String[] data) {
        conn = getConnection(activity);  // 首先要获取连接，即连接到数据库
        try {
            String sql = "insert into "+tabName+"(";
            int length = fields.length;
            for(int i=0;i<length;i++){
                sql+=fields[i];
                //防止最后一个,
                if(i<length-1){
                    sql+=",";
                }
            }
            sql+=") values(";
            for(int i=0;i<length;i++){
                sql+="?";
                //防止最后一个,
                if(i<length-1){
                    sql+=",";
                }
            }
            sql+=");";
            System.out.println("添加数据的sql:"+sql);
            //预处理SQL 防止注入
            excutePs(sql,length,data);
            //执行
            ps.executeUpdate();
            //关闭流
            ps.close();
            conn.close();  //关闭数据库连接
        } catch (SQLException e) {
            System.out.println("添加数据失败" + e.getMessage());
        }
    }



    /**
     * 查询表 【查询结果的顺序要和数据库字段的顺序一致】
     * @param tabName 表名
     * @param fields 参数字段
     * @param data 参数字段数据
     * @param tab_fields 数据库的字段
     */
//    public static String[] query(Activity activity,String tabName,String[] fields,String[] data,String[] tab_fields){
    public static ArrayList<Question> query(Activity activity, String tabName, String[] fields, String[] data, String[] tab_fields){
        ArrayList<Question> questionList = new ArrayList<Question>();
        conn = getConnection(activity);  // 首先要获取连接，即连接到数据库
        String[] result = null;
        try {
            String sql = "";
            int length = 0;

            if(fields != null && fields.length > 0){
                sql = "select * from "+tabName+" where ";
                length = fields.length;
                for(int i=0;i<length;i++){
                    sql+=fields[i]+" = ? ";
                    //防止最后一个,
                    if(i<length-1){
                        sql+=" and ";
                    }
                }
            }else{
                sql = "select * from "+tabName;
            }

            sql+=";";
            System.out.println("查询sql:"+sql);
            //预处理SQL 防止注入
            excutePs(sql,length,data);
            //查询结果集
            ResultSet rs = ps.executeQuery();
            //存放结果集
            result = new String[tab_fields.length];
            while(rs.next()){
                for (int i = 0; i < tab_fields.length; i++) {
                    result[i] = rs.getString(tab_fields[i]);
                }

                Question question1 = new Question();
                question1.setId(result[0]);
                question1.setTitle(result[1]);
                if(result[2] != null && !result[2].isEmpty()){
                    question1.setAnswerNum(Integer.parseInt(result[2]));
                }

                question1.setChoiceA(result[3]);
                question1.setChoiceB(result[4]);
                question1.setChoiceC(result[5]);
                question1.setChoiceD(result[6]);
                question1.setChoiceE(result[7]);
                question1.setChoiceF(result[8]);
                question1.setPreQuestion(result[9]);
                question1.setNextQuestion_A(result[10]);
                question1.setNextQuestion_B(result[11]);
                question1.setNextQuestion_C(result[12]);
                question1.setNextQuestion_D(result[13]);
                question1.setNextQuestion_E(result[14]);
                question1.setNextQuestion_F(result[15]);
                questionList.add(question1);
            }


            //关闭流
            rs.close();
            ps.close();
            conn.close();  //关闭数据库连接
        } catch (SQLException e) {
            System.out.println("查询失败" + e.getMessage());
        }
//        return result;
        return questionList;
    }



    /**
     * 获取某张表总数
     * @param tabName
     * @return
     */
    public static Integer getCount(Activity activity,String tabName){
        int count = 0;
        conn = getConnection(activity);  // 首先要获取连接，即连接到数据库
        try {
            String sql = "select count(*) from "+tabName+" ;";
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                count = rs.getInt(1);
            }
            rs.close();
            ps.close();
            conn.close();  //关闭数据库连接
        } catch (SQLException e) {
            System.out.println("获取总数失败" + e.getMessage());
        }
        return count;
    }



    /**
     * 后台分页显示
     * @param tabName
     * @param pageNo
     * @param pageSize
     * @param tab_fields
     * @return
     */
    public static List<String[]> queryForPage(Activity activity,String tabName,int pageNo,int pageSize ,String[] tab_fields){
        conn = getConnection(activity);  // 首先要获取连接，即连接到数据库
        List<String[]> list = new ArrayList<String[]>();
        try {
            String sql = "select * from "+tabName+" LIMIT ?,? ; ";
            System.out.println("查询sql:"+sql);
            //预处理SQL 防止注入
            ps = conn.prepareStatement(sql);
            //注入参数
            ps.setInt(1,pageNo);
            ps.setInt(2,pageSize);
            //查询结果集
            ResultSet rs = ps.executeQuery();
            //存放结果集
            while(rs.next()){
                String[] result = new String[tab_fields.length];
                for (int i = 0; i < tab_fields.length; i++) {
                    result[i] = rs.getString(tab_fields[i]);
                }
                list.add(result);
            }
            //关闭流
            rs.close();
            ps.close();
            conn.close();  //关闭数据库连接
        } catch (SQLException e) {
            System.out.println("查询失败" + e.getMessage());
        }
        return list;
    }



    /**
     * 清空表数据
     * @param tabName 表名称
     */
    public static void delete(Activity activity,String tabName){
        conn = getConnection(activity);  // 首先要获取连接，即连接到数据库
        try {
            String sql = "delete from "+tabName+";";
            System.out.println("删除数据的sql:"+sql);
            //预处理SQL 防止注入
            ps = conn.prepareStatement(sql);
            //执行
            ps.executeUpdate();
            //关闭流
            ps.close();
            conn.close();  //关闭数据库连接
        } catch (SQLException e) {
            System.out.println("删除数据失败" + e.getMessage());
        }
    }



    /**
     * 用于注入参数
     * @param @ps
     * @param data
     * @throws SQLException
     */
    private static void excutePs(String sql,int length,String[] data) throws SQLException{
        //预处理SQL 防止注入
        ps = conn.prepareStatement(sql);
        if(data != null && data.length > 0){
            //注入参数
            for(int i=0;i<length;i++){
                ps.setString(i+1,data[i]);
            }
        }

    }

    /* 获取数据库连接的函数*/
    private static Connection getConnection(Context mContext) {
        Connection con = null;  //创建用于连接数据库的Connection对象
        try {
            Properties properties = new Properties();
            properties.load(mContext.getResources().openRawResource(R.raw.db_properties));
            String dbClassName = properties.getProperty("db.classname");
            String dbUrl = properties.getProperty("db.url");
            String dbUserName = properties.getProperty("db.username");
            String dbPassword = properties.getProperty("db.password");
//            Class.forName(bundle.getString("db.classname"));// 加载Mysql数据驱动
            Class.forName(dbClassName);// 加载Mysql数据驱动
            con = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);// 创建数据连接
        } catch (Exception e) {
            System.out.println("数据库连接失败" + e.getMessage());
        }
        return con;  //返回所建立的数据库连接
    }


    /**
     * 判断表是否存在
     * @param tabName
     * @return
     */
    public static boolean exitTable(Activity activity, String tabName){
        boolean flag = false;
        conn = getConnection(activity);  // 首先要获取连接，即连接到数据库
        try {
            String sql = "select id from "+tabName+";";
            //预处理SQL 防止注入
            ps = conn.prepareStatement(sql);
            //执行
            flag = ps.execute();
            //关闭流
            ps.close();
            conn.close();  //关闭数据库连接
        } catch (SQLException e) {
            System.out.println("删除数据失败" + e.getMessage());
        }
        return flag;
    }


    /**
     * 删除数据表
     * 如果执行成功则返回false
     * @param tabName
     * @return
     */
    public static boolean dropTable(Activity activity,String tabName){
        boolean flag = true;
        conn = getConnection(activity);  // 首先要获取连接，即连接到数据库
        try {
            String sql = "drop table "+tabName+";";
            //预处理SQL 防止注入
            ps = conn.prepareStatement(sql);
            //执行
            flag = ps.execute();
            //关闭流
            ps.close();
            conn.close();  //关闭数据库连接
        } catch (SQLException e) {
            System.out.println("删除数据失败" + e.getMessage());
        }
        return flag;
    }




    /**
     * 测试方法
     * @param args
     */
    public static void main(String[] args) {
        //建表===========================================
        //表名
//    String tabName = "mytable";
        //表字段
//    String[] tab_fields = {"name","password","sex","age"};
        //创建表
//    createTable(tabName, tab_fields);
        //添加===========================================
        //模拟数据
//    String[] data1 = {"jack","123456","男","25"};
//    String[] data2 = {"tom","456789","女","20"};
//    String[] data3 = {"mark","aaa","哈哈","21"};
        //插入数据
//    insert(tabName, tab_fields, data1);
//    insert(tabName, tab_fields, data2);
//    insert(tabName, tab_fields, data3);
        //查询=============================================
//    String[] q_fileds ={"name","sex"};
//    String[] data4 = {"jack","男"};
//
//    String[] result = query(tabName, q_fileds, data4, tab_fields);
//    for (String string : result) {
//      System.out.println("结果：\t"+string);
//    }
        //删除 清空=============================================
//    delete(tabName);
        //是否存在
//    System.out.println(exitTable("mytable"));
        //删除表
//    System.out.println(dropTable("mytable"));
    }
}


