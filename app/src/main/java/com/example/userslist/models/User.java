package com.example.userslist.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.userslist.R;

import java.util.ArrayList;
import java.util.List;

public class User implements Parcelable {
    private String name;
    private int trips;
    private String _id;
    private List<Airline> airline = new ArrayList<>();

    public User() {
    }


    protected User(Parcel in) {
        name = in.readString();
        trips = in.readInt();
        _id = in.readString();
        airline = in.createTypedArrayList(Airline.CREATOR);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTrips() {
        return trips;
    }

    public void setTrips(int trips) {
        this.trips = trips;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<Airline> getAirline() {
        return airline;
    }

    public void setAirline(List<Airline> airline) {
        this.airline = airline;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(trips);
        dest.writeString(_id);
        dest.writeTypedList(airline);
    }
    @BindingAdapter("android:imageUrl")
    public static void loadImage(ImageView view, String url) {
        Glide.with(view)
                .load(url)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view);
    }

}
