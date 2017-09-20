package com.example.rid1studios.swolemate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CreateWorkoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_create_workout);
    }
}
