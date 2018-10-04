package com.example.jjlee3_feelsbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;


/**
 * ListFeelingActivity class, provides the UI of the application
 * Provides all the lists of the Feeling Arrays as well as the count for each one
 */
public class ListFeelingActivity extends Activity {

    private ListView oldFeelsList;
    private TextView feelCounts;
    private ArrayList<Feeling> feelList;
    private ArrayAdapter<Feeling> adapter;

    private MyApp app;
    private static final String[] ALL_EMOTIONS = {
            "Joy", "Sad", "Love", "Surprise", "Anger","Fear"
    };
    private static Integer[] Emote_Counts = {
            0, 0, 0, 0, 0, 0
    };


    /**
     * onCreate method sets the variables as well as setting up the onclickListener for the ListView
     * It also sets up the counts and puts on the display
     * @param savedInstanceState Bundle type
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_feeling);

        feelCounts = (TextView) findViewById(R.id.feelCountsView);
        oldFeelsList = (ListView) findViewById(R.id.FeelsList);

        app = (MyApp) getApplicationContext();

        oldFeelsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListFeelingActivity.this, FeelingEditActivity.class);
                intent.putExtra("THE_INDEX", position);
                startActivity(intent);
            }
        });
    }

<<<<<<< HEAD
    /**
     * DisplayCounts displays the count for each emotions given
     */
    public void DisplayCounts(){
=======
    public void SetUpFeelings(){
>>>>>>> a4792192469c53a1ba3c04f3bc11584e50ebcf0a
        feelList = app.getFeel();
        adapter = new ArrayAdapter<Feeling>(this,
                R.layout.list_item, feelList);
        oldFeelsList.setAdapter(adapter);
        String eCounts = "| ";
        for (int i = 0; i < 6; i++) {
            Integer num = countFeeling(ALL_EMOTIONS[i]);
            Emote_Counts[i] = num;
            eCounts = eCounts + ALL_EMOTIONS[i] + " : " + num.toString() + " | ";
        }
        feelCounts.setText(eCounts);
        adapter.notifyDataSetChanged();
    }

    /**
     * countFeeling counts the number of a specific emotion
     * @param keyword type String
     * @return count type Integer
     */
    public Integer countFeeling(String keyword){
        Integer count = 0;
        for (Feeling feeling : feelList){
            String feel = feeling.getFeel();
            if (feel.equals(keyword)){
                count ++;
            }
        }
        return count;
    }

    /**
     * returnBack returns the user back to the previous activity
     * @param v
     */
    public void returnBack(View v){
        finish();
    }

    /**
     * onResume display the counts every time the user returns to this activity
     */
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
<<<<<<< HEAD
        DisplayCounts();
=======
        SetUpFeelings();
>>>>>>> a4792192469c53a1ba3c04f3bc11584e50ebcf0a
    }
}
