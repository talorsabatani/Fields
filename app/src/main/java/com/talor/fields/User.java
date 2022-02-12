package com.talor.fields;

import java.io.Serializable;

public class User implements Serializable {

    private  String uid ;
    private  String name = "";
    private  String nickName = "";
    private  String phoneNumber = "";
    private  String status = "";
    private  String favoriteField = "";
    private double lastLng=0.0;
    private double lastLat=0.0;
    private String position = "";
    private String gender = "";
    private String age;
    private String about="";
    private String imageUrl;

    public User() {
    }

    public String getUid() {
        return uid;
    }

    public User setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public User setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getFavoriteField() {
        return favoriteField;
    }

    public User setFavoriteField(String favoriteField) {
        this.favoriteField = favoriteField;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public User setStatus(String status) {
        this.status = status;
        return this;
    }

    public double getLastLng() {
        return lastLng;
    }

    public User setLastLng(double lastLng) {
        this.lastLng = lastLng;
        return this;
    }

    public double getLastLat() {
        return lastLat;
    }

    public User setLastLat(double lastLat) {
        this.lastLat = lastLat;
        return this;
    }

    public String getPosition() {
        return position;
    }

    public User setPosition(String position) {
        this.position = position;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public User setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public String getAge() {
        return age;
    }

    public User setAge(String age) {
        this.age = age;
        return this;
    }

    public String getAbout() {
        return about;
    }

    public User setAbout(String about) {
        this.about = about;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public User setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
    public String getNickName() {
        return nickName;
    }

    public User setNickName(String nickName) {
        this.nickName = nickName;
        return this;
    }


}
