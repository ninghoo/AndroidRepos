package com.weydio.weydio16de31.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/1/4.
 */

public class Person implements Parcelable {
    String name;
    int age;
    boolean isMan;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.age);
        dest.writeByte(this.isMan ? (byte) 1 : (byte) 0);
    }

    public Person() {
    }

    protected Person(Parcel in) {
        this.name = in.readString();
        this.age = in.readInt();
        this.isMan = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel source) {
            return new Person(source);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
}
