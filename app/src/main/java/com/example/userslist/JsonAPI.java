package com.example.userslist;

import com.example.userslist.models.User;
import com.example.userslist.models.UserData;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface JsonAPI {

    @GET("passenger")
    Call<UserData> getUsers( @QueryMap Map<String, String> options);

    @GET("passenger/{userId}")
    Call<User> getUser(@Path("userId") String userId);
}
