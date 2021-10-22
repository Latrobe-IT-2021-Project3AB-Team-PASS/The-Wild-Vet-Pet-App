package com.example.demo;

import java.util.Date;
import java.util.Objects;
import java.text.DateFormat;

public class Medication_petMediDetail {

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
