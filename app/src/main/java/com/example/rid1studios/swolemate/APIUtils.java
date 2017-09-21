package com.example.rid1studios.swolemate;

/**
 * Created by rid on 9/21/17.
 */

public class APIUtils {

    private APIUtils() {}

    public static final String BASE_URL = "https://wger.de/api/v2/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
