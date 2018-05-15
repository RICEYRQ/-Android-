package com.example.riceyrq.thenextday.Data;

public class LunarDate {
    private int year;
    private int month;
    private int day;

    public LunarDate(int year, int month, int day){
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public LunarDate(int month, int day){
        this.month = month;
        this.day = day;
    }

    public LunarDate(){

    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}