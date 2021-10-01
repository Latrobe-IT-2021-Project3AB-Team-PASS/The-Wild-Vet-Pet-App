package com.example.demo;

import java.util.Date;
import java.util.Objects;

public class ParasitePrevention_pet {

    //for ParasitePrevention.kt and ParasitePreventionList.java使用.
    //用来get or set判定数据类型以便于获取/设置数据
    //select Pet_id,Pet_image,Pet_name from Pet where Account_username = 'login account';
    //Pet_id = String, Pet_image = null for right now,  Pet_name = String

    private String id;
    private String name;
    private String image;

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

    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", image='" + image + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
