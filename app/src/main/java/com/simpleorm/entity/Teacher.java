package com.simpleorm.entity;

import java.io.Serializable;

/**
 * Created by guofeng
 * on 2017/6/21.
 */

public class Teacher implements Serializable {


    private String name;

    private int age;

    private String address;

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
