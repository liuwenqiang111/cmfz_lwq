package com.baizhi.entity;

public class Month {

    private String month;
    private Integer count;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Month() {
    }

    public Month(String month, Integer count) {
        this.month = month;
        this.count = count;
    }

    @Override
    public String toString() {
        return "Month{" +
                "month='" + month + '\'' +
                ", count=" + count +
                '}';
    }
}
