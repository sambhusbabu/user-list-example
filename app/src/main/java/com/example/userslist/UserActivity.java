package com.example.userslist;

import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.userslist.databinding.ActivityUserBinding;
import com.example.userslist.util.InternetConBroadcastReceiver;
import com.example.userslist.util.InternetConnection;

public class UserActivity extends AppCompatActivity {

    private static final String TAG = "UserActivity";
    ActivityUserBinding binding;
    UserActivityLiveModel userActivityLiveModel;
    InternetConBroadcastReceiver internetConBroadcastReceiver = new InternetConBroadcastReceiver();

    boolean stat = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userActivityLiveModel = new ViewModelProvider(this).get(UserActivityLiveModel.class);

        String userId = getIntent().getStringExtra("USER_ID");
        if (InternetConnection.isConnected(this))
            userActivityLiveModel.getUserList(userId);
        else
            toastMsg("No Internet");

        userActivityLiveModel.userDataMutableLiveData.observe(this, user -> {
            binding.setData(user);
        });
        userActivityLiveModel.getProgressBar().observe(this, aBoolean -> binding.progressBar2.setVisibility(aBoolean ? View.VISIBLE : View.GONE));

        internetConBroadcastReceiver.setOnConnection(new InternetConBroadcastReceiver.OnConnection() {
            @Override
            public void lossInternetConnection() {
                stat = true;
            }

            @Override
            public void onConnected() {
                if (stat) {
                    userActivityLiveModel.getUserList(userId);
                    toastMsg("Internet Connected");
                }
            }
        });

        userActivityLiveModel.getStatus().observe(this, this::toastMsg);

    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(internetConBroadcastReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(internetConBroadcastReceiver);
    }

    private void toastMsg(String msg) {

        try {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "toastMsg:-- " + msg + " --");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}