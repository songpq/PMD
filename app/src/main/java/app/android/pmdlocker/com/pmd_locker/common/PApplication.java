package app.android.pmdlocker.com.pmd_locker.common;

import android.app.Application;
import android.support.compat.BuildConfig;

import app.android.pmdlocker.com.pmd_locker.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;



public class PApplication extends Application {
    /**
     * Define static properties.
     */

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts_ui/SF-UI-Display-Regular.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        // Setup host endpoint.
    }
}
