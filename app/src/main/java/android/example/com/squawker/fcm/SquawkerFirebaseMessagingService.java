package android.example.com.squawker.fcm;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.example.com.squawker.R;
import android.support.annotation.Nullable;
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

        if (remoteMessage != null) {

            Map<String, String> data = remoteMessage.getData();
            String author = data.get("author");
            String authorKey = data.get("authorKey");
            String message = data.get("message");
            String date = data.get("date");


            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_duck)
                    .setContentTitle(author)
                    .setContentText(message)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true);

            NotificationManagerCompat notific = NotificationManagerCompat.from(this);
            notific.notify(4, mBuilder.build());


            ContentValues values = new ContentValues();
            values.put("author", author);
            values.put("authorKey", authorKey);
            values.put("message", message);
            values.put("date", date);

            ContentResolver resolver = getContentResolver();

            new myTask(resolver, values);


            //resolver.insert(SquawkProvider.SquawkMessages.CONTENT_URI,values);
        }
    }


    public static class myTask extends IntentService {

        ContentResolver mResolver;
        ContentValues mValues;



        public myTask(ContentResolver resolver, ContentValues values) {
            super("");
            mResolver = resolver;
            mValues = values;
        }

        @Override
        protected void onHandleIntent(@Nullable Intent intent) {
            mResolver.insert(android.example.com.squawker.provider.SquawkProvider.SquawkMessages.CONTENT_URI, mValues);
            Log.d("onHandleIntent", "inserted the intent!");
        }
    }
}
