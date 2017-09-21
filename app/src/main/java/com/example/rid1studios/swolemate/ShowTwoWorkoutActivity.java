package com.example.rid1studios.swolemate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class ShowTwoWorkoutActivity extends AppCompatActivity {

    private ListView list1;
    private ListView list2;
    private ArrayList<RowData> rowData1;
    private ArrayList<RowData> rowData2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_show_workout2);

        setupWidgets();

    }

    public void setupWidgets(){
        list1 = (ListView) findViewById(R.id.muscleGroup1);
        list2 = (ListView) findViewById(R.id.muscleGroup2);



        Intent intent = getIntent();
        HashMap<String, String> workout1 = (HashMap)intent.getSerializableExtra("workout1");
        HashMap<String, String> workout2 = (HashMap)intent.getSerializableExtra("workout2");

        ArrayList<String> workout1Names = new ArrayList<>(workout1.keySet());
        ArrayList<String> workout1Ranges = new ArrayList<>(workout1.values());
        rowData1 = new ArrayList<>();
        for(int i = 0; i < workout1Names.size(); i++){
            RowData row = new RowData(workout1Names.get(i), workout1Ranges.get(i));
            rowData1.add(row);
        }

        ArrayList<String> workout2Names = new ArrayList<>(workout2.keySet());
        ArrayList<String> workout2Ranges = new ArrayList<>(workout2.values());
        rowData2 = new ArrayList<>();
        for(int i = 0; i < workout2Names.size(); i++){
            RowData row = new RowData(workout2Names.get(i), workout2Ranges.get(i));
            rowData2.add(row);
        }

        ListViewAdapter adapter = new ListViewAdapter(this, rowData1);
        list1.setAdapter(adapter);

        ListViewAdapter adapter2 = new ListViewAdapter(this, rowData2);
        list2.setAdapter(adapter2);
    }
}
