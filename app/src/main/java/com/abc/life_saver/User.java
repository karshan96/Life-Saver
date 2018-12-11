package com.abc.life_saver;

import java.util.Date;

public class User {
    private String name;
    private String email;
    private String password;
    private int contact;
    private String gender;
    private Date dob;
    private String bloodGroup;

    public User(){

    }

    public User(String name, String email, String password, int contact, String gender, Date dob, String bloodGroup) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.contact = contact;
        this.gender = gender;
        this.dob = dob;
        this.bloodGroup = bloodGroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getContact() {
        return contact;
    }

    public void setContact(int contact) {
        this.contact = contact;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }
}
