package com.example.userslist.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.userslist.models.Server;

import java.util.List;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Server> servers);

    @Query("SELECT DISTINCT * FROM server")
    LiveData<List<Server>> getServerData();

    @Query("DELETE FROM server")
    void deleteAll();


}
