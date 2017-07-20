package app.android.pmdlocker.com.pmd_locker.utils;

import android.util.Log;

import app.android.pmdlocker.com.pmd_locker.BuildConfig;
import okhttp3.logging.HttpLoggingInterceptor;


/**
 * Created by tuanhoang on 3/23/17.
 */

public class EndpointUtil {
    public static HttpLoggingInterceptor getLogging() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                if (BuildConfig.DEBUG) {
                    Log.e("--okhttp--", message);
                }
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }
}
