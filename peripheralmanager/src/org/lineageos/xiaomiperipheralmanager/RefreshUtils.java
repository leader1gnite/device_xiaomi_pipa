package org.lineageos.xiaomiperipheralmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.util.Log;

public final class RefreshUtils {
    private static final String TAG = "XiaomiPeripheralManagerRefreshUtils";
    private static final String KEY_PEAK_REFRESH_RATE = "peak_refresh_rate";
    private static final String KEY_MIN_REFRESH_RATE = "min_refresh_rate";
    private static final String KEY_PEN_MODE = "pen_mode";
    private static final String PREF_FILE_NAME = "pen_refresh_prefs";

    private Context mContext;
    private SharedPreferences mSharedPrefs;

    public RefreshUtils(Context context) {
        mContext = context;
        mSharedPrefs = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    public void setPenRefreshRate() {
        boolean penMode = mSharedPrefs.getBoolean(KEY_PEN_MODE, false);

        // Only set the refresh rate if pen mode is being enabled
        if (!penMode) {
            float currentMaxRate = Settings.System.getFloat(mContext.getContentResolver(), KEY_PEAK_REFRESH_RATE, 144f);

            // Set refresh rate to 120Hz only if it's not already set to 120Hz
            if (currentMaxRate != 120f) {
                float minRate = Settings.System.getFloat(mContext.getContentResolver(), KEY_MIN_REFRESH_RATE, 60f);
                mSharedPrefs.edit()
                        .putFloat(KEY_MIN_REFRESH_RATE, minRate)
                        .putFloat(KEY_PEAK_REFRESH_RATE, 120f)
                        .putBoolean(KEY_PEN_MODE, true)
                        .apply();

                // Set the values in the Settings.System
                Settings.System.putFloat(mContext.getContentResolver(), KEY_MIN_REFRESH_RATE, minRate);
                Settings.System.putFloat(mContext.getContentResolver(), KEY_PEAK_REFRESH_RATE, 120f);
                Log.d(TAG, "Pen mode enabled: Refresh rate set to 120Hz");
            }
        } else {
            Log.d(TAG, "Pen mode is already enabled; no changes made to refresh rate.");
        }
    }

    public void resetPenMode() {
        mSharedPrefs.edit().putBoolean(KEY_PEN_MODE, false).apply();
        Log.d(TAG, "Pen mode reset to false");
    }
}
