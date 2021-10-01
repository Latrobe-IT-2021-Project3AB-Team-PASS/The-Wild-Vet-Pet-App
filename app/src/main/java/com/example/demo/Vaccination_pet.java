package com.example.demo;

import java.util.Date;
import java.util.Objects;

public class Vaccination_pet {

    //for Vaccination.ket和VaccinationList.java使用.
    //用来get or set判定数据类型以便于获取/设置数据
    //select Pet_id,Pet_image,Pet_name from Pet where Account_username = 'login account';
    //Pet_id = String, Pet_image = null for right now,  Pet_name = String

    private String id;
    private String name;
    private String image;
    private Date date;
    //private Integer id;
    //private String sex;
    //private Integer age;
    //private String type;


    /*public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }*/

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }



    /*public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }*/



    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", image='" + image + '\'' +
                ", name='" + name + '\'' +
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
