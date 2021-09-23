package com.example.userslist.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Airline implements Parcelable {
    private int id;
    private String name;
    private String country;
    private String logo;
    private String slogan;
    private String head_quaters;
    private String website;
    private String established;

    public Airline() {
    }

    protected Airline(Parcel in) {
                id = in.readInt();
                name = in.readString();
                country = in.readString();
                logo = in.readString();
                slogan = in.readString();
                head_quaters = in.readString();
                website = in.readString();
                established = in.readString();
        }

        public static final Creator<Airline> CREATOR = new Creator<Airline>() {
                @Override
                public Airline createFromParcel(Parcel in) {
                        return new Airline(in);
                }

                @Override
                public Airline[] newArray(int size) {
                        return new Airline[size];
                }
        };

        public int getId() {
                return id;
        }

        public void setId(int id) {
                this.id = id;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getCountry() {
                return country;
        }

        public void setCountry(String country) {
                this.country = country;
        }

        public String getLogo() {
                return logo;
        }

        public void setLogo(String logo) {
                this.logo = logo;
        }

        public String getSlogan() {
                return slogan;
        }

        public void setSlogan(String slogan) {
                this.slogan = slogan;
        }

        public String getHead_quaters() {
                return head_quaters;
        }

        public void setHead_quaters(String head_quaters) {
                this.head_quaters = head_quaters;
        }

        public String getWebsite() {
                return website;
        }

        public void setWebsite(String website) {
                this.website = website;
        }

        public String getEstablished() {
                return established;
        }

        public void setEstablished(String established) {
                this.established = established;
        }

        @Override
        public int describeContents() {
                return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(id);
                dest.writeString(name);
                dest.writeString(country);
                dest.writeString(logo);
                dest.writeString(slogan);
                dest.writeString(head_quaters);
                dest.writeString(website);
                dest.writeString(established);
        }

    @Override
    public String toString() {
        return "Airline{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", logo='" + logo + '\'' +
                ", slogan='" + slogan + '\'' +
                ", head_quaters='" + head_quaters + '\'' +
                ", website='" + website + '\'' +
                ", established='" + established + '\'' +
                '}';
    }
}
