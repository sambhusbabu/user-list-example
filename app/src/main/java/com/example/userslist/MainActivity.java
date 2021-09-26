package com.example.userslist;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.userslist.adapter.UsersAdapter;
import com.example.userslist.databinding.ActivityMainBinding;
import com.example.userslist.models.Server;
import com.example.userslist.models.User;
import com.example.userslist.util.InternetConBroadcastReceiver;
import com.example.userslist.util.InternetConnection;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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
        mainActivityLiveModel.getServerData().observe(this, userData -> {
            List<User> users = new ArrayList<>();
            for (Server item :
                    userData) {
                Log.d(TAG, "onCreate: Json Data:--- " + item.getData());
                users.add(new Gson().fromJson(item.getData(), User.class));
            }

            int count = userList.size();
            this.userList.clear();
            this.userList.addAll(users);
            adapter.notifyItemRangeChanged(count - 1, userData.size() - count);
        });

        if (!InternetConnection.isConnected(this)) {
            toastMsg("No Internet");
        }

        adapter.setOnClickItem((user, position, view) -> {
            Log.d(TAG, "onCreate: Item Click Id:-- " + user.get_id());
            Intent intent = new Intent(getBaseContext(), UserActivity.class);
            intent.putExtra("USER_ID", user.get_id());
            startActivity(intent);
        });

        mainActivityLiveModel.getProgressBar().observe(this, aBoolean -> {
            binding.progressBar.setVisibility(aBoolean ? View.VISIBLE : View.GONE);
            binding.swiperefresh.setRefreshing(aBoolean);
        });

        internetConBroadcastReceiver.setOnConnection(new InternetConBroadcastReceiver.OnConnection() {
            @Override
            public void lossInternetConnection() {
                stat = true;

            }

            @Override
            public void onConnected() {
                if (stat) {
                    toastMsg("Internet Connected");
                }
            }
        });

        mainActivityLiveModel.getStatus().observe(this, this::toastMsg);

        binding.userListRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //Log.d(TAG, "onScrolled: Last Scrole Value :--- DX:-- "+dx+ "   DY:-- "+dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == userList.size() - 1) {
                    //bottom of list!

                    mainActivityLiveModel.nextPage();

                }

            }
        });

        binding.swiperefresh.setOnRefreshListener(() -> mainActivityLiveModel.refreshData());

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