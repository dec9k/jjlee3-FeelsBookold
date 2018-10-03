package com.example.jjlee3_feelsbook;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class FeelingEditActivity extends Activity {

    private Integer index;

    private MyApp app;

    private ArrayList<Feeling> feelList;
    private Feeling newFeel;
    private EditText currentComment;
    private EditText currentDay;
    private EditText currentHour;
    private EditText currentMin;
    private String dateasISO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeling_edit);

        app = (MyApp) getApplicationContext();
        feelList = app.getFeel();
        Integer sessionIndex = getIntent().getIntExtra("THE_INDEX",0);
        setIndex(sessionIndex);
        newFeel = feelList.get(sessionIndex);

        TextView currentFeeling = (TextView) findViewById(R.id.currentFeelingView);
        currentFeeling.setText("Current Feeling: " + newFeel.getFeel());
        currentComment = (EditText) findViewById(R.id.newCommentText);
        currentComment.setText(newFeel.getMessage());

        currentDay = (EditText) findViewById(R.id.dayText);
        currentHour = (EditText) findViewById(R.id.hourText);
        currentMin = (EditText) findViewById(R.id.minText);

        setDateTexts();
    }

    public Integer ISOsubStrings(Integer begin, Integer end){
        return Integer.parseInt(dateasISO.substring(begin,end));
    }

    public void setDateTexts(){

        dateasISO = newFeel.DateasISO();

        Integer dofm = ISOsubStrings(8,10);
        Integer hod = ISOsubStrings(11,13);
        Integer moh = ISOsubStrings(14,16);

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

    public Boolean setnewDate(){
        Boolean passed;

        Calendar myCal = Calendar.getInstance();
        Date oldDate = newFeel.getDate();
        myCal.setTime(oldDate);

        String dateasISO = newFeel.DateasISO();

        Integer YEAR = ISOsubStrings(0,4);
        Integer MONTH = ISOsubStrings(5,7);
        Integer DAY = Integer.parseInt(currentDay.getText().toString());

        Integer HOUR = Integer.parseInt(currentHour.getText().toString());
        Integer MIN = Integer.parseInt(currentMin.getText().toString());
        Integer SEC = myCal.get(Calendar.SECOND);

        Calendar mycal = new GregorianCalendar(YEAR, MONTH, 0);
        Integer maximumday = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);

        if (maximumday >= DAY){
            //Inside the zone
        }else{
            //Invalid timezone
            DAY = maximumday;
        }

        if ((HOUR >= 24) || (HOUR <= 0)){
            //Make sure it doesn't pass 24 hours nor go under 0
            HOUR = 0;
        }

        if ((MIN >= 60) || (MIN <= 0)){
            //Make sure it doesn't pass 60 minutes nor go under 0
            MIN = 0;
        }

        TimeZone tz = TimeZone.getTimeZone("UTC");
        Calendar cal = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//
        df.setTimeZone(tz);
        String tradeDate;
        tradeDate = changeString(YEAR) + "-" + changeString(MONTH) + "-" + changeString(DAY) + " " + changeString(HOUR) + ":" + changeString(MIN) + ":" + changeString(SEC);
        try {
            cal.setTime(df.parse(tradeDate));
            passed = true;
            oldDate = cal.getTime();
            newFeel.setDate(oldDate);
        } catch(ParseException e) {
            passed = false;
        }

        return passed;
    }

    public String changeString(Integer atime){
        String at = atime.toString();
        if (at.length() == 1){
            at = "0"+at;
        }
        return at;
    }

    public void applied(View v){

        //Apply the changes to the thing
        Boolean fixed = setnewDate();
        if (fixed == true){
            Toast.makeText(this, "Applied changes",Toast.LENGTH_SHORT).show();
            newFeel.setMessage(currentComment.getText().toString());
            app.setFeel(feelList);
            app.saveFile();
        }else{
            Toast.makeText(this, "Something went wrong; failed to save",Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    public void cancelled(View v){
        Toast.makeText(this, "Cancelled",Toast.LENGTH_SHORT).show();
        finish();
    }
}
