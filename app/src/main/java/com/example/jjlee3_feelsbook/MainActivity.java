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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText bodyText;

    private MyApp app;
    private ArrayList<Feeling> feelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bodyText = (EditText) findViewById(R.id.commentText);
        app = (MyApp) getApplicationContext();
    }

    public void startIntent(){
        Intent intent = new Intent(MainActivity.this, ListFeelingActivity.class);
        startActivity(intent);
    }

    public void recordFeel(View v){
        startIntent();
    }

    public void moodButton(View v){
        Button b = (Button)v;
        String str = b.getText().toString();
        setFeeling(str);
    }

    public void setFeeling(String myfeeling){
        feelList = app.getFeel();
        Toast.makeText(MainActivity.this, myfeeling + " Added", Toast.LENGTH_SHORT).show();
        //setResult(RESULT_OK);
        String text = bodyText.getText().toString();
        Feeling feel = new Feeling(text, myfeeling);

        feelList.add(feel);
        app.setFeel(feelList);
        app.saveFile();
    }
}
