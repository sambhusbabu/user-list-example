package com.example.userslist;

import android.util.Log;

import com.example.userslist.models.UserData;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainRepo {
    private static final String TAG = "MainRepo";

    private DataReceive dataReceive;
    Map<String,String> query = new HashMap<>();

    public MainRepo(DataReceive dataReceive) {
        this.dataReceive = dataReceive;
    }

    public interface DataReceive {
        void onDataReceive(UserData userData);

        void onDataReceiveFailure(String message);
    }

    public void getUsersList(String pageNumber) {

        query.put("page",pageNumber);
        query.put("size","20");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.instantwebtools.net/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonAPI jsonApi = retrofit.create(JsonAPI.class);
        Call<UserData> call = jsonApi.getUsers(query);

        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                if (!response.isSuccessful()) {
                    dataReceive.onDataReceiveFailure("Code: " + response.code());
                    return;
                }
                UserData userData = response.body();
                dataReceive.onDataReceive(userData);
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                dataReceive.onDataReceiveFailure(t.getMessage());
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

}
