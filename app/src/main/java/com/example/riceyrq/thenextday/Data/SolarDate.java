package com.example.riceyrq.thenextday.Data;

public class SolarDate {
    private int year;
    private int month;
    private int day;

    public SolarDate(int year, int month, int day){
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public SolarDate(int month, int day){
        this.month = month;
        this.day = day;
    }

    public SolarDate(){

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