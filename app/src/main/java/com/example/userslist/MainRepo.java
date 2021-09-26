package com.example.userslist;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.userslist.data.Repository;
import com.example.userslist.models.Server;
import com.example.userslist.models.User;
import com.example.userslist.models.UserData;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainRepo {
    private static final String TAG = "MainRepo";

    private DataReceive dataReceive;
    Map<String, String> query = new HashMap<>();
    Repository repository;

    public MainRepo(DataReceive dataReceive, Application application) {
        this.dataReceive = dataReceive;
        repository = new Repository(application);
    }

    public interface DataReceive {
        void onDataReceive();

        void onDataReceiveFailure(String message);
    }

    public void getUsersList(String pageNumber) {

        query.put("page", pageNumber);
        query.put("size", "20");

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
//                dataReceive.onDataReceive(userData);

                List<Server> serverList = new ArrayList<>();
                assert userData != null;
                for (User user : userData.getData()) {
                    serverList.add(new Server(user.get_id(), new Gson().toJson(user)));
                }
                repository.insert(serverList);
                dataReceive.onDataReceive();
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                dataReceive.onDataReceiveFailure(t.getMessage());
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

    public LiveData<List<Server>> getAllServerData() {
        return repository.getAllServerData();
    }

    public void deleteData() {
        repository.deleteAll();
    }
}
