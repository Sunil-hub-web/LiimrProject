package com.in.apnisevatechinican.modelclass;

public class Login_ModelClass {

    String userid,mobileNo,emailId,userName,password,image,category,subcategory;

    public Login_ModelClass(String userid, String mobileNo, String emailId, String userName,
                            String password,String image,String category,String subcategory) {
        this.userid = userid;
        this.mobileNo = mobileNo;
        this.emailId = emailId;
        this.userName = userName;
        this.password = password;
        this.image = image;
        this.category = category;
        this.subcategory = subcategory;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }
}
