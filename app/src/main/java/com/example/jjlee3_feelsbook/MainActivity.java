/*
Copyright (C) 2014 Jason Lee jjlee3@ualberta.ca

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.
You should have received a copy of the GNU General Public License
along with this program. If not, see <http://www.gnu.org/licenses/>.

*/

package com.example.jjlee3_feelsbook;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String DEFAULTFEELING = "None";
    private EditText bodyText;
    private TextView titleText;
    private String CurrentFeeling = DEFAULTFEELING;

    MyApp app;
    ArrayList<Feeling> feelList;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){

            app = (MyApp) getApplicationContext();

            if (CurrentFeeling.equals(DEFAULTFEELING)) {
                Toast.makeText(MainActivity.this, "Must Choose a Feeling",Toast.LENGTH_SHORT).show();
            }else{
                feelList = app.getFeel();
                Toast.makeText(MainActivity.this, "Feeling Added", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                String text = bodyText.getText().toString();

                Feeling feel = new ExtendFeel(text, CurrentFeeling);

                feelList.add(feel);
                app.setFeel(feelList);
                app.saveFile();
                startIntent();

                resetViews();
            }
      }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bodyText = (EditText) findViewById(R.id.commentText);
        titleText = (TextView) findViewById(R.id.ChosenFeelingTextView);

        app = (MyApp) getApplicationContext();
    }

    public void startIntent(){
        Intent intent = new Intent(MainActivity.this, ListFeelingActivity.class);
        startActivity(intent);
    }

    public void recordFeel(View v){
        startIntent();
    }

    public void addFeeling(View v){
        threadTest();
    }

    public void threadTest(){
        Runnable r = new Runnable(){
            @Override
            public void run() {
                long futureTime = System.currentTimeMillis() + 1000;
                while (System.currentTimeMillis() < futureTime){
                    synchronized (this){
                        try{
                            wait(futureTime-System.currentTimeMillis());
                        }catch(Exception e){}
                    }
                }
                handler.sendEmptyMessage(0);
            }
        };

        Thread testThread = new Thread(r);
        testThread.start();

    }

    public void moodButton(View v){
        Button b = (Button)v;
        String str = b.getText().toString();
        setFeeling(str);
    }

    public void resetViews(){
        setFeeling(DEFAULTFEELING);
        titleText.setText("Current Feeling: ");
        bodyText.setText("");
    }

    public void setFeeling(String myfeeling){
        this.CurrentFeeling = myfeeling;
        titleText.setText("Current Feeling: " + myfeeling);
        //Toast.makeText(MainActivity.this, "Feeling Chosen: "+CurrentFeeling,Toast.LENGTH_SHORT).show();
    }
}
