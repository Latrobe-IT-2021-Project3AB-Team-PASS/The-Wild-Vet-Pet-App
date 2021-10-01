package com.example.demo;

public class AccountSetting_List {

    //for AccountSetting.ket和VaccinationListDetail.java使用.
    //用来get or set判定数据类型以便于获取/设置数据
    //select Account_username,Account_password,Account_nameTitle,Account_fullname,Account_email,Account_address,Account_phone from Account where Account_username = 'username';
    //Account_username = String, Account_password = String, Account_nameTitle = String,
    //Account_fullname = String, Account_email = String, Account_address = String,
    //Account_phone = String

    private String username;
    private String password;
    private String nametitle;
    private String fullname;
    private String email;
    private String address;
    private String phone;



    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getNametitle() {
        return nametitle;
    }
    public void setNametitle(String nametitle) {
        this.nametitle = nametitle;
    }

    public String getFullname() {
        return fullname;
    }
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String toString() {
        return "Account{" +
                "username=" + username + '\'' +
                ", password='" + password + '\'' +
                ", nametitle='" + nametitle + '\'' +
                ", fullname='" + fullname + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
