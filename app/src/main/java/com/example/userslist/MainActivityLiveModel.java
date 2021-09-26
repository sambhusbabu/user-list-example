package com.example.userslist;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.userslist.models.Server;

import java.util.List;

public class MainActivityLiveModel extends AndroidViewModel implements MainRepo.DataReceive {

    MutableLiveData<String> status = new MutableLiveData<>();
    MutableLiveData<Boolean> progressBar = new MutableLiveData<>();
    private MainRepo mainRepo;
    public static final String PREFS_NAME = "icom.example.userslist";
    public static final String PREFS_PAGE_NUMBER = "page_number";
    String pageNumber;
    SharedPreferences sharedPreferences;
    public MainActivityLiveModel(@NonNull Application application) {
        super(application);
        mainRepo = new MainRepo(this, application);
        sharedPreferences = application.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE );
        pageNumber = sharedPreferences.getString(PREFS_PAGE_NUMBER, "0");
        getUsersList();

    }

    public void getUsersList() {
        mainRepo.getUsersList(pageNumber);
        progressBar.setValue(true);
    }
    public void refreshData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREFS_PAGE_NUMBER, "0");
        editor.apply();
        pageNumber = "0";
        mainRepo.deleteData();
        getUsersList();
    }


    public void nextPage() {
        pageNumber = "" + (Integer.parseInt(pageNumber) + 1);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREFS_PAGE_NUMBER, pageNumber);
        editor.apply();
        getUsersList();
    }

    public LiveData<List<Server>> getServerData() {
        return mainRepo.getAllServerData();
    }

    public LiveData<String> getStatus() {
        return status;
    }

    public LiveData<Boolean> getProgressBar() {
        return progressBar;
    }

    @Override
    public void onDataReceive() {
        progressBar.setValue(false);
    }

    @Override
    public void onDataReceiveFailure(String message) {
        status.setValue(message);
        progressBar.setValue(false);
    }
}
