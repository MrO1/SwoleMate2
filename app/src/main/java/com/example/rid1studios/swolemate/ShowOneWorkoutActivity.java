package com.example.rid1studios.swolemate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

public class ShowOneWorkoutActivity extends AppCompatActivity {

    ListView list;
    ArrayList<RowData> rowData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_show_workout1);

        list = (ListView) findViewById(R.id.workoutList1);



        Intent intent = getIntent();
        HashMap<String, String> workout = (HashMap)intent.getSerializableExtra("workout");
        ArrayList<String> workoutNames = new ArrayList<>(workout.keySet());
        ArrayList<String> workoutRanges = new ArrayList<>(workout.values());
        rowData = new ArrayList<>();
        for(int i = 0; i < workoutNames.size(); i++){
            RowData row = new RowData(workoutNames.get(i), workoutRanges.get(i));
            rowData.add(row);
        }

        ListViewAdapter adapter = new ListViewAdapter(this, rowData);
        list.setAdapter(adapter);

    }
}
