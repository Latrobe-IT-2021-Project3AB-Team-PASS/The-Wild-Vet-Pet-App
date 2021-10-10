package com.example.demo;

import java.util.Date;
import java.util.Objects;
import java.text.DateFormat;

public class Vaccination_petsVaccDetail {

    //for VaccinationDetail.ket和VaccinationListDetail.java使用.
    //用来get or set判定数据类型以便于获取/设置数据
    //select Pet_id,Vacc_type,Vacc_date,Vacc_productname,Vacc_name,Vacc_dueday from Vaccination where Pet_id = 'returned pet id';
    //Pet_id = String, Vacc_type = String, Vacc_date = Date, Vacc_productname = String, Vacc_name = String, Vacc_dueday = DATE

    private String id;
    private String vacctype;
    private Date vaccdate;
    private String vaccproductname;
    private String vaccname;
    private Date vaccdueday;
    private String accountname;
    //private Integer id;
    //private String sex;
    //private Integer age;
    //private String type;


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getVacctype() {
        return vacctype;
    }
    public void setVacctype(String vacctype) {
        this.vacctype = vacctype;
    }

    public Date getVaccdate() {
        return vaccdate;
    }
    public void setVaccdate(Date vaccdate) {
        this.vaccdate = vaccdate;
    }

    public String getVaccproductname() {
        return vaccproductname;
    }
    public void setVaccproductname(String vaccproductname) {
        this.vaccproductname = vaccproductname;
    }

    public String getVaccname() {
        return vaccname;
    }
    public void setVaccname(String vaccname) {
        this.vaccname = vaccname;
    }

    public Date getVaccdueday() {
        return vaccdueday;
    }
    public void setVaccdueday(Date vaccdueday) {
        this.vaccdueday = vaccdueday;
    }

    public String getAccountname() {
        return accountname;
    }
    public void setAccountname(String accountname) {
        this.accountname = accountname;
    }




    //    private String id;
    //    private String vacctype;
    //    private Date date;
    //    private String vaccproductname;
    //    private String vaccname;
    //    private Date duedate;
    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id + '\'' +
                ", vacctype='" + vacctype + '\'' +
                ", vaccdate='" + vaccdate + '\'' +
                ", vaccproductname='" + vaccproductname + '\'' +
                ", vaccname='" + vaccname + '\'' +
                ", vaccdueday='" + vaccdueday + '\'' +
                '}';
    }

    /*public String toString() {
        return "Pet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", type='" + type + '\'' +
                ", image='" + image + '\'' +
                '}';
    }*/
}
