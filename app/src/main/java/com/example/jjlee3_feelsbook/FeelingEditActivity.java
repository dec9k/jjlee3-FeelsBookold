package com.example.jjlee3_feelsbook;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class FeelingEditActivity extends Activity {

    private Integer index;

    MyApp app;

    ArrayList<Feeling> feelList;
    private Feeling newFeel;
    private EditText currentComment;
    private EditText currentDay;
    private EditText currentHour;
    private EditText currentMin;

    private Calendar calendar;

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

        currentDay = (EditText) findViewById(R.id.dayText);
        currentHour = (EditText) findViewById(R.id.hourText);
        currentMin = (EditText) findViewById(R.id.minText);

        setDateTexts();
    }

    public void setDateTexts(){
        calendar = GregorianCalendar.getInstance();
        calendar.setTime(newFeel.getDate());

        Integer dofm = calendar.get(Calendar.DAY_OF_MONTH);
        Integer hod = calendar.get(Calendar.HOUR_OF_DAY);
        Integer moh = calendar.get(Calendar.MINUTE);

        currentDay.setText(dofm.toString());
        currentHour.setText(hod.toString());
        currentMin.setText(moh.toString());
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

    public void setnewDate(){

        Integer YEAR = calendar.get(Calendar.YEAR);
        Integer MONTH = calendar.get(Calendar.MONTH);
        Integer DAY = Integer.parseInt(currentDay.getText().toString());

        Integer HOUR = Integer.parseInt(currentHour.getText().toString());
        Integer MIN = Integer.parseInt(currentMin.getText().toString());

        Calendar mycal = new GregorianCalendar(YEAR, MONTH,DAY);
        Integer maximumday = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);

        if (maximumday >= DAY){
            //Inside the zone
        }else{
            //Invalid timezone
            DAY = maximumday;
        }

        if (HOUR >= 24){
            //Make sure it doesn't pass 24 hours
            HOUR = 24;
        }else if (HOUR <= 0){
            HOUR = 0;
            //Make sure it doesn't go to negative integers
        }

        if ((MIN >= 60) || (MIN <= 0)){
            MIN = 0;
        }

        Calendar myCal = Calendar.getInstance();


        Date oldDate = newFeel.getDate();
        oldDate = myCal.getTime();

        Calendar cal = Calendar.getInstance();
        cal.setTime(oldDate);

        cal.set(Calendar.DAY_OF_MONTH, DAY);
        cal.set(Calendar.HOUR_OF_DAY, HOUR);
        cal.set(Calendar.MINUTE, MIN);

        String string = "";


        oldDate = cal.getTime();
        newFeel.setDate(oldDate);
    }

    public void applied(View v){
        Toast.makeText(this, "Applied changes",Toast.LENGTH_SHORT).show();
        //Apply the changes to the thing
        newFeel.setMessage(currentComment.getText().toString());
        setnewDate();
        app.setFeel(feelList);
        app.saveFile();
        finish();
    }

    public void cancelled(View v){
        Toast.makeText(this, "Cancelled",Toast.LENGTH_SHORT).show();
        finish();
    }
}
