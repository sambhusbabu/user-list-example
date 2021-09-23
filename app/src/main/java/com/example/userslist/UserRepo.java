package com.example.userslist;

import android.util.Log;

import com.example.userslist.models.User;
import com.example.userslist.models.UserData;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserRepo {
    private static final String TAG = "MainRepo";

    private DataReceive dataReceive;

    public UserRepo(DataReceive dataReceive) {
        this.dataReceive = dataReceive;
    }

    public interface DataReceive {
        void onDataReceive(User userData);

        void onDataReceiveFailure(String message);
    }

    public void getUsersList(String userId) {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.instantwebtools.net/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonAPI jsonApi = retrofit.create(JsonAPI.class);
        Call<User> call = jsonApi.getUser(userId);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    dataReceive.onDataReceiveFailure("Code: " + response.code());
                    return;
                }
                User userData = response.body();
                dataReceive.onDataReceive(userData);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                dataReceive.onDataReceiveFailure(t.getMessage());
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

}
