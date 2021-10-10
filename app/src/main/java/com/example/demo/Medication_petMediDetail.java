package com.example.demo;

import java.util.Date;
import java.util.Objects;
import java.text.DateFormat;

public class Medication_petMediDetail {

    //for MedicationDetail.kt和MedicationListDetail.java使用.
    //用来get or set判定数据类型以便于获取/设置数据
    //select Pet_id,Medi_product, Medi_purchasedate from Medication where Pet_id = '1001'
    //Pet_id = String, Medi_product = String, Medi_purchasedate = Date

    private String id;
    private String mediproduct;
    private Date medipurchasedate;
    private String accountname;


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getMediproduct() {
        return mediproduct;
    }
    public void setMediproduct(String mediproduct) {
        this.mediproduct = mediproduct;
    }

    public Date getMedipurchasedate() {
        return medipurchasedate;
    }
    public void setMedipurchasedate(Date medipurchasedate) {
        this.medipurchasedate = medipurchasedate;
    }

    public String getAccountname() {
        return accountname;
    }
    public void setAccountname(String accountname) {
        this.accountname = accountname;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id + '\'' +
                ", mediproduct='" + mediproduct + '\'' +
                ", medipurchasedate='" + medipurchasedate + '\'' +
                '}';
    }
}
