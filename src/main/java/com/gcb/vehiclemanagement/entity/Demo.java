package com.gcb.vehiclemanagement.entity;

public class Demo {
    private int id;
    private String test;


    public Demo() {
        super();
    }

    public Demo(int id, String test) {
        this.id = id;
        this.test = test;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    @Override
    public String toString() {
        return "Demo{" +
                "id='" + id + '\'' +
                ", test='" + test + '\'' +
                '}';
    }
}
