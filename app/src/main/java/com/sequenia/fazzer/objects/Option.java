package com.sequenia.fazzer.objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chybakut2004 on 24.02.15.
 */
public class Option implements Parcelable {
    private String id;
    private String label;

    public Option(String id, String label) {
        this.id = id;
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(label);
    }

    public final Parcelable.Creator<Option> CREATOR = new Creator<Option>() {
        @Override
        public Option createFromParcel(Parcel source) {
            return null;
        }

        @Override
        public Option[] newArray(int size) {
            return new Option[size];
        }
    };

    private Option(Parcel in) {
        this.id = in.readString();
        this.label = in.readString();
    }
}
