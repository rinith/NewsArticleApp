package com.example.newsarticleapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class PublisherModel implements Parcelable {
    String  name;
    boolean isChecked = false;

    public PublisherModel(String name) {
        this.name = name;
    }

    protected PublisherModel(Parcel in) {
        name = in.readString();
        isChecked = in.readByte() != 0;
    }

    public static final Creator<PublisherModel> CREATOR = new Creator<PublisherModel>() {
        @Override
        public PublisherModel createFromParcel(Parcel in) {
            return new PublisherModel(in);
        }

        @Override
        public PublisherModel[] newArray(int size) {
            return new PublisherModel[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeByte((byte) (isChecked ? 1 : 0));
    }
}
