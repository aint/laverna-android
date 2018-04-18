//package com.github.android.lvrn.lvrnproject.persistent.entity;
//
//import android.os.Parcel;
//import android.os.Parcelable;
//import android.support.annotation.NonNull;
//
///**
// * @author Vadim Boitsov <vadimboitsov1@gmail.com>
// */
//
//public class Profile extends Entity {
//
//    @NonNull
//    private String name;
//
//    public Profile(@NonNull String id, @NonNull String name) {
//        this.id = id;
//        this.name = name;
//    }
//
//    private Profile(Parcel in) {
//        this.id = in.readString();
//        this.name = in.readString();
//    }
//
//    @NonNull
//    public String getId() {
//        return id;
//    }
//
//    public void setId(@NonNull String id) {
//        this.id = id;
//    }
//
//    @NonNull
//    public String getName() {
//        return name;
//    }
//
//    public void setName(@NonNull String name) {
//        this.name = name;
//    }
//
//    @NonNull
//    @Override
//    public String toString() {
//        return "Profile{" + super.toString() +
//                "name='" + name + '\'' +
//                '}';
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(this.id);
//        dest.writeString(this.name);
//    }
//
//    public static final Parcelable.Creator<Profile> CREATOR = new Parcelable.Creator<Profile>() {
//
//        @Override
//        public Profile createFromParcel(Parcel source) {
//            return new Profile(source);
//        }
//
//        @Override
//        public Profile[] newArray(int size) {
//            return new Profile[size];
//        }
//    };
//}
