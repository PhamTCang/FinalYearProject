package my.traditionalfood.application.SendNotification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import java.util.Random;

import my.traditionalfood.application.Account.MainActivity;
import my.traditionalfood.application.ChefFoodPanel.BottomNav_ChefFoodPanel;
import my.traditionalfood.application.ChefFunctions.ChefAcceptedOrderViewDetail;
import my.traditionalfood.application.CusFoodPanel.BottomNav_CusFoodPanel;
import my.traditionalfood.application.R;
import my.traditionalfood.application.ShipFoodPanel.BottomNav_ShipFoodPanel;

public class ShowNotification {

    public static void ShowNotif(Context context, String title, String message, String page) {
        String CHANNEL_ID = "NOTICE";
        String CHANNEL_NAME = "NOTICE";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setLockscreenVisibility(NotificationCompat.VISIBILITY_PUBLIC);
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        Intent acIntent = new Intent(context, MainActivity.class);
        acIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, acIntent, PendingIntent.FLAG_ONE_SHOT);
        if (page.trim().equalsIgnoreCase("Order")) {
            acIntent = new Intent(context, BottomNav_ChefFoodPanel.class).putExtra("Page", "OrderPage");
            pendingIntent = PendingIntent.getActivity(context, 0, acIntent, PendingIntent.FLAG_ONE_SHOT);
        }
        if (page.trim().equalsIgnoreCase("Payment")) {
            acIntent = new Intent(context, PayableOrders.class);
            pendingIntent = PendingIntent.getActivity(context, 0, acIntent, PendingIntent.FLAG_ONE_SHOT);
        }
        if (page.trim().equalsIgnoreCase("Home")) {
            acIntent = new Intent(context, BottomNav_CusFoodPanel.class).putExtra("Page", "HomePage");
            pendingIntent = PendingIntent.getActivity(context, 0, acIntent, PendingIntent.FLAG_ONE_SHOT);
        }
        if (page.trim().equalsIgnoreCase("Confirm")) {
            acIntent = new Intent(context, BottomNav_ChefFoodPanel.class).putExtra("Page", "ConfirmPage");
            pendingIntent = PendingIntent.getActivity(context, 0, acIntent, PendingIntent.FLAG_ONE_SHOT);
        }
        if (page.trim().equalsIgnoreCase("Preparing")) {
            acIntent = new Intent(context, BottomNav_CusFoodPanel.class).putExtra("Page", "FoodCartPage");
            pendingIntent = PendingIntent.getActivity(context, 0, acIntent, PendingIntent.FLAG_ONE_SHOT);
        }
        if (page.trim().equalsIgnoreCase("Prepared")) {
            acIntent = new Intent(context, BottomNav_CusFoodPanel.class).putExtra("Page", "TrackOrderPage");
            pendingIntent = PendingIntent.getActivity(context, 0, acIntent, PendingIntent.FLAG_ONE_SHOT);
        }
        if (page.trim().equalsIgnoreCase("DeliveryOrder")) {
            acIntent = new Intent(context, BottomNav_ShipFoodPanel.class).putExtra("Page", "ShippingOrderPage");
            pendingIntent = PendingIntent.getActivity(context, 0, acIntent, PendingIntent.FLAG_ONE_SHOT);
        }
        if (page.trim().equalsIgnoreCase("DeliverOrder")) {
            acIntent = new Intent(context, BottomNav_CusFoodPanel.class).putExtra("Page", "DeliverOrderPage");
            pendingIntent = PendingIntent.getActivity(context, 0, acIntent, PendingIntent.FLAG_ONE_SHOT);
        }
        if (page.trim().equalsIgnoreCase("AcceptOrder")) {
            acIntent = new Intent(context, BottomNav_ChefFoodPanel.class).putExtra("Page", "AcceptOrderPage");
            pendingIntent = PendingIntent.getActivity(context, 0, acIntent, PendingIntent.FLAG_ONE_SHOT);
        }
        if (page.trim().equalsIgnoreCase("RejectOrder")) {
            acIntent = new Intent(context, ChefAcceptedOrderViewDetail.class).putExtra("Page", "RejectOrderPage");
            pendingIntent = PendingIntent.getActivity(context, 0, acIntent, PendingIntent.FLAG_ONE_SHOT);
        }
        if (page.trim().equalsIgnoreCase("ThankYou")) {
            acIntent = new Intent(context, BottomNav_CusFoodPanel.class).putExtra("Page", "ThankYouPage");
            pendingIntent = PendingIntent.getActivity(context, 0, acIntent, PendingIntent.FLAG_ONE_SHOT);
        }
        if (page.trim().equalsIgnoreCase("Delivered")) {
            acIntent = new Intent(context, BottomNav_ChefFoodPanel.class).putExtra("Page", "DeliveredPage");
            pendingIntent = PendingIntent.getActivity(context, 0, acIntent, PendingIntent.FLAG_ONE_SHOT);
        }


        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(context, CHANNEL_ID).setSmallIcon(R.drawable.ic_baseline_restaurant_24)
                .setColor(ContextCompat.getColor(context, R.color.gray))
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        int random = new Random().nextInt(9999 - 1) + 1;
        notificationManagerCompat.notify(random, nBuilder.build());
    }
}
