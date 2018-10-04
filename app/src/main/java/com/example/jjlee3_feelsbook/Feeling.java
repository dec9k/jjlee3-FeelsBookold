package com.example.jjlee3_feelsbook;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Feeling class, the property of the data being used
 */
public class Feeling{

    private Date date;
    private String message;
    private String feel;
    private static final Integer MAX_CHARS = 100;

    /**
     * Constructor of Feeling takes in two parameters and sets them
     * @param message String type
     * @param feel String type
     */
    Feeling(String message, String feel){
        this.feel = feel;
        setDate(new Date());
        setMessage(message);
    }

    /**
     * getFeel a get method for Feel
     * @return feel String type
     */
    public String getFeel(){
        return this.feel;
    }

    /**
     * getDate a get method for Date
     * @return date Date type
     */
    public Date getDate(){return this.date;}

    /**
     * getMessage a get method for the message
     * @return message String type
     */
    public String getMessage(){
        return this.message;
    }

    /**
     * setMessage set method for the message also makes sure it doesn't go over the MAX CHARS given
     * @param message String type
     */
    public void setMessage(String message) {
        if (message.length() <= this.MAX_CHARS) {
            this.message = message;
        } else {
            //only get up to MAX_CHARS then
            this.message = message.substring(0,Math.min(message.length(),100));
        }
    }


    /**
     * setDate set method for the Date
     * @param date Date type
     */
    public void setDate(Date date){
        this.date = date;
    }

    /**
     * DateasISO returns a String which is the current Date formatted as ISO standard
     * @return nowAsISO String type
     */
    public String DateasISO(){
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        df.setTimeZone(tz);
        String nowAsISO = df.format(date);
        return nowAsISO;
    }

    /**
     * toString returns a String which is the combinations of Current Feeling, ISO formatted Date, and message
     * @return combinations of Feeling, ISO Date, and message
     */
    public String toString(){
        String nowAsISO = DateasISO();
        return this.feel + " | " + nowAsISO + " | " + this.message;
    }
}
