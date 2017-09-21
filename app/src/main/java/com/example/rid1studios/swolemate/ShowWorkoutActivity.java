package com.example.rid1studios.swolemate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

public class ShowWorkoutActivity extends AppCompatActivity {

    ListView list;
    List<RowData> rowData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_show_workout1);

        list = (ListView) findViewById(R.id.workoutList1);
        ListViewAdapter adapter = new ListViewAdapter(this, rowData);
        list.setAdapter(adapter);

    }
}
