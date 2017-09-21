package com.example.rid1studios.swolemate;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateWorkoutActivity extends AppCompatActivity {

    private CheckBox chestBox, backBox, shoulderBox, absBox, biBox, triBox, quadBox, hamBox, gluteBox;

    private ArrayList<CheckBox> checkBoxes;

    private Hashtable<String, Integer> muscleGroups;

    private Button powerButton;
    private Button hypButton;

    private int numChecked = 0;

    private APIService mAPIService;

    private final int STATUSID = 2;
    private final int LANGID = 2;

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_create_workout);


        populateMusclegroupHashtable();
        setupWidgets();
    }

    public void populateMusclegroupHashtable(){
        muscleGroups = new Hashtable<>();
        muscleGroups.put("shoulders", 2);
        muscleGroups.put("biceps", 1);
        muscleGroups.put("hamstrings", 11);
        muscleGroups.put("quadriceps", 10);
        muscleGroups.put("triceps", 5);
        muscleGroups.put("glutes", 8);
        muscleGroups.put("back", 12);
        muscleGroups.put("chest", 4);
        muscleGroups.put("abdominals", 6);

    }


    public void setupWidgets(){

        mAPIService = APIUtils.getAPIService();

        progress = new ProgressDialog(CreateWorkoutActivity.this,R.style.MyTheme);
        progress.setCancelable(false);
        progress.setProgressStyle(android.R.style.Widget_ProgressBar_Large);

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
                if(!isOnline()){
                    showCustDialog("Please check internet connection");
                }else{
                    if(selectedBoxes.size() == 0){
                        showCustDialog("Please select at least 1 but at most 2 muscle groups");
                    }
                    else if (selectedBoxes.size() == 1){
                        System.out.println(selectedBoxes.get(0).getText().toString().toLowerCase() + " NUM:" + muscleGroups.get(selectedBoxes.get(0).getText().toString().toLowerCase()));
                        progress.show();
                        singlePowerSendGetRequest(LANGID,STATUSID, muscleGroups.get(selectedBoxes.get(0).getText().toString().toLowerCase()));
                    }else{
                        progress.show();
                        firstPowerSendGetRequest(LANGID, STATUSID, muscleGroups.get(selectedBoxes.get(0).getText().toString().toLowerCase()), muscleGroups.get(selectedBoxes.get(1).getText().toString().toLowerCase()));
                    }
                }

            }
        });

        hypButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<CheckBox> selectedBoxes = checkedBoxes();
                if(!isOnline()){
                    showCustDialog("Please check internet connection");
                }else{
                    if(selectedBoxes.size() == 0){
                        showCustDialog("Please select at least 1 but at most 2 muscle groups");
                    }
                    else if (selectedBoxes.size() == 1){
                        progress.show();
                        singleHypSendGetRequest(LANGID,STATUSID, muscleGroups.get(selectedBoxes.get(0).getText().toString().toLowerCase()));
                    }else{
                        progress.show();
                        firstHypSendGetRequest(LANGID, STATUSID, muscleGroups.get(selectedBoxes.get(0).getText().toString().toLowerCase()),muscleGroups.get(selectedBoxes.get(1).getText().toString().toLowerCase()));
                    }
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



    //GET Requests for single workouts
    private void singlePowerSendGetRequest(int langId, int statusId, int muscleId){
        mAPIService.doGetWorkoutList(langId, statusId, muscleId).enqueue(new Callback<WorkoutGet>() {
            @Override
            public void onResponse(Call<WorkoutGet> call, Response<WorkoutGet> response) {
                createPowerWorkout(response);
            }

            @Override
            public void onFailure(Call<WorkoutGet> call, Throwable t) {

            }
        });
    }

    private void singleHypSendGetRequest(int langId, int statusId, int muscleId){
        mAPIService.doGetWorkoutList(langId, statusId, muscleId).enqueue(new Callback<WorkoutGet>() {
            @Override
            public void onResponse(Call<WorkoutGet> call, Response<WorkoutGet> response) {
                createHypWorkout(response);
            }

            @Override
            public void onFailure(Call<WorkoutGet> call, Throwable t) {

            }
        });
    }

    //Get Requests for 2 workouts
    //get first workout, then second.
    private void firstPowerSendGetRequest(final int langId, final int statusId, final int muscleId1, final int muscleId2){
        mAPIService.doGetWorkoutList(langId, statusId, muscleId1).enqueue(new Callback<WorkoutGet>() {
            @Override
            public void onResponse(Call<WorkoutGet> call, Response<WorkoutGet> response) {
                secondPowerSendGetRequest(langId, statusId, muscleId2, response);
            }

            @Override
            public void onFailure(Call<WorkoutGet> call, Throwable t) {

            }
        });
    }


    private void secondPowerSendGetRequest(int langId, int statusId, int muscleId, final Response<WorkoutGet> responseIn){
        mAPIService.doGetWorkoutList(langId, statusId, muscleId).enqueue(new Callback<WorkoutGet>() {
            @Override
            public void onResponse(Call<WorkoutGet> call, Response<WorkoutGet> response) {
                createTwoPowerWorkout(response, responseIn);

            }

            @Override
            public void onFailure(Call<WorkoutGet> call, Throwable t) {

            }
        });
    }


    private void firstHypSendGetRequest(final int langId, final int statusId, final int muscleId1, final int muscleId2){
        mAPIService.doGetWorkoutList(langId, statusId, muscleId1).enqueue(new Callback<WorkoutGet>() {
            @Override
            public void onResponse(Call<WorkoutGet> call, Response<WorkoutGet> response) {
                secondHypSendGetRequest(langId, statusId, muscleId2, response);
            }

            @Override
            public void onFailure(Call<WorkoutGet> call, Throwable t) {

            }
        });
    }


    private void secondHypSendGetRequest(int langId, int statusId, int muscleId, final Response<WorkoutGet> responseIn){
        mAPIService.doGetWorkoutList(langId, statusId, muscleId).enqueue(new Callback<WorkoutGet>() {
            @Override
            public void onResponse(Call<WorkoutGet> call, Response<WorkoutGet> response) {
                createTwoHypWorkout(response, responseIn);
            }

            @Override
            public void onFailure(Call<WorkoutGet> call, Throwable t) {

            }
        });
    }




    //Create single workout
    private void createPowerWorkout(Response<WorkoutGet> response){
        Hashtable<String, String> workout = formulatePowerWorkout(response);

        Intent i = new Intent(CreateWorkoutActivity.this, ShowOneWorkoutActivity.class);
        i.putExtra("workout", workout);
        progress.cancel();
        startActivity(i);
    }

    private void createHypWorkout(Response<WorkoutGet> response){
        Hashtable<String, String> workout = formulateHypWorkout(response);

        Intent i = new Intent(CreateWorkoutActivity.this, ShowOneWorkoutActivity.class);
        i.putExtra("workout", workout);
        progress.cancel();
        startActivity(i);
    }


    //Create double workout
    private void createTwoPowerWorkout(Response<WorkoutGet> response1, Response<WorkoutGet> response2){
        Hashtable<String, String> workout1 = formulatePowerWorkout(response1);
        Hashtable<String, String> workout2 = formulatePowerWorkout(response2);

        Intent i = new Intent(CreateWorkoutActivity.this, ShowTwoWorkoutActivity.class);
        i.putExtra("workout1", workout1);
        i.putExtra("workout2", workout2);
        progress.cancel();
        startActivity(i);
    }

    private void createTwoHypWorkout(Response<WorkoutGet> response1, Response<WorkoutGet> response2){
        Hashtable<String, String> workout1 = formulateHypWorkout(response1);
        Hashtable<String, String> workout2 = formulateHypWorkout(response2);

        Intent i = new Intent(CreateWorkoutActivity.this, ShowTwoWorkoutActivity.class);
        i.putExtra("workout1", workout1);
        i.putExtra("workout2", workout2);
        progress.cancel();
        startActivity(i);
    }

    //Set number of sets and reps
    private Hashtable<String, String> formulateHypWorkout(Response<WorkoutGet> data){
        Hashtable<String, String> workout = new Hashtable<>();
        for(int i = 0; i < 4; i++){
            Random rand = new Random();
            int lowReps = 8;
            int highReps = 15;
            int lowSets = 2;
            int highSets = 5;

            int sets = rand.nextInt(highSets - lowSets) + lowSets;
            int reps = rand.nextInt(highReps - lowReps) + lowReps;
            String exerciseName = data.body().getResults().get(rand.nextInt(data.body().getResults().size())).getName();
            workout.put(exerciseName, sets + " x " + reps);
        }

        return workout;
    }

    private Hashtable<String, String> formulatePowerWorkout(Response<WorkoutGet> data){
        Hashtable<String, String> workout = new Hashtable<>();
        for(int i = 0; i < 4; i++){
            Random rand = new Random();
            int sets = rand.nextInt(3) + 1;
            int reps = rand.nextInt(5) + 1;
            String exerciseName = data.body().getResults().get(rand.nextInt(data.body().getResults().size())).getName();
            workout.put(exerciseName, sets + " x " + reps);
        }

        return workout;
    }


    private boolean isOnline() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    private void showCustDialog(String message){
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
