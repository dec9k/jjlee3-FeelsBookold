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


/**
 * FeelingEditActivity class, provides the UI of the application
 * It also applies changes to the given Feeling class Date & Comments
 */
public class FeelingEditActivity extends Activity {

    private MyApp app;

    private ArrayList<Feeling> feelList;
    private Feeling newFeel;
    private EditText currentComment;
    private EditText currentDay;
    private EditText currentHour;
    private EditText currentMin;
    private String dateasISO;

    /**
     * onCreate method starts when the Activity is first created
     * Gets the index of the selected Feeling and sets the variables needed in the future
     * @param savedInstanceState Bundle type
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeling_edit);

        app = (MyApp) getApplicationContext();
        feelList = app.getFeel();
        Integer sessionIndex = getIntent().getIntExtra("THE_INDEX",0);
        newFeel = feelList.get(sessionIndex);

        TextView currentFeeling = (TextView) findViewById(R.id.currentFeelingView);
        currentFeeling.setText("Current Feeling: " + newFeel.getFeel());
        currentComment = (EditText) findViewById(R.id.newCommentText);
        currentComment.setText(newFeel.getMessage());

        currentDay = (EditText) findViewById(R.id.dayText);
        currentHour = (EditText) findViewById(R.id.hourText);
        currentMin = (EditText) findViewById(R.id.minText);

        setDisplayTexts();
    }

    /**
     * ISOsubStrings returns the substring of the date in ISO format then formatted into an Integer
     * @param begin
     * @param end
     * @return
     */
    public Integer ISOsubStrings(Integer begin, Integer end){
        return Integer.parseInt(dateasISO.substring(begin,end));
    }

    /**
     * setDisplayTexts sets the Display UI with the correct information of the Feeling class
     */
    public void setDisplayTexts(){

        dateasISO = newFeel.DateasISO();

        Integer dofm = ISOsubStrings(8,10);
        Integer hod = ISOsubStrings(11,13);
        Integer moh = ISOsubStrings(14,16);

        TextView isoDisplay = (TextView) findViewById(R.id.isoTime);
        isoDisplay.setText(dateasISO);

        currentDay.setText(dofm.toString());
        currentHour.setText(hod.toString());
        currentMin.setText(moh.toString());
    }

    /**
     * deleteButton Deletes the given Feeling class from the ArrayList and saves the File
     * It invokes when 'Delete' button is pressed and then returns back to previous Activity
     * @param v View type (Delete button)
     */
    public void deleteButton(View v){
        Toast.makeText(this, "Removed",Toast.LENGTH_SHORT).show();
        feelList.remove(newFeel);
        app.setFeel(feelList);
        app.saveFile();
        finish();
    }

    /**
     * setnewDate updates the date to the new given time by ISO standard
     * Does some computation with the Calendar's date and ISO's date
     * @return Boolean that checks if it the update was successful or not
     */
    public Boolean setnewDate(){
        Boolean passed;

        Calendar myCal = Calendar.getInstance();
        Date oldDate = newFeel.getDate();
        myCal.setTime(oldDate);

        Integer YEAR = ISOsubStrings(0,4);
        Integer MONTH = ISOsubStrings(5,7);
        Integer DAY = Integer.parseInt(currentDay.getText().toString());

        Integer HOUR = Integer.parseInt(currentHour.getText().toString());
        Integer MIN = Integer.parseInt(currentMin.getText().toString());
        Integer SEC = myCal.get(Calendar.SECOND);

        Calendar mycal = new GregorianCalendar(YEAR, MONTH, 0);
        Integer maximumday = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);

        if (maximumday >= DAY){
            //Inside the day of the month
        }else{
            //Invalid day
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

        TimeZone timezone = TimeZone.getTimeZone("UTC");
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//
        format.setTimeZone(timezone);
        String tradeDate;
        tradeDate = changeString(YEAR) + "-" + changeString(MONTH) + "-" + changeString(DAY) + " " + changeString(HOUR) + ":" + changeString(MIN) + ":" + changeString(SEC);
        try {
            mycal.setTime(format.parse(tradeDate));
            passed = true;
            oldDate = mycal.getTime();
            newFeel.setDate(oldDate);
        } catch(ParseException e) {
            passed = false;
        }

        return passed;
    }

    /**
     * changeString changes an integer to a string and adds a 0 in front depending on the length of the string
     * Used for when calculating the format of the date
     * @param atime Integer type
     * @return astring String type
     */
    public String changeString(Integer atime){
        String astring = atime.toString();
        if (astring.length() == 1){
            astring = "0"+astring;
        }
        return astring;
    }

    /**
     * applied Apply the changes to the given Feeling class
     * It invokes when 'Apply' button is pressed and then returns to the previous Activity
     * @param v View type (Apply Button)
     */
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

    /**
     * cancelled Cancels Editing process of the Feeling class
     * It invokes when 'Cancel' button is pressed and returns to the previous Activity
     * @param v View type (Cancel Button)
     */
    public void cancelled(View v){
        Toast.makeText(this, "Cancelled",Toast.LENGTH_SHORT).show();
        finish();
    }
}
