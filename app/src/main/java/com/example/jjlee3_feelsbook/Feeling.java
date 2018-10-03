package com.example.jjlee3_feelsbook;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Feeling{

    private Date date;
    private String message;
    private String feel;
    private static final Integer MAX_CHARS = 100;

    Feeling(String message, String feel){
        this.feel = feel;
        this.date = new Date();
        setMessage(message);
    }

    public String getFeel(){
        return this.feel;
    }

    public Date getDate(){return this.date;}

    public String getMessage(){
        return this.message;
    }

    public void setMessage(String message) {
        if (message.length() <= this.MAX_CHARS) {
            this.message = message;
        } else {
            //only get up to MAX_CHARS then
            this.message = message.substring(0,Math.min(message.length(),100));
        }
    }

    public int compareTo(Feeling feeling){
        return getDate().compareTo(feeling.getDate());
    }

    public void setDate(Date date){
        this.date = date;
    }

    public String DateasISO(){
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        df.setTimeZone(tz);
        String nowAsISO = df.format(date);
        return nowAsISO;
    }

    public String toString(){
        String nowAsISO = DateasISO();
        return this.feel + " | " + nowAsISO + " | " + this.message;
    }
}
