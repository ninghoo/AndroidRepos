package com.ninghoo.beta17ma27.weydio2.Model;

/**
 * Created by ningfu on 17-3-24.
 */

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;

import java.util.ArrayList;

/**
 * Created by ningfu on 17-2-8.
 */

public class MediaDetails
{
    public static final String[] AUDIO_KEYS = new String[]
            {
                    MediaStore.Audio.Media._ID,
                    MediaStore.Audio.Media.TITLE,
                    MediaStore.Audio.Media.TITLE_KEY,
                    MediaStore.Audio.Media.ARTIST,
                    MediaStore.Audio.Media.ARTIST_KEY,
                    MediaStore.Audio.Media.COMPOSER,

                    MediaStore.Audio.Media.ALBUM,
                    MediaStore.Audio.Media.ALBUM_KEY,
                    MediaStore.Audio.Media.DISPLAY_NAME,
                    MediaStore.Audio.Media.YEAR,
                    MediaStore.Audio.Media.MIME_TYPE,
                    MediaStore.Audio.Media.DATA,

                    MediaStore.Audio.Media.ARTIST_ID,
                    MediaStore.Audio.Media.ALBUM_ID,
                    MediaStore.Audio.Media.TRACK,
                    MediaStore.Audio.Media.DURATION,
                    MediaStore.Audio.Media.SIZE,

                    MediaStore.Audio.Media.IS_RINGTONE,
                    MediaStore.Audio.Media.IS_PODCAST,
                    MediaStore.Audio.Media.IS_ALARM,
                    MediaStore.Audio.Media.IS_MUSIC,
                    MediaStore.Audio.Media.IS_NOTIFICATION,

                    MediaStore.Audio.Media.DATE_ADDED
            };

    // 由于是static方法，所以可以直接使用类名直接调用该方法。
    public static ArrayList<Audio> getAudioList(Context context, int o)
    {
        // 实际上这里是一个向下转型。
        ArrayList<Audio> autoList = new ArrayList<Audio>();

        ContentResolver resolver = context.getContentResolver();

        Cursor cursor = null;

        switch (o)
        {
            // A_Z
            case 0:
                // query方法最后一个参数可实现排序。
                cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        AUDIO_KEYS, null, null, MediaStore.Audio.Media.TITLE );
                // + " DESC"可实现倒序。
                break;

            // time
            case 1:
//                cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
//                        AUDIO_KEYS, null, null, MediaStore.Audio.Media._ID  + " DESC");
                cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        AUDIO_KEYS, null, null, MediaStore.Audio.Media._ID);
                break;

            // artist
            case 2:
                cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        AUDIO_KEYS, null, null, MediaStore.Audio.Media.ARTIST);
                break;
        }

//        cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
//                        AUDIO_KEYS, null, null, MediaStore.Audio.Media.TITLE );

        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
        {
            Bundle bundle = new Bundle();

            for(int i = 0; i < AUDIO_KEYS.length; i++)
            {
                final String key = AUDIO_KEYS[i];
                final int columnIndex = cursor.getColumnIndex(key);
                // column纵列。Index指标。

                final int type = cursor.getType(columnIndex);

                switch(type)
                {
                    case Cursor.FIELD_TYPE_BLOB:
                        break;

                    case Cursor.FIELD_TYPE_FLOAT:
                        float floatValue = cursor.getFloat(columnIndex);
                        bundle.putFloat(key, floatValue);
                        break;

                    case Cursor.FIELD_TYPE_INTEGER:
                        int intValue = cursor.getInt(columnIndex);
                        bundle.putInt(key, intValue);
                        break;

                    case Cursor.FIELD_TYPE_NULL:
                        break;

                    case Cursor.FIELD_TYPE_STRING:
                        String stringValue = cursor.getString(columnIndex);
                        bundle.putString(key, stringValue);
                        break;
                }
            }

            Audio audio = new Audio(bundle);

            autoList.add(audio);
        }

        cursor.close();
        return autoList;
    }
}


