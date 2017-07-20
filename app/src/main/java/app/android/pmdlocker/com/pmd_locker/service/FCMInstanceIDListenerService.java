package app.android.pmdlocker.com.pmd_locker.service;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import app.android.pmdlocker.com.pmd_locker.utils.ConstantGlobalVariable;
import app.android.pmdlocker.com.pmd_locker.utils.Utility;

/**
 * Created by tuanhnm on 9/15/16.
 */
public class FCMInstanceIDListenerService extends FirebaseInstanceIdService {


  public FCMInstanceIDListenerService() {
  }

  @Override
  public void onTokenRefresh() {
    String refreshedToken = FirebaseInstanceId.getInstance().getToken();
    Log.d("token",refreshedToken);
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    Utility.saveKeyString(ConstantGlobalVariable.SharedPreferencesKey.KEY_FCM,refreshedToken,prefs);

  }
}
