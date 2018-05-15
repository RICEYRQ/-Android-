package com.example.riceyrq.thenextday.Data;

public class LunarHoliday {
    private int month;//阴历月份
    private int day;//阴历日子
    private String holiday;

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getHoliday() {
        return holiday;
    }

    public void setHoliday(String holiday) {
        this.holiday = holiday;
    }


    public LunarHoliday(int month, int day, String holiday){
        this.month = month;
        this.day = day;
        this.holiday = holiday;
    }
}