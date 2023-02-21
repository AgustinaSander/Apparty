package com.example.apparty.persistence.room.mappers;

import static android.content.ContentValues.TAG;

import android.util.JsonReader;
import android.util.JsonWriter;
import android.util.Log;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static String fromStringSet(Set<String> strings) {
        if (strings==null) {
            return(null);
        }

        StringWriter result=new StringWriter();
        JsonWriter json=new JsonWriter(result);

        try {
            json.beginArray();

            for (String s : strings) {
                json.value(s);
            }

            json.endArray();
            json.close();
        }
        catch (IOException e) {
            Log.e(TAG, "Exception creating JSON", e);
        }

        return(result.toString());
    }

    @TypeConverter
    public static Set<String> toStringSet(String strings) {
        if (strings==null) {
            return(null);
        }

        StringReader reader=new StringReader(strings);
        JsonReader json=new JsonReader(reader);
        HashSet<String> result=new HashSet<>();

        try {
            json.beginArray();

            while (json.hasNext()) {
                result.add(json.nextString());
            }

            json.endArray();
        }
        catch (IOException e) {
            Log.e(TAG, "Exception parsing JSON", e);
        }

        return(result);
    }

    @TypeConverter
    public static ArrayList<String> fromString(String value) {
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

}
