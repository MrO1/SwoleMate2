package com.example.rid1studios.swolemate;

/**
 * Created by rid on 9/21/17.
 */

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {

    @GET("exercise/?")
    Call<WorkoutGet> doGetWorkoutList(@Query("language") int langId,
                                      @Query("status") int statusId,
                                      @Query("muscles") int muscleId);
}