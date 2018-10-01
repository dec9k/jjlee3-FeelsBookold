package com.example.jjlee3_feelsbook;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.health.SystemHealthManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;

public class FeelingEditActivity extends Activity {

    private Integer index;
    private static final String FILENAME = "file.sav";

    MyApp app;

    ArrayList<Feeling> feelList;
    private Feeling newFeel;
    private EditText currentComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeling_edit);

        app = (MyApp) getApplicationContext();
        feelList = app.getFeel();
        Integer sessionIndex = getIntent().getIntExtra("THE_INDEX",0);
        setIndex(sessionIndex);
        newFeel = feelList.get(sessionIndex);
        //System.out.println("The INDEX IS : "+ sessionIndex.toString());

        TextView currentFeeling = (TextView) findViewById(R.id.currentFeelingView);
        currentFeeling.setText("Current Feeling: " + newFeel.getFeel());
        currentComment = (EditText) findViewById(R.id.newCommentText);
        currentComment.setText(newFeel.getMessage());
    }

    public void setIndex(Integer index){
        this.index = index;
    }

    public Integer getIndex(){
        return this.index;
    }

    public void deleteButton(View v){
        Toast.makeText(this, "Removed",Toast.LENGTH_SHORT).show();
        feelList.remove(newFeel);
        app.setFeel(feelList);
        app.saveFile();
        finish();
    }

    public void setnewDate(Feeling myfeel){
        /*
        currentYear = (EditText) findViewById(R.id.newYearText);
        currentMonth = (EditText) findViewById(R.id.newMonthText);
        currentDay = (EditText) findViewById(R.id.newDayText);
        ////
        currentHour = (EditText) findViewById(R.id.newHourText);
        currentMin = (EditText) findViewById(R.id.newMinText);
        currentSec = (EditText) findViewById(R.id.newSecText);

        Integer YEAR = currentYear.getText();
        Integer MONTH = currentMonth.getText();
        Integer DAY = currentDay.getText();

        Integer HOUR = currentHour.getText();
        Integer MIN = currentMin.getText();
        Integer SEC = currentSec.getText();


        Calendar mycal = new GregorianCalendar(YEAR, MONTH,1);
        Integer maximumday = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);

        if (maximumday <= DAY){
            //Inside the zone
        }else{
            //Invalid timezone
            DAY = maximumday;
        }

        if (HOUR >= 24){
            //Make sure it doesn't pass 24 hours
            HOUR = 24;
        }

        Date oldDate = newFeel.getDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(oldDate);

        cal.set(Calendar.YEAR, YEAR);
        cal.set(Calendar.MONTH, MONTH-1);
        cal.set(Calendar.DAY_OF_MONTH, DAY);
        //
        cal.set(Calendar.HOUR_OF_DAY, HOUR);
        cal.set(Calendar.MINUTE, MIN);
        cal.set(Calendar.SECOND, SEC);

        oldDate = cal.getTime();
        myfeel.setDate(oldDate);
        */

    }

    public void applied(View v){
        Toast.makeText(this, "Applied changes",Toast.LENGTH_SHORT).show();
        //Apply the changes to the thing
        newFeel.setMessage(currentComment.getText().toString());
        setnewDate(newFeel);
        app.setFeel(feelList);
        app.saveFile();
        finish();
    }

    public void cancelled(View v){
        Toast.makeText(this, "Cancelled",Toast.LENGTH_SHORT).show();
        finish();
    }
}
