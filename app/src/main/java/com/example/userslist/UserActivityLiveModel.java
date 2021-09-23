package com.example.userslist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.userslist.models.User;
import com.example.userslist.models.UserData;

public class UserActivityLiveModel extends ViewModel implements UserRepo.DataReceive {

    MutableLiveData<User> userDataMutableLiveData = new MutableLiveData<>();
    MutableLiveData<String> status = new MutableLiveData<>();
    MutableLiveData<Boolean> progressBar = new MutableLiveData<>();
    private UserRepo mainRepo = new UserRepo(this);

    public void getUserList(String userId) {
        mainRepo.getUsersList(userId);
        progressBar.setValue(true);
    }


    public LiveData<User> getUserDataMutableLiveData() {
        return userDataMutableLiveData;
    }

    public LiveData<String> getStatus() {
        return status;
    }

    public LiveData<Boolean> getProgressBar() {
        return progressBar;
    }

    @Override
    public void onDataReceive(User userData) {
        userDataMutableLiveData.setValue(userData);
        progressBar.setValue(false);

    }

    @Override
    public void onDataReceiveFailure(String message) {
        status.setValue(message);
        progressBar.setValue(false);
    }
}
