package com.example.riceyrq.thenextday.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.riceyrq.thenextday.Data.LunarDate;
import com.example.riceyrq.thenextday.Data.SolarDate;
import com.example.riceyrq.thenextday.Data.YMD;
import com.example.riceyrq.thenextday.R;
import com.example.riceyrq.thenextday.Util.DataUtil;
import com.example.riceyrq.thenextday.Util.ToastUtil;

import java.util.Calendar;

public class Main extends AppCompatActivity {
    private Spinner yearChos;
    private Spinner monthChos;
    private Spinner dayChos;
    private TextView nextDay;
    private Button start;
    private ArrayAdapter<Integer> yearAdapter;
    private ArrayAdapter<Integer> monthAdapter;
    private ArrayAdapter<Integer> dayAdapter;
    private int year = 0;
    private int month = 0;
    private int day = 0;
    private int dayOfWeek = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        yearChos = (Spinner) findViewById(R.id.year);
        monthChos = (Spinner) findViewById(R.id.month);
        dayChos = (Spinner) findViewById(R.id.day);
        nextDay = (TextView) findViewById(R.id.next);
        start = (Button) findViewById(R.id.start);

        yearAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, DataUtil.getYears());
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearChos.setAdapter(yearAdapter);

        monthAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, DataUtil.getMonths());
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthChos.setAdapter(monthAdapter);

        Calendar ccc = Calendar.getInstance();


        year = ccc.get(Calendar.YEAR);
        yearChos.setSelection(year - 1900);
        Log.e("ymd", "" + year);
        month = ccc.get(Calendar.MONTH);
        month++;
        /*if (month == 13)
            month = 1;*/
        monthChos.setSelection(month - 1);
        Log.e("ymd", "" + month);
        day = ccc.get(Calendar.DATE);
        dayChos.setSelection(day - 1);
        Log.e("ymd", "" + day);

        openDayChoose();
        yearChos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                year = position + 1900;
                openDayChoose();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                yearChos.setSelection(year - 1900);
                openDayChoose();
            }
        });

        monthChos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                month = position + 1;
                openDayChoose();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Calendar calendar = Calendar.getInstance();
                month = calendar.get(Calendar.MONTH);
                monthChos.setSelection(month - 1);
                openDayChoose();
            }
        });

        dayChos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                day = position + 1;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Calendar calendar = Calendar.getInstance();
                day = calendar.get(Calendar.DAY_OF_MONTH);
                dayChos.setSelection(day - 1);
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("ymd", year + "年" + month + "月" + day + "日");
                if (year != 0 && month != 0 && day != 0){
                    SolarDate now = new SolarDate();
                    now.setDay(day);
                    now.setMonth(month);
                    now.setYear(year);
                    SolarDate next = DataUtil.getNextYang(now);
                    dayOfWeek = DataUtil.getDayOfWeek(next);
                    String nextSolar = "明天是" + next.getYear() + "年" + next.getMonth() + "月" + next.getDay() + "日"
                            + YMD.daysOfWeek[dayOfWeek];
                    LunarDate lunarDate = DataUtil.getLunarDay(next);
                    String nextLunar;
                    if (lunarDate == null){
                        nextLunar = "己亥猪年" + YMD.lunarMonths[11] + "月" + YMD.lunarDays[day] + "日";
                    } else
                        nextLunar = DataUtil.getGanZhi(lunarDate.getYear()) + DataUtil.animalsYear(lunarDate.getYear()) + "年" + YMD.lunarMonths[lunarDate.getMonth() - 1] + "月" + YMD.lunarDays[lunarDate.getDay() - 1];
                    String nextSolarHoliday = DataUtil.getSolarHoliday(next);
                    String nextLunarHoliday = DataUtil.getLunarHoliday(next);
                    String nextWeekHoliday = DataUtil.getWeekHoliday(next);
                    nextDay.setText(nextSolar + "\r\n" + nextLunar + "\r\n" + nextSolarHoliday + " " + nextLunarHoliday + " " + nextWeekHoliday);
                } else {
                    ToastUtil.showToast(getApplication(), "请输入完整的日期再点击确认！");
                }
            }
        });


    }

    private void openDayChoose(){
        if (month == 0 || year == 0){
            dayChos.setEnabled(false);
        } else {
            if (DataUtil.isSpecial(year)){
                dayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, DataUtil.getDaysSpecial(month));
                dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dayChos.setAdapter(dayAdapter);
                if(day > YMD.daysSpecial[month - 1]){
                    day = YMD.daysSpecial[month - 1];
                }
            } else {
                dayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, DataUtil.getDaysNormal(month));
                dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dayChos.setAdapter(dayAdapter);
                if(day > YMD.daysNormal[month - 1]){
                    day = YMD.daysNormal[month - 1];
                }
            }
            dayChos.setEnabled(true);
            yearChos.setSelection(year - 1900);
            Log.e("ymd", "" + month);
            monthChos.setSelection(month - 1);
            dayChos.setSelection(day - 1);
        }
    }


}
