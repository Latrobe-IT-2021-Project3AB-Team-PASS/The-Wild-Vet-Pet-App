package com.example.demo;

import java.util.Date;
import java.util.Objects;
import java.text.DateFormat;

public class ParasitePrevention_petPPDetail {

    private String id;
    private Date ppdate;
    private String ppproduct;
    private String ppfuequency;
    private String accountname;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public Date getPpdate() {
        return ppdate;
    }
    public void setPpdate(Date ppdate) {
        this.ppdate = ppdate;
    }

    public String getPpproduct() {
        return ppproduct;
    }
    public void setPpproduct(String ppproduct) {
        this.ppproduct = ppproduct;
    }

    public String getPpfuequency() {
        return ppfuequency;
    }
    public void setPpfuequency(String ppfuequency) {
        this.ppfuequency = ppfuequency;
    }

    public String getAccountname() {
        return accountname;
    }
    public void setAccountname(String accountname) {
        this.accountname = accountname;
    }

}

