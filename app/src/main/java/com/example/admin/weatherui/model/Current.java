package com.example.admin.weatherui.model;


import java.util.List;

public class Current{

    private List<CurrentDatum> data = null;
    private Integer count;

    public List<CurrentDatum> getData() {
        return data;
    }

    public void setData(List<CurrentDatum> data) {
        this.data = data;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
