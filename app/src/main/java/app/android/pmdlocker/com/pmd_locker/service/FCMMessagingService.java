package app.android.pmdlocker.com.pmd_locker.service;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by tuanhnm on 9/15/16.
 */
public class FCMMessagingService extends FirebaseMessagingService {

  private static final String TAG = FCMMessagingService.class.getName();

  @Override public void onMessageReceived(RemoteMessage remoteMessage) {
    super.onMessageReceived(remoteMessage);
//    PreferencesUtility.getInstance()
//        .setBoolean(PreferenceKeys.PREF_KEY_HAS_UPDATE_NOTIFICATION, true);
//    if (!CommonUtils.isAppIsInBackground()) {
//      NotificationUtility.getInstance()
//          .buildInAppNoti(NotificationUtility.InAppNotiType.INFO_NOTI,
//              remoteMessage.getNotification().getBody(), null, USnackBar.LENGTH_LONG);
//    } else {
//      NotificationUtility.getInstance()
//          .buildPushNoti(remoteMessage.getNotification().getTitle(),
//              remoteMessage.getNotification().getBody(),android.R.drawable.ic_dialog_email);
//    }
  }
}
