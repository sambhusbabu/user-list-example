package com.example.userslist.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.userslist.models.Server;

@Database(entities = {Server.class}, version = 5)
public abstract class ServerDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "userServer";

    public abstract UserDao userDao();

    public static volatile ServerDatabase INSTANCE = null;

    public static ServerDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (ServerDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, ServerDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .addCallback(callback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static Callback callback = new Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE);
        }
    };

    static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private UserDao userDao;

        public PopulateDbAsync(ServerDatabase catDatabase) {
            userDao = catDatabase.userDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            userDao.deleteAll();
            return null;
        }
    }


}
