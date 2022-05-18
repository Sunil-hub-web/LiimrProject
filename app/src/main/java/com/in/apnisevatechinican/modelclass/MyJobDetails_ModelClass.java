package com.in.apnisevatechinican.modelclass;

public class MyJobDetails_ModelClass {

    String id,bookingId,category_Name,coustomer_Name,status,price,mobile,address,address1,
            subtotal,igst,cgst,create_user_id,verification_pin,pin_verification_status,work_status,work_details,
            extra_amt,payment_status,book_pay_status,online_book_pay_id,pay_type,created_at;

    public MyJobDetails_ModelClass(String id, String bookingId, String category_Name, String coustomer_Name,
                                   String status, String price, String mobile, String address, String address1,
                                   String subtotal, String igst, String cgst, String create_user_id,
                                   String verification_pin, String pin_verification_status, String work_status,
                                   String work_details, String extra_amt, String payment_status,
                                   String book_pay_status, String online_book_pay_id, String pay_type,String created_at) {
        this.id = id;
        this.bookingId = bookingId;
        this.category_Name = category_Name;
        this.coustomer_Name = coustomer_Name;
        this.status = status;
        this.price = price;
        this.mobile = mobile;
        this.address = address;
        this.address1 = address1;
        this.subtotal = subtotal;
        this.igst = igst;
        this.cgst = cgst;
        this.create_user_id = create_user_id;
        this.verification_pin = verification_pin;
        this.pin_verification_status = pin_verification_status;
        this.work_status = work_status;
        this.work_details = work_details;
        this.extra_amt = extra_amt;
        this.payment_status = payment_status;
        this.book_pay_status = book_pay_status;
        this.online_book_pay_id = online_book_pay_id;
        this.pay_type = pay_type;
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getCategory_Name() {
        return category_Name;
    }

    public void setCategory_Name(String category_Name) {
        this.category_Name = category_Name;
    }

    public String getCoustomer_Name() {
        return coustomer_Name;
    }

    public void setCoustomer_Name(String coustomer_Name) {
        this.coustomer_Name = coustomer_Name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getIgst() {
        return igst;
    }

    public void setIgst(String igst) {
        this.igst = igst;
    }

    public String getCgst() {
        return cgst;
    }

    public void setCgst(String cgst) {
        this.cgst = cgst;
    }

    public String getCreate_user_id() {
        return create_user_id;
    }

    public void setCreate_user_id(String create_user_id) {
        this.create_user_id = create_user_id;
    }

    public String getVerification_pin() {
        return verification_pin;
    }

    public void setVerification_pin(String verification_pin) {
        this.verification_pin = verification_pin;
    }

    public String getPin_verification_status() {
        return pin_verification_status;
    }

    public void setPin_verification_status(String pin_verification_status) {
        this.pin_verification_status = pin_verification_status;
    }

    public String getWork_status() {
        return work_status;
    }

    public void setWork_status(String work_status) {
        this.work_status = work_status;
    }

    public String getWork_details() {
        return work_details;
    }

    public void setWork_details(String work_details) {
        this.work_details = work_details;
    }

    public String getExtra_amt() {
        return extra_amt;
    }

    public void setExtra_amt(String extra_amt) {
        this.extra_amt = extra_amt;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public String getBook_pay_status() {
        return book_pay_status;
    }

    public void setBook_pay_status(String book_pay_status) {
        this.book_pay_status = book_pay_status;
    }

    public String getOnline_book_pay_id() {
        return online_book_pay_id;
    }

    public void setOnline_book_pay_id(String online_book_pay_id) {
        this.online_book_pay_id = online_book_pay_id;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
