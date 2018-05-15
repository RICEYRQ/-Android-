package com.example.riceyrq.thenextday.Data;

public class WeekHoliday {
    private int month;
    private int dayth;//第几个
    private int dayOfWeek;//星期几
    private String holiday;//节日

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDayth() {
        return dayth;
    }

    public void setDayth(int dayth) {
        this.dayth = dayth;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getHoliday() {
        return holiday;
    }

    public void setHoliday(String holiday) {
        this.holiday = holiday;
    }



    public WeekHoliday(int month, int dayth, int dayOfWeek, String holiday){
        this.month = month;
        this.dayth = dayth;
        this.dayOfWeek = dayOfWeek;
        this.holiday = holiday;
    }
}