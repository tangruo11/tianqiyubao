package com.example.tianqiyubao.db;

public class DataBaseBean {
    //数据库必须要有主键
    private int id;
    private String city;
    private String content;

    public DataBaseBean(int id, String city, String content) {
        this.id = id;
        this.city = city;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
