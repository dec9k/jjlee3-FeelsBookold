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

    public void SetUpFeelings(){
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

    public void returnBack(View v){
        finish();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        SetUpFeelings();
    }

}
