package moneygroup.devufa.ru.moneygroup.service.firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Random;

import moneygroup.devufa.ru.moneygroup.R;
import moneygroup.devufa.ru.moneygroup.activity.HomeActivity;
import moneygroup.devufa.ru.moneygroup.activity.NotificationsActivity;

import static android.support.constraint.Constraints.TAG;

public class NotificationsService extends FirebaseMessagingService {
    public NotificationsService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getNotification() != null) {
            if (remoteMessage.getData().size() > 0) {
                Log.d(TAG, "Message data payload: " + remoteMessage.getData().get("id"));


            }
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage);
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    private void sendNotification(RemoteMessage remoteMessage) {
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Log.d("msg", "onMessageReceived: " + remoteMessage.getData().get("message"));
        PendingIntent pendingIntent;

        if (remoteMessage.getData().get("type") != null) {
            Map<String, String> debtMap = remoteMessage.getData();
            Intent intent = new Intent(this, NotificationsActivity.class);
            intent.putExtra("id", debtMap.get("id"));
            intent.putExtra("telephoneNumber", debtMap.get("telephoneNumber"));
            intent.putExtra("currentCount", debtMap.get("currentCount"));
            intent.putExtra("type", debtMap.get("type"));
            intent.putExtra("title", remoteMessage.getNotification().getTitle());
            intent.putExtra("body", remoteMessage.getNotification().getBody());
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            pendingIntent = PendingIntent.getActivity(this, new Random().nextInt(), intent, PendingIntent.FLAG_ONE_SHOT);
        } else {
            Intent intent = new Intent(this, HomeActivity.class);
            pendingIntent = PendingIntent.getActivity(this, new Random().nextInt(), intent, PendingIntent.FLAG_ONE_SHOT);
        }


        String channelId = "Default";
        NotificationCompat.Builder builder = new  NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.logo11)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.logo11))
                .setContentTitle(this.getString(R.string.app_name))
                .setContentText(remoteMessage.getNotification().getBody()).setAutoCancel(true).setContentIntent(pendingIntent)
                .setSound(defaultSoundUri);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        int id = new Random().nextInt();
        manager.notify(id, builder.build());


    }


    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);
    }
}
