package com.josjos.aseloe.api19.Service;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;
import com.josjos.aseloe.api19.Entity.FilmItems;
import com.josjos.aseloe.api19.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

import static com.josjos.aseloe.api19.MainActivity.PlayingNow;

public class AlarmBrodcastReceiver extends BroadcastReceiver {

    ArrayList<FilmItems> filmItemGeneral = new ArrayList<>();
    Locale current;
    public static final int NOTIF_ID_DAILY = 200;
    public static final int NOTIF_ID_TODAY = 201;
    public static final String EXTRA_TITLE = "title";

    public AlarmBrodcastReceiver(){

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int type = intent.getIntExtra(EXTRA_TITLE,NOTIF_ID_DAILY);

        if(type==NOTIF_ID_TODAY)
            getTitlesWeb(context);

        System.out.println(filmItemGeneral.get(0).getTitle());

        String title = type == NOTIF_ID_TODAY ? filmItemGeneral.get(0).getTitle() : "Catalogue Movie";
        String message = type == NOTIF_ID_TODAY ? "Hari ini " + filmItemGeneral.get(0).getTitle() + "rilis!" : "We miss you!";
        int notifId = type == NOTIF_ID_TODAY ? NOTIF_ID_TODAY : NOTIF_ID_DAILY;

        showAlarmNotification(context, title, message, notifId);
    }

    private void showAlarmNotification(Context context, String title, String message, int notifId){
        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_sync_black_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    "1", "NOTIFICATION_CHANNEL_NAME",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.YELLOW);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            builder.setChannelId("1");
            notificationManagerCompat.createNotificationChannel(notificationChannel);
        }

        notificationManagerCompat.notify(notifId, builder.build());
    }

    public void cancelAlarm(Context context, int type){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmBrodcastReceiver.class);
        int requestCode = type == NOTIF_ID_TODAY ? NOTIF_ID_TODAY : NOTIF_ID_DAILY;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        alarmManager.cancel(pendingIntent);
    }

    public void setRepeatingAlarm(Context context, int type){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmBrodcastReceiver.class);
        intent.putExtra(EXTRA_TITLE,type);

        Calendar calendar = Calendar.getInstance();

        if(type==NOTIF_ID_DAILY)
            calendar.set(Calendar.HOUR_OF_DAY, 7);
        else{
            calendar.set(Calendar.HOUR_OF_DAY, 8);
        }


        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        System.out.println(calendar.getTime());
        int requestCode = type;


        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    PendingIntent.getBroadcast(context,type,intent,PendingIntent.FLAG_CANCEL_CURRENT));

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    PendingIntent.getBroadcast(context,type,intent,PendingIntent.FLAG_CANCEL_CURRENT));
        }

        Toast.makeText(context, "Repeating alarm set up", Toast.LENGTH_SHORT).show();
    }


    private void getTitlesWeb(Context context){
        SyncHttpClient client = new SyncHttpClient();

         Date c = Calendar.getInstance().getTime();
         SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        final String formattedDate = df.format(c);

        client.get(PlayingNow + current.getDefault().getLanguage(), new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try{
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length() ; i++){
                        JSONObject film = list.getJSONObject(i);
                        FilmItems filmItems = new FilmItems(film);

                        if(filmItems.getRelease_date().equalsIgnoreCase(formattedDate))
                            filmItemGeneral.add(filmItems);

                        System.out.println("Current time => " + formattedDate);
                        System.out.println("DataReal => " + filmItems.getRelease_date());

                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }
}
