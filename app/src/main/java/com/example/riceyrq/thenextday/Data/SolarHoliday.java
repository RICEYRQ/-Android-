package com.example.riceyrq.thenextday.Data;

public class SolarHoliday {
    private int month;//阳历月份
    private int day;//阳历日子
    private String holiday;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getHoliday() {
        return holiday;
    }

    public void setHoliday(String holiday) {
        this.holiday = holiday;
    }


    public SolarHoliday(int month, int day, String holiday){
        this.month = month;
        this.day = day;
        this.holiday = holiday;
    }
}