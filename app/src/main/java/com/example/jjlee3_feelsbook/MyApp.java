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

/**
 * MyApp class, provides the Save, Load, and where the Data properties are stored on
 */
public class MyApp extends Application {
    private static final String FILENAME = "file.sav";
    private static ArrayList<Feeling> feelList;

    /**
     * onCreate method starts when the Application is first created
     * Loads in the file stored in the android
     */
    @Override
    public void onCreate() {
        super.onCreate();
        loadFile();
    }

    /**
     * setFeel sets the data ArrayList to a newly given ArrayList
     * @param newList ArrayList<Feeling> type
     */
    public void setFeel(ArrayList<Feeling> newList){
        this.feelList = newList;
    }

    /**
     * getFeel get method for data ArrayList
     * @return feelList ArrayList<Feeling> type
     */
    public ArrayList<Feeling> getFeel(){
        Collections.sort(feelList, new Comparator<Feeling>(){
            public int compare(Feeling o1, Feeling o2){
                return o1.getDate().compareTo(o2.getDate());
            }
        });
        return this.feelList;
    }

    /**
     * loadFile method used to load the stored data in the android
     * If nothing is found then creates an empty ArrayList
     */
    public void loadFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            Type listType = new TypeToken<ArrayList<Feeling>>(){}.getType();

            feelList = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            feelList = new ArrayList<>();
            //e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * saveFile method used to store the current data to the stored data in the android
     */
    public void saveFile(){
        try {


            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(feelList, out);
            out.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
