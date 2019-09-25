package com.qf.freemarker.entity;

import java.util.Date;

public class Student {
    private Integer id;
    private String name;
    private Integer age;
    private Date entryDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Student() {
    }

    public Student(Integer id, String name, Integer age, Date entryDate) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.entryDate = entryDate;
    }
}
