package com.example.rid1studios.swolemate;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class CreateWorkoutActivity extends AppCompatActivity {

    private CheckBox chestBox, backBox, shoulderBox, absBox, biBox, triBox, quadBox, hamBox, gluteBox;

    private ArrayList<CheckBox> checkBoxes;

    private Button powerButton;
    private Button hypButton;

    private int numChecked = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_create_workout);

        setupWidgets();
    }


    public void setupWidgets(){
        chestBox = (CheckBox)findViewById(R.id.chestBox);
        backBox = (CheckBox)findViewById(R.id.backBox);
        shoulderBox = (CheckBox)findViewById(R.id.shoulderBox);
        absBox = (CheckBox)findViewById(R.id.absBox);
        biBox = (CheckBox)findViewById(R.id.bicepsBox);
        triBox = (CheckBox)findViewById(R.id.tricepsBox);
        quadBox = (CheckBox)findViewById(R.id.quadBox);
        hamBox = (CheckBox)findViewById(R.id.hammyBox);
        gluteBox = (CheckBox)findViewById(R.id.glutesBox);

        checkBoxes = new ArrayList<>();
        checkBoxes.addAll(Arrays.asList(chestBox, backBox, shoulderBox, absBox, biBox, triBox, quadBox, hamBox, gluteBox));

        setupCheckboxes();

        powerButton = (Button)findViewById(R.id.powerButton);
        hypButton = (Button)findViewById(R.id.hypButton);
        setupButtons();


    }

    public void setupButtons(){
        powerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<CheckBox> selectedBoxes = checkedBoxes();
                if(selectedBoxes.size() == 0){
                    showDialog("Please select at least 1 but at most 2 muscle groups");
                }
            }
        });

        hypButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<CheckBox> selectedBoxes = checkedBoxes();
                if(selectedBoxes.size() == 0){
                    showDialog("Please select at least 1 but at most 2 muscle groups");
                }
            }
        });
    }

    public void setupCheckboxes(){
        for(int i = 0; i < checkBoxes.size(); i++){
            checkBoxes.get(i).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if(isChecked){
                        numChecked += 1;
                        if(numChecked == 2){
                            disableCheckboxes();
                        }
                    }
                    if(!isChecked){
                        numChecked -= 1;
                        enableCheckboxes();
                    }
                }
            });


        }
    }

    public void disableCheckboxes(){
        for(int i = 0; i < checkBoxes.size(); i++){
            if(checkBoxes.get(i).isChecked() == false){
                checkBoxes.get(i).setEnabled(false);
            }
        }
    }

    public void enableCheckboxes(){
        for(int i = 0; i < checkBoxes.size(); i++){
            if(checkBoxes.get(i).isChecked() == false){
                checkBoxes.get(i).setEnabled(true);
            }
        }
    }

    public ArrayList<CheckBox> checkedBoxes(){
        ArrayList<CheckBox> returnVal = new ArrayList<>();
        for(int i = 0; i < checkBoxes.size(); i++){
            if(checkBoxes.get(i).isChecked()){
                returnVal.add(checkBoxes.get(i));
            }
        }
        return returnVal;
    }

    private void showDialog(String message){
        AlertDialog alertDialog = new AlertDialog.Builder(CreateWorkoutActivity.this).create();
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
