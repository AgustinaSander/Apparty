package com.example.apparty;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AirplaneModeReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID= "notification_channel";

    @Override
    public void onReceive(Context context, Intent intent) {


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            showNotification(context.getApplicationContext());
        } else {
            showNewNotification(context.getApplicationContext());
       }
    }

    private boolean isAiplaneModeOn(Context applicationContext) {
        return Settings.System.getInt(applicationContext.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
    }

    private void showNotification(Context context) {
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "New", NotificationManager.IMPORTANCE_DEFAULT);

        NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        manager.createNotificationChannel(channel);
        showNewNotification(context);
    }

    private void showNewNotification(Context context){
        String contextText = isAiplaneModeOn(context) ? "El modo avion ha sido activado." : "El modo avion ha sido desactivado.";

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.glass_cheers_24)
                .setContentTitle("Modo avion")
                .setContentText(contextText)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context.getApplicationContext());
        managerCompat.notify(1, builder.build());
    }
}
