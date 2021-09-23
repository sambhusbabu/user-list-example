package com.example.userslist.models;

import java.util.ArrayList;
import java.util.List;

public class UserData {

    String totalPassengers;
    String  totalPages;
    List<User> data = new ArrayList<>();

    public UserData() {
    }

    public String getTotalPassengers() {
        return totalPassengers;
    }

    public void setTotalPassengers(String totalPassengers) {
        this.totalPassengers = totalPassengers;
    }

    public String getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(String totalPages) {
        this.totalPages = totalPages;
    }

    public List<User> getData() {
        return data;
    }

    public void setData(List<User> data) {
        this.data = data;
    }
}
