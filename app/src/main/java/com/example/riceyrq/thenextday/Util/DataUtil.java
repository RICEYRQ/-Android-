package com.example.riceyrq.thenextday.Util;

import android.util.Log;

import com.example.riceyrq.thenextday.Data.LunarDate;
import com.example.riceyrq.thenextday.Data.LunarHoliday;
import com.example.riceyrq.thenextday.Data.SolarDate;
import com.example.riceyrq.thenextday.Data.SolarHoliday;
import com.example.riceyrq.thenextday.Data.WeekHoliday;
import com.example.riceyrq.thenextday.Data.YMD;

import java.util.ArrayList;
import java.util.List;

public class DataUtil {
    public static List<Integer> getYears(){
        List<Integer> list = new ArrayList<>();
        for (int i = 1900; i <= 2100; i++){
            list.add(i);
        }
        return list;
    }

    public static List<Integer> getMonths(){
        List<Integer> list = new ArrayList<>();
        for(int i = 1; i <= 12; i++){
            list.add(i);
        }
        return list;
    }

    public static List<Integer> getDaysNormal(int month){
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= YMD.daysNormal[month - 1]; i++){
            list.add(i);
        }
        return list;
    }

    public static List<Integer> getDaysSpecial(int month){
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= YMD.daysSpecial[month - 1]; i++){
            list.add(i);
        }
        return list;
    }

    //是否阳历闰年
    public static boolean isSpecial(int year){
        if (year % 100 == 0){
            if (year % 400 == 0)
                return true;
            else
                return false;
        } else {
            if (year % 4 == 0)
                return true;
            else
                return false;
        }
    }

    //获得阳历下一天
    public static SolarDate getNextYang(SolarDate now){
        int year = now.getYear();
        int month = now.getMonth();
        int day = now.getDay();
        SolarDate next = new SolarDate();
        if (month == 12 && day == 31){
            next.setYear(year + 1);
            next.setMonth(1);
            next.setDay(1);
            return next;
        }
        if (isSpecial(year)){
            if (day < YMD.daysSpecial[month - 1]){
                next.setYear(year);
                next.setMonth(month);
                next.setDay(day + 1);
                return next;
            } else {
                next.setYear(year);
                next.setMonth(month + 1);
                next.setDay(1);
                return next;
            }
        } else {
            if (day < YMD.daysNormal[month - 1]){
                next.setYear(year);
                next.setMonth(month);
                next.setDay(day + 1);
                return next;
            } else {
                next.setYear(year);
                next.setMonth(month + 1);
                next.setDay(1);
                return next;
            }
        }

    }

    //获得两个阳历天数差值
    public static int getDateCha(SolarDate solarDate1, SolarDate solarDate2){
        return  Math.abs(getDateChaWithBeginning(solarDate1) - getDateChaWithBeginning(solarDate2));
    }

    //某天到公元元年1月1日的天数差值
    public static int getDateChaWithBeginning(SolarDate solarDate){
        int cha = 0;
        if (isSpecial(solarDate.getYear())){
            cha += YMD.monthSpecial[solarDate.getMonth() - 1] + solarDate.getDay();
        } else {
            cha += YMD.monthNormal[solarDate.getMonth() - 1] + solarDate.getDay();
        }
        cha += (solarDate.getYear() - 1) * 365 + (solarDate.getYear() - 1) / 4 - (solarDate.getYear() - 1) / 100 + (solarDate.getYear() - 1) / 400;
        return cha;
    }

    //周几？
    public static int getDayOfWeek(SolarDate solarDate){
        return getDateCha(solarDate, new SolarDate(1900, 1, 1)) % 7;
    }

    //获得当前是第几个星期几   具体星期几用上面的函数算
    public static int getDaythInWeek(SolarDate solarDate){
        return (solarDate.getDay() - 1) / 7 + 1;
    }

    //从阳历获得阴历
    public static LunarDate getLunarDay(SolarDate solarDate){
        if (solarDate.getYear() == 1900 && solarDate.getMonth() == 1 && solarDate.getDay() < 31)
            return null;
        long[] nongDate = new long[7];
        LunarDate lunarDate = new LunarDate();
        int i = 0, temp = 0, leap = 0;
        //Date baseDate = new GregorianCalendar(0 + 1900, 0, 31).getTime();
        //Date objDate = new GregorianCalendar(y, m - 1, d).getTime();
        //long offset = (objDate.getTime() - baseDate.getTime()) / 86400000L;
        /*nongDate[5] = offset + 40;
        nongDate[4] = 14;*/
        int offset = getDateCha(solarDate, new SolarDate(1900, 1, 31));
        for (i = 1900; i < 2100 && offset > 0; i++) {
            temp = lunarYearDays(i);
            offset -= temp;
            nongDate[4] += 12;
        }
        if (offset < 0) {
            offset += temp;
            i--;
            nongDate[4] -= 12;
        }
        nongDate[0] = i;
        lunarDate.setYear(i);
        nongDate[3] = i - 1864;
        leap = leapMonth(i); // 闰哪个月
        nongDate[6] = 0;
        for (i = 1; i < 13 && offset > 0; i++) {
            // 闰月
            if (leap > 0 && i == (leap + 1) && nongDate[6] == 0) {
                --i;
                nongDate[6] = 1;
                temp = leapDays((int) nongDate[0]);
            } else {
                temp = monthDays((int) nongDate[0], i);
            }
            // 解除闰月
            if (nongDate[6] == 1 && i == (leap + 1))
                nongDate[6] = 0;
            offset -= temp;
            if (nongDate[6] == 0)
                nongDate[4]++;
        }
        if (offset == 0 && leap > 0 && i == leap + 1) {
            if (nongDate[6] == 1) {
                nongDate[6] = 0;
            } else {
                nongDate[6] = 1;
                --i;
                --nongDate[4];
            }
        }
        if (offset < 0) {
            offset += temp;
            --i;
            --nongDate[4];
        }
        nongDate[1] = i;
        lunarDate.setMonth(i);
        nongDate[2] = offset + 1;
        lunarDate.setDay(offset + 1);
        //return nongDate;
        return lunarDate;
    }

    //查看今天阳历是否有节日
    public static String getSolarHoliday(SolarDate solarDate){
        for (int i = 0; i < YMD.solarHolidays.length; i++){
            SolarHoliday solarHoliday = YMD.solarHolidays[i];
            /*Log.e("ymd", "" + solarHoliday.getMonth() + " " + solarHoliday.getDay());
            Log.e("ymd", "" + solarDate.getMonth() + " " + solarDate.getDay());*/
            if (solarDate.getMonth() == solarHoliday.getMonth() && solarDate.getDay() == solarHoliday.getDay()){
                //Log.e("ymd++", "" + solarHoliday.getMonth() + " " + solarHoliday.getDay());
                return solarHoliday.getHoliday();
            }
        }
        return "";
    }

    //查看今天阴历是否有节日
    public  static String getLunarHoliday(LunarDate lunarDate){
        if (lunarDate == null){
            return "";
        }
        for (int i = 0; i < YMD.lunarHolidays.length; i++){
            LunarHoliday lunarHoliday = YMD.lunarHolidays[i];
            if (lunarDate.getMonth() == lunarHoliday.getMonth() && lunarDate.getDay() == lunarHoliday.getDay()){
                return lunarHoliday.getHoliday();
            }
        }
        return "";
    }

    //查看今天阴历是否有节日
    public static String getLunarHoliday(SolarDate solarDate){
        LunarDate lunarDate = getLunarDay(solarDate);
        LunarDate lunarDate1 = getLunarDay(getNextYang(solarDate));
        if (getLunarHoliday(lunarDate1).equals("春节")){
            return "除夕";
        } else {
            return getLunarHoliday(lunarDate);
        }

    }

    //查看今天根据周几是否有节日
    public static String getWeekHoliday(SolarDate solarDate){
        for (int i = 0; i < YMD.weekHolidays.length; i++){
            WeekHoliday weekHoliday = YMD.weekHolidays[i];
            if (weekHoliday.getMonth() == solarDate.getMonth() && weekHoliday.getDayOfWeek() == getDayOfWeek(solarDate)
                    && weekHoliday.getDayth() == getDaythInWeek(solarDate)){
                return weekHoliday.getHoliday();
            }
        }
        return "";
    }

    //返回这年天数
    public static int lunarYearDays(int year){
        int i, sum = 348;
        for (i = 0x8000; i > 0x8; i >>= 1) {
            if ((YMD.lunarInfo[year - 1900] & i) != 0)
                sum += 1;
        }
        return (sum + leapDays(year));
    }

    //返回这年闰月的天数
    public static int leapDays(int year){
        if (leapMonth(year) != 0) {
            if ((YMD.lunarInfo[year - 1900] & 0x10000) != 0)
                return 30;
            else
                return 29;
        } else
            return 0;
    }

    //农历闰哪个月
    public static int leapMonth(int year){
        return (int) (YMD.lunarInfo[year - 1900] & 0xf);
    }

    //传回农历 year年month月的总天数
    public static int monthDays(int year, int month){
        if ((YMD.lunarInfo[year - 1900] & (0x10000 >> month)) == 0)
            return 29;
        else
            return 30;
    }

    //某年生肖
    public static String animalsYear(int year){
        return YMD.Animals[(year - 4) % 12];
    }

    //根据月日offset传回天干地支，0=甲子年
    public static String getGanZhi(int year){
        int num = year - 1900 + 36;
        return (YMD.Gan[num % 10] + YMD.Zhi[num % 12]);
    }
}