package com.example.foodorder.Model;

public class Feedback {
    private String name;
    private String phone;
    private String email;
    private String comment;

    private String sendFbDate;

    public Feedback() {
    }

    public Feedback(String name, String phone, String email, String comment, String sendFbDate) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.comment = comment;
        this.sendFbDate = sendFbDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSendFbDate() {
        return sendFbDate;
    }

    public void setSendFbDate(String sendFbDate) {
        this.sendFbDate = sendFbDate;
    }
}
