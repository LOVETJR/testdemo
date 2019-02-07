package com.example.testdemo.module;

/**
 * @author Created by 楚天佳
 * @date 2019/2/7 15:53
 */
public class User {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User(String name) {
        this.name = name;
    }

    public String getDescription() {
        return "This is " + name;
    }
}
