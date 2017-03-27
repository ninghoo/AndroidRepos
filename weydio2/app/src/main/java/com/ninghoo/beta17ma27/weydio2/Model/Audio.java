package com.ninghoo.beta17ma27.weydio2.Model;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;

/**
 * Created by ningfu on 17-3-24.
 */

public class Audio implements Parcelable {

    private String mTitle,
            mTitleKey,
            mArtist,
            mArtistKey,
            mComposer,
            mAlbum,
            mAlbumKey,
            mDisplayName,
            mMimeType,
            mPath;

    private int mId,
            mArtistId,
            mAlbumId,
            mYear,
            mTrack;

    private int mDuration = 0,
            mSize;

    private boolean isRingtone = false,
            isPodcast = false,
            isAlarm = false,
            isMusic = false,
            isNotification = false;

    public Audio (Bundle bundle)
    {
        mId = bundle.getInt(MediaStore.Audio.Media._ID);
        mTitle = bundle.getString(MediaStore.Audio.Media.TITLE);
        mTitleKey = bundle.getString(MediaStore.Audio.Media.TITLE_KEY);
        mArtist = bundle.getString(MediaStore.Audio.Media.ARTIST);
        mArtistKey = bundle.getString(MediaStore.Audio.Media.ARTIST_KEY);
        mComposer = bundle.getString(MediaStore.Audio.Media.COMPOSER);

        mAlbum = bundle.getString(MediaStore.Audio.Media.ALBUM);
        mAlbumKey = bundle.getString(MediaStore.Audio.Media.ALBUM_KEY);
        mDisplayName = bundle.getString(MediaStore.Audio.Media.DISPLAY_NAME);
        mYear = bundle.getInt(MediaStore.Audio.Media.YEAR);
        mMimeType = bundle.getString(MediaStore.Audio.Media.MIME_TYPE);
        mPath = bundle.getString(MediaStore.Audio.Media.DATA);

        mArtistId = bundle.getInt(MediaStore.Audio.Media.ARTIST_ID);
        mAlbumId = bundle.getInt(MediaStore.Audio.Media.ALBUM_ID);
        mTrack = bundle.getInt(MediaStore.Audio.Media.TRACK);
        mDuration = bundle.getInt(MediaStore.Audio.Media.DURATION);
        mSize = bundle.getInt(MediaStore.Audio.Media.SIZE);

        isRingtone = bundle.getInt(MediaStore.Audio.Media.IS_RINGTONE) == 1;
        isPodcast = bundle.getInt(MediaStore.Audio.Media.IS_PODCAST) == 1;
        isAlarm = bundle.getInt(MediaStore.Audio.Media.IS_ALARM) == 1;
        isMusic = bundle.getInt(MediaStore.Audio.Media.IS_MUSIC) == 1;
        isNotification = bundle.getInt(MediaStore.Audio.Media.IS_NOTIFICATION) == 1;
    }


    public int getmId() {
        return mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmTitleKey() {
        return mTitleKey;
    }

    public String getmArtist() {
        return mArtist;
    }

    public String getmArtistKey() {
        return mArtistKey;
    }

    public String getmComposer() {
        return mComposer;
    }

    public String getmAlbum() {
        return mAlbum;
    }

    public String getmAlbumKey() {
        return mAlbumKey;
    }

    public String getmDisplayName() {
        return mDisplayName;
    }

    public String getmMimeType() {
        return mMimeType;
    }

    public String getmPath() {
        return mPath;
    }

    public int getmArtistId() {
        return mArtistId;
    }

    public int getmAlbumId() {
        return mAlbumId;
    }

    public int getmYear() {
        return mYear;
    }

    public int getmTrack() {
        return mTrack;
    }

    public int getmDuration() {
        return mDuration;
    }

    public int getmSize() {
        return mSize;
    }

    public boolean isRingtone() {
        return isRingtone;
    }

    public boolean isPodcast() {
        return isPodcast;
    }

    public boolean isAlarm() {
        return isAlarm;
    }

    public boolean isMusic() {
        return isMusic;
    }

    public boolean isNotification() {
        return isNotification;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mTitle);
        dest.writeString(this.mTitleKey);
        dest.writeString(this.mArtist);
        dest.writeString(this.mArtistKey);
        dest.writeString(this.mComposer);
        dest.writeString(this.mAlbum);
        dest.writeString(this.mAlbumKey);
        dest.writeString(this.mDisplayName);
        dest.writeString(this.mMimeType);
        dest.writeString(this.mPath);
        dest.writeInt(this.mId);
        dest.writeInt(this.mArtistId);
        dest.writeInt(this.mAlbumId);
        dest.writeInt(this.mYear);
        dest.writeInt(this.mTrack);
        dest.writeInt(this.mDuration);
        dest.writeInt(this.mSize);
        dest.writeByte(this.isRingtone ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isPodcast ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isAlarm ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isMusic ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isNotification ? (byte) 1 : (byte) 0);
    }

    protected Audio(Parcel in) {
        this.mTitle = in.readString();
        this.mTitleKey = in.readString();
        this.mArtist = in.readString();
        this.mArtistKey = in.readString();
        this.mComposer = in.readString();
        this.mAlbum = in.readString();
        this.mAlbumKey = in.readString();
        this.mDisplayName = in.readString();
        this.mMimeType = in.readString();
        this.mPath = in.readString();
        this.mId = in.readInt();
        this.mArtistId = in.readInt();
        this.mAlbumId = in.readInt();
        this.mYear = in.readInt();
        this.mTrack = in.readInt();
        this.mDuration = in.readInt();
        this.mSize = in.readInt();
        this.isRingtone = in.readByte() != 0;
        this.isPodcast = in.readByte() != 0;
        this.isAlarm = in.readByte() != 0;
        this.isMusic = in.readByte() != 0;
        this.isNotification = in.readByte() != 0;
    }

    public static final Creator<Audio> CREATOR = new Creator<Audio>() {
        @Override
        public Audio createFromParcel(Parcel source) {
            return new Audio(source);
        }

        @Override
        public Audio[] newArray(int size) {
            return new Audio[size];
        }
    };
}

