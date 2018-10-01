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
    ArrayList<Feeling> feelList;
    ArrayAdapter<Feeling> adapter;

    MyApp app;
    private ArrayList<String> feelings;
    private ArrayList<Integer> feelingCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_feeling);

        feelCounts = (TextView) findViewById(R.id.feelCountsView);
        oldFeelsList = (ListView) findViewById(R.id.FeelsList);

        setupCounts();

        app = (MyApp) getApplicationContext();

        oldFeelsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListFeelingActivity.this, FeelingEditActivity.class);
                intent.putExtra("THE_INDEX", position);
                //System.out.println("THE INDEX : " + position);
                startActivity(intent);
            }
        });
    }

    public void setupCounts(){
        feelings = new ArrayList<String>();
        feelings.add("Joy");
        feelings.add("Sad");
        feelings.add("Love");
        feelings.add("Surprise");
        feelings.add("Anger");
        feelings.add("Fear");
        feelingCount = new ArrayList<Integer>();
        feelingCount.add(0);
        feelingCount.add(0);
        feelingCount.add(0);
        feelingCount.add(0);
        feelingCount.add(0);
        feelingCount.add(0);
    }

    public void CountFeelings(){
        feelList = app.getFeel();
        adapter = new ArrayAdapter<Feeling>(this,
                R.layout.list_item, feelList);
        oldFeelsList.setAdapter(adapter);
        String str = "| ";
        for (int i = 0; i < feelings.size(); i++) {
            Integer num = countFeeling(feelings.get(i));
            feelingCount.set(i, num);
            str = str + feelings.get(i) + " : " + num.toString() + " | ";
            //System.out.println(feelings.get(i) + " : " + num.toString());
        }
        feelCounts.setText(str);
        adapter.notifyDataSetChanged();
    }

    public Integer countFeeling(String keyword){
        Integer num = 0;
        for (Feeling f : feelList){
            String feel = f.getFeel();
            //System.out.println(feel);
            if (feel.equals(keyword)){
                num ++;
            }
        }
        return num;
    }

    public void returnBack(View v){
        finish();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        CountFeelings();
    }

}
