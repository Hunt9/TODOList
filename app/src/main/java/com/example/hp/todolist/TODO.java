package com.example.hp.todolist;

/**
 * Created by hp on 1/14/2017.
 */

public class TODO {

    private String name;
    private String date;
    private String description;
    private String username;
    private String key;


    public TODO()
    {}

    public TODO(String name, String date, String description,String username,String key)
    {
        this.name = name;
        this.date = date;
        this.description = description;
        this.username = username;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getUsername() {
        return username;
    }

    public String getKey() {
        return key;
    }

    public String setKey(String key) {
        this.key = key;
        return key;
    }
}
