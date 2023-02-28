package com.example.apparty.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Utils {
    private static Gson gson;

    public static Gson getGsonParser() {
        if(null == gson) {
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer());
            builder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
            gson = builder.create();
        }
        return gson;
    }

    public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        return Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
    }

    public static byte[] getStringFromBitmap(Bitmap bitmapPicture){
        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
        bitmapPicture.compress(Bitmap.CompressFormat.PNG, 0, byteArrayBitmapStream);
        return byteArrayBitmapStream.toByteArray();
    }

    public static Bitmap getBitmapFromString(byte[] b){
        return BitmapFactory.decodeByteArray(b, 0, b.length);
    }

}
