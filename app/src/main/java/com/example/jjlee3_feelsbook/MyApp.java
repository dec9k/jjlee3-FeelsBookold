package com.example.jjlee3_feelsbook;

import android.app.Application;
import android.content.Context;

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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MyApp extends Application {

    private static final String FILENAME = "file.sav";
    private static ArrayList<Feeling> feelList;
    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("Boopgrevwqd");
        appContext = getApplicationContext();

        loadFile();
    }

    public void setFeel(ArrayList<Feeling> newList){
        this.feelList = newList;
    }

    public static Context getAppContext() {
        return appContext;
    }

    public ArrayList<Feeling> getFeel(){
        Collections.sort(feelList, new Comparator<Feeling>(){
            public int compare(Feeling o1, Feeling o2){
                return o1.getDate().compareTo(o2.getDate());
            }
        });
        return this.feelList;
    }

    public void loadFile() {

        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson(); //library to save objects

            Type listType = new TypeToken<ArrayList<ExtendFeel>>(){}.getType();

            feelList = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            feelList = new ArrayList<Feeling>(); //an array of tweets
            //e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void saveFile(){
        try {

            Collections.sort(feelList, new Comparator<Feeling>(){
                public int compare(Feeling o1, Feeling o2){
                    return o1.getDate().compareTo(o2.getDate());
                }
            });

            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(feelList, out);
            out.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
