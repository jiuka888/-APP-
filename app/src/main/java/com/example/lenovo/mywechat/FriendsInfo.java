package com.example.lenovo.mywechat;

/**
 * Created by dell on 2020-04-20.
        */

public class FriendsInfo {//bean类
    int imageId;
    String name;
    String info;
    String time;

    public FriendsInfo(int imageId, String name, String info, String time) {
        this.imageId = imageId;
        this.name = name;
        this.info = info;
        this.time = time;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
