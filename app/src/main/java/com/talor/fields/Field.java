package com.talor.fields;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Field implements Serializable {

    private double lng = 0.0;
    private double lat = 0.0;
    private String pid = "";
    private String name = "";
    private String address = "";
    private float rating;
    private List<String> usersUidList;
    private String parkImage1; // link
    private String water = "";
    private String bags = "";
    private String benches = "";
    public int count_user;


    public Field() {
        usersUidList = new ArrayList<>();
        count_user = 0;
    }

    public String getBags() {
        return bags;
    }

    public Field setBags(String bags) {
        this.bags = bags;
        return this;
    }

    public double getLng() {
        return lng;
    }

    public Field setLng(double lng) {
        this.lng = lng;
        return this;
    }

    public double getLat() {
        return lat;
    }

    public Field setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public String getPid() {
        return pid;
    }

    public Field setPid(String pid) {
        this.pid = pid;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Field setAddress(String address) {
        this.address = address;
        return this;
    }

    public void addUserToField(String uid) {
        if (!usersUidList.contains(uid)) {
            usersUidList.add(uid);
            count_user++;
        }

    }

    public void removeUserFromField(String uid) {
        usersUidList.remove(uid);
        count_user--;
    }

    public List<String> getUsersUidList() {
        return usersUidList;
    }

    public Field setUsersUidList(List<String> usersUidList) {
        this.usersUidList = usersUidList;
        return this;
    }

    public String getParkImage1() {
        return parkImage1;
    }

    public Field setParkImage1(String parkImage1) {
        this.parkImage1 = parkImage1;
        return this;
    }

    public float getRating() {
        return rating;
    }

    public Field setRating(float rating) {
        this.rating = rating;
        return this;
    }

    public String getWater() {
        return water;
    }

    public Field setWater(String water) {
        this.water = water;
        return this;
    }

    public String getBenches() {
        return benches;
    }

    public Field setBenches(String benches) {
        this.benches = benches;
        return this;
    }

    public String getName() {
        return name;
    }

    public Field setName(String name) {
        this.name = name;
        return this;
    }

    public int getCount_user() {
        return count_user;
    }

    public Field setCount_user(int count_user) {
        this.count_user = count_user;
        return this;
    }
}
