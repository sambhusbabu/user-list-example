package com.example.userslist.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.userslist.models.Server;

import java.util.List;

public class Repository {
    public UserDao userDao;
    public LiveData<List<Server>> getAllServerData;
    private ServerDatabase database;

    public Repository(Application application) {
        database = ServerDatabase.getInstance(application);
        userDao = database.userDao();
        getAllServerData = userDao.getServerData();

    }

    public void insert(List<Server> cats) {
        new InsertAsyncTask(userDao).execute(cats);

    }

    public void deleteAll() {
        new DeleteAllDataAsyncTask(userDao).execute();
    }

    public LiveData<List<Server>> getAllServerData() {
        return getAllServerData;
    }

    private static class InsertAsyncTask extends AsyncTask<List<Server>, Void, Void> {
        private UserDao userDao1;

        public InsertAsyncTask(UserDao catDao) {
            this.userDao1 = catDao;
        }

        @Override
        protected Void doInBackground(List<Server>... lists) {
            userDao1.insert(lists[0]);
            return null;
        }
    }

    private static class DeleteAllDataAsyncTask extends AsyncTask<Void, Void, Void> {
        private UserDao userDao1;

        private DeleteAllDataAsyncTask(UserDao userDao1) {
            this.userDao1 = userDao1;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            userDao1.deleteAll();
            return null;
        }
    }

}
