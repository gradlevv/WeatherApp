package com.example.admin.weatherui.firebaseservice;


import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.example.admin.weatherui.config.Config;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        handleNotificatio(remoteMessage.getNotification().getBody());
    }

    private void handleNotificatio(String body) {
        Intent pushNotification = new Intent(Config.STR_PUSH);
        pushNotification.putExtra("message",body);
        LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
    }
}
