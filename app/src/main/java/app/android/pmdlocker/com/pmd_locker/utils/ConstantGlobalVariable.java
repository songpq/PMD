package app.android.pmdlocker.com.pmd_locker.utils;


import app.android.pmdlocker.com.pmd_locker.R;

/**
 * Created by Ca. Phan Thanh on 3/28/2017.
 */

public class ConstantGlobalVariable {
    public static final String LANGUAGE_VI = "vi";
    public static final String LANGUAGE_EN = "en";

    public static final String []ARRAY_SUPPORT_LANGUAGE = {LANGUAGE_VI,LANGUAGE_EN};
    public static final int []ARRAY_SUPPORT_LANGUAGE_NAME = {R.string.text_language_vi,R.string.text_language_en};

    public static final String DEFAULT_LANGUAGE = "en";


    public static final int TIME_SHOW_LOGO = 3000;

    public static final int REQUEST_ACTIVITY_SETTINGS = 1001;
    public static final int REQUEST_ACTIVITY_SIGN_UP = REQUEST_ACTIVITY_SETTINGS + 1;
    public static final int REQUEST_ACTIVITY_OTP = REQUEST_ACTIVITY_SIGN_UP + 1;
    public static final int REQUEST_ACTIVITY_LOGIN = REQUEST_ACTIVITY_OTP + 1;
    public static final int REQUEST_ACTIVITY_FORGOT = REQUEST_ACTIVITY_LOGIN + 1;
    public static final int REQUEST_ACTIVITY_FORGOT_OTP = REQUEST_ACTIVITY_FORGOT + 1;
    public static final int REQUEST_ACTIVITY_FORGOT_RENEW_PASS = REQUEST_ACTIVITY_FORGOT_OTP + 1;

    public class SharedPreferencesKey
    {
        public final static String KEY_LANGUAGE = "Language";
        public final static String KEY_SHOW_TUTORIAL = "KeyShowTutorial";
        public final static String KEY_FCM = "FCM";
    }
    public final static String GOOGLE_CLOUD_MESSING_SENDER_ID="216180630898";
    public class SharedPreferencesKeyUser
    {
        public final static String KEY_USER_FIRST_NAME = "first_name";
        public final static String KEY_USER_LAST_NAME = "last_name";
        public final static String KEY_USER_NAME = "username";
        public final static String KEY_USER_EMAIL = "email";
        public final static String KEY_USER_PHONE = "phone";
        public final static String KEY_USER_AUTHEN_TOKEN = "authentication_token";
        public final static String KEY_USER_FB_ID = "fb_id";
        public final static String KEY_USER_PASSWORD = "password";
        public final static String KEY_USER_FB_ACCESS_TOKEN = "fb_access_token";
        public final static String KEY_USER_STATUS = "status";
        public final static String KEY_USER_USAGE_LOCKER ="usage_locker";
        public final static String KEY_USER_USAGE_DURATION ="usage_duration";
        public final static String KEY_USER_USAGE_LOKCER_LOCATION="locker_location";
        public final static String KEY_USER_USAGE_LOKCER_LOCATION_NAME="locker_location_name";
        public final static String KEY_USER_USAGE_WALLET="wallet";

    }
    public class ShareKeyIntent{
        public final static String KEY_PHONE = "KeyPhone";


    }

    public final static int POS_FRAGMENT_USERPROFILE = 0;
    public final static int POS_FRAGMENT_LOCATION = POS_FRAGMENT_USERPROFILE + 1;
    public final static int POS_FRAGMENT_WALLET = POS_FRAGMENT_LOCATION + 1;
    public final static int POS_FRAGMENT_BOOKING = POS_FRAGMENT_WALLET + 1;
    public final static int POS_FRAGMENT_NOTIFICATIONS = POS_FRAGMENT_BOOKING + 1;
    public final static int POS_FRAGMENT_DAILY_DEALS = POS_FRAGMENT_NOTIFICATIONS + 1;
    public final static int []ARRAY_BOTTOM_MENU =new int[]{POS_FRAGMENT_USERPROFILE,POS_FRAGMENT_LOCATION,POS_FRAGMENT_WALLET,POS_FRAGMENT_BOOKING,POS_FRAGMENT_NOTIFICATIONS,POS_FRAGMENT_DAILY_DEALS};

    public final static int ERROR_CODE_NOT_USER = 405;
    public final static String USER_STATUS_ACTIVE = "active";
    public final static String USER_STATUS_PENDING = "pending";
    public final static String USER_DEVICE_TYPE = "device_type";
    public final static String DEVICE_ANDROID = "0";

}
