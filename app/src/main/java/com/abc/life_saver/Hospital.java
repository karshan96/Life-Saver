package com.abc.life_saver;

public class Hospital {
    private String hosName;
    private String hosEmail;
    private String hosPassword;
    private int hosContact;
    private String hosAddress;
    private String hosChiefName;
    private int hosChiefContact;

    public Hospital(){

    }

    public Hospital(String hosName, String hosEmail, String hosPassword, int hosContact, String hosAddress, String hosChiefName, int hosChiefContact) {
        this.hosName = hosName;
        this.hosEmail = hosEmail;
        this.hosPassword = hosPassword;
        this.hosContact = hosContact;
        this.hosAddress = hosAddress;
        this.hosChiefName = hosChiefName;
        this.hosChiefContact = hosChiefContact;
    }

    public int getHosChiefContact() {
        return hosChiefContact;
    }

    public void setHosChiefContact(int hosChiefContact) {
        this.hosChiefContact = hosChiefContact;
    }

    public String getHosName() {
        return hosName;
    }

    public void setHosName(String hosName) {
        this.hosName = hosName;
    }

    public String getHosEmail() {
        return hosEmail;
    }

    public void setHosEmail(String hosEmail) {
        this.hosEmail = hosEmail;
    }

    public String getHosPassword() {
        return hosPassword;
    }

    public void setHosPassword(String hosPassword) {
        this.hosPassword = hosPassword;
    }

    public int getHosContact() {
        return hosContact;
    }

    public void setHosContact(int hosContact) {
        this.hosContact = hosContact;
    }

    public String getHosAddress() {
        return hosAddress;
    }

    public void setHosAddress(String hosAddress) {
        this.hosAddress = hosAddress;
    }

    public String getHosChiefName() {
        return hosChiefName;
    }

    public void setHosChiefName(String hosChiefName) {
        this.hosChiefName = hosChiefName;
    }
}
