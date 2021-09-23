package com.example.userslist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.userslist.models.UserData;

public class MainActivityLiveModel extends ViewModel implements MainRepo.DataReceive {

    MutableLiveData<UserData> userDataMutableLiveData = new MutableLiveData<>();
    MutableLiveData<String> status = new MutableLiveData<>();
    MutableLiveData<Boolean> progressBar = new MutableLiveData<>();
    private MainRepo mainRepo = new MainRepo(this);
    String pageNumber = "0";

    public void getUsersList() {
        mainRepo.getUsersList(pageNumber);
        progressBar.setValue(true);
    }

    public void nextPage() {
        pageNumber = "" + (Integer.parseInt(pageNumber) + 1);
        getUsersList();
    }
    public void previousPage() {
        pageNumber = "" + (Integer.parseInt(pageNumber) - 1);
        getUsersList();
    }

    public LiveData<UserData> getUserDataMutableLiveData() {
        return userDataMutableLiveData;
    }

    public LiveData<String> getStatus() {
        return status;
    }

    public LiveData<Boolean> getProgressBar() {
        return progressBar;
    }

    @Override
    public void onDataReceive(UserData userData) {
        userDataMutableLiveData.setValue(userData);
        progressBar.setValue(false);

    }

    @Override
    public void onDataReceiveFailure(String message) {
        status.setValue(message);
        progressBar.setValue(false);
    }
}
