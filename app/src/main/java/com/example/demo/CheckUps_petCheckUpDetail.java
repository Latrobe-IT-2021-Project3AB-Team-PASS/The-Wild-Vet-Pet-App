package com.example.demo;

import java.util.Date;
import java.util.Objects;
import java.text.DateFormat;

public class CheckUps_petCheckUpDetail {

    private String id;
    private String vetfullname;
    private String checkupstype;
    private Date checkupsdate;
    private String checkupsnotes;
    private String accountname;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getVetfullname() {
        return vetfullname;
    }
    public void setVetfullname(String vetfullname) {
        this.vetfullname = vetfullname;
    }

    public String getCheckupstype() {
        return checkupstype;
    }
    public void setCheckupstype(String checkupstype) {
        this.checkupstype = checkupstype;
    }

    public Date getCheckupsdate() {
        return checkupsdate;
    }
    public void setCheckupsdate(Date checkupsdate) {
        this.checkupsdate = checkupsdate;
    }

    public String getCheckupsnotes() {
        return checkupsnotes;
    }
    public void setCheckupsnotes(String checkupsnotes) {
        this.checkupsnotes = checkupsnotes;
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
                ", vetfullname='" + vetfullname + '\'' +
                ", checkupstype='" + checkupstype + '\'' +
                ", checkupsdate='" + checkupsdate + '\'' +
                ", checkupsnotes='" + checkupsnotes + '\'' +
                '}';
    }
}
