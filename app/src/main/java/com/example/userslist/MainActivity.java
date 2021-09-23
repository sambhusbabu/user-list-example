package com.example.userslist;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.userslist.adapter.UsersAdapter;
import com.example.userslist.databinding.ActivityMainBinding;
import com.example.userslist.models.User;
import com.example.userslist.util.InternetConBroadcastReceiver;
import com.example.userslist.util.InternetConnection;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    ActivityMainBinding binding;
    MainActivityLiveModel mainActivityLiveModel;
    UsersAdapter adapter;
    List<User> userList = new ArrayList<>();
    InternetConBroadcastReceiver internetConBroadcastReceiver = new InternetConBroadcastReceiver();
    boolean stat = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mainActivityLiveModel = new ViewModelProvider(this).get(MainActivityLiveModel.class);

        adapter = new UsersAdapter(this, userList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        binding.userListRecycler.setLayoutManager(layoutManager);
        binding.userListRecycler.setScrollContainer(false);
        binding.userListRecycler.setAdapter(adapter);
        mainActivityLiveModel.userDataMutableLiveData.observe(this, userData -> {
            this.userList.clear();
            this.userList.addAll(userData.getData());
            adapter.notifyDataSetChanged();
        });

        if (InternetConnection.isConnected(this)) {
            mainActivityLiveModel.getUsersList();
        } else
            toastMsg("No Internet");

        adapter.setOnClickItem((user, position, view) -> {
            Log.d(TAG, "onCreate: Item Click Id:-- " + user.get_id());
            Intent intent = new Intent(getBaseContext(), UserActivity.class);
            intent.putExtra("USER_ID", user.get_id());
            startActivity(intent);
        });

        binding.nextBtn.setOnClickListener(v -> mainActivityLiveModel.nextPage());
        binding.preBtn.setOnClickListener(v -> mainActivityLiveModel.previousPage());
        mainActivityLiveModel.getProgressBar().observe(this, aBoolean -> binding.progressBar.setVisibility(aBoolean ? View.VISIBLE : View.GONE));

        internetConBroadcastReceiver.setOnConnection(new InternetConBroadcastReceiver.OnConnection() {
            @Override
            public void lossInternetConnection() {
                stat = true;

            }

            @Override
            public void onConnected() {
                if (stat) {
                    mainActivityLiveModel.getUsersList();
                    toastMsg("Internet Connected");
                }
            }
        });
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