package android.example.com.squawker.fcm;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.example.com.squawker.R;
import android.example.com.squawker.provider.SquawkProvider;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by avigagel on 26/02/2018.
 */

public class SquawkerFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d("onMessageReceived", "Received message!");

        if (remoteMessage != null) {

            Map<String, String> data = remoteMessage.getData();

            displayNotification(data);
            insertToLocalDb(data);

        }
    }


    private void displayNotification(Map<String, String> notificMapData) {

        String author = notificMapData.get("author");
        // String authorKey = notificMapData.get("authorKey");
        String message = notificMapData.get("message");
        // String date = notificMapData.get("date");

        String subMessage = message;
        if (message.length() > 30) subMessage = message.substring(0, 30);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_duck)
                .setContentTitle(author)
                .setContentText(subMessage)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManagerCompat notific = NotificationManagerCompat.from(this);
        notific.notify(4, mBuilder.build());
    }



    private void insertToLocalDb(final Map<String, String> notificMapData) {

        new AsyncTask<Void, Void, Void>()  {

            @Override
            protected Void doInBackground(Void... voids) {

                ContentValues values = new ContentValues();
                values.put("author", notificMapData.get("author"));
                values.put("authorKey", notificMapData.get("authorKey"));
                values.put("message", notificMapData.get("message"));
                values.put("date", notificMapData.get("date"));

                ContentResolver resolver = getContentResolver();
                resolver.insert(SquawkProvider.SquawkMessages.CONTENT_URI,values);
                return null;
            }
        }.execute();

        Log.d("AsyncTask", "DB operation done!");
    }

}
