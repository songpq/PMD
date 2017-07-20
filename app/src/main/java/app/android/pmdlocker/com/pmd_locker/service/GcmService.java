/*
Copyright 2015 Google Inc. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package app.android.pmdlocker.com.pmd_locker.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import org.json.JSONException;

import app.android.pmdlocker.com.pmd_locker.MainActivity;
import app.android.pmdlocker.com.pmd_locker.R;
import app.android.pmdlocker.com.pmd_locker.models.objects.HNotificationObj;


/**
 * Service used for receiving GCM messages. When a message is received this service will log it.
 */
public class GcmService extends GcmListenerService {

    private static final String TAG = GcmService.class.getName();
    private LoggingService.Logger logger;

    public GcmService() {
        logger = new LoggingService.Logger(this);
    }

    @Override
    public void onMessageReceived(String from, Bundle data) {
        Log.d(TAG,data.toString());
        String message = data.getString("message");
        if(!TextUtils.isEmpty(message))
        {
            try{
                HNotificationObj hNotificationObj = HNotificationObj.parseNotification(message);
                sendNotification(hNotificationObj);
            }catch (JSONException e)
            {

            }
        }

    }

    @Override
    public void onDeletedMessages() {
        Log.d(TAG,"DELETE GCM");
        //sendNotification("Deleted messages on server");
    }

    @Override
    public void onMessageSent(String msgId) {
        Log.d(TAG,"DELETE SENT:"+msgId);
        //sendNotification("Upstream message sent. Id=" + msgId);
    }

    @Override
    public void onSendError(String msgId, String error) {
        //sendNotification("Upstream message send error. Id=" + msgId + ", error" + error);
    }

    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.
    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;
    private void sendNotification(HNotificationObj hNotificationObj) {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent intent = PendingIntent.getActivity(getApplicationContext(), 0,
                notificationIntent, 0);



        notificationBuilder = new NotificationCompat.Builder(this)
                .setContentIntent(intent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(hNotificationObj.title)
                .setContentText(hNotificationObj.message)
                .setAutoCancel(true);
        notificationManager.notify(0, notificationBuilder.build());




        logger.log(Log.INFO, hNotificationObj.toString());


    }
}
