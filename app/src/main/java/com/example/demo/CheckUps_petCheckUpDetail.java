package com.example.demo;

import java.util.Date;
import java.util.Objects;
import java.text.DateFormat;

public class CheckUps_petCheckUpDetail {

    //for CheckUpsDetail.CheckUpsListDetail.java使用.
    //用来get or set判定数据类型以便于获取/设置数据
    //select Pet_id,Vet_fullname,Checkups_type,Checkups_date,Checkups_notes from Checkups where Pet_id = 'returned pet id';
    //Pet_id = String, Vet_fullname = String, Checkups_type = String, Checkups_date = Date, Checkups_notes = String

    private String id;
    private String vetfullname;
    private String checkupstype;
    private Date checkupsdate;
    private String checkupsnotes;

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
