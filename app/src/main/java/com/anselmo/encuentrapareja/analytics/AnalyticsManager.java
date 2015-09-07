package com.anselmo.encuentrapareja.analytics;

import android.content.Context;
import android.util.Log;

import com.anselmo.encuentrapareja.BuildConfig;
import com.anselmo.encuentrapareja.R;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by alan on 06/06/15.
 */
public class AnalyticsManager {
    private static Context sAppContext = null;

    private static Tracker mTracker;
    private final static String TAG = "AnalyticsManager";

    public static synchronized void setTracker(Tracker tracker) {
        mTracker = tracker;
    }

    private static boolean canSend() {
        return sAppContext != null && mTracker != null;
    }

    public static void sendScreenView(String screenName) {
        Log.d(TAG, "candSend: " + canSend());
        if (canSend()) {
            mTracker.setScreenName(screenName);
            mTracker.send(new HitBuilders.AppViewBuilder().build());
            Log.d(TAG, "Screen View recorded: " + screenName);
        } else {
            Log.d(TAG, "Screen View NOT recorded (analytics disabled or not ready).");
        }
    }

    public static void sendScreenView() {
        Log.d(TAG, "candSend: " + canSend());
        if (canSend()) {
            mTracker.send(new HitBuilders.AppViewBuilder().build());
            Log.d(TAG, "Screen View recorded");
        } else {
            Log.d(TAG, "Screen View NOT recorded (analytics disabled or not ready).");
        }
    }

    public Tracker getTracker() {
        return mTracker;
    }

    public static synchronized void initializeAnalyticsTracker(Context context) {
        sAppContext = context;
        if (mTracker == null) {
            int useProfile;
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "Analytics manager using DEBUG ANALYTICS PROFILE.");
                useProfile = R.xml.app_tracker_test;
            } else {
                useProfile = R.xml.app_tracker;
            }
            GoogleAnalytics mgA= GoogleAnalytics.getInstance(context);
            mgA.getLogger().setLogLevel(Logger.LogLevel.VERBOSE);
            mTracker = mgA.newTracker(useProfile);
        }
    }

    //region report hits
    public static void sendEvent(String category, String action, String label, long value) {
        if (canSend()) {
            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory(category)
                    .setAction(action)
                    .setLabel(label)
                    .setValue(value)
                    .build());

            Log.d(TAG, "Event recorded:");
            Log.d(TAG, "\tCategory: " + category);
            Log.d(TAG, "\tAction: " + action);
            Log.d(TAG, "\tLabel: " + label);
            Log.d(TAG, "\tValue: " + value);
        } else {
            Log.d(TAG, "Analytics event ignored (analytics disabled or not ready).");
        }
    }

    public static void sendEvent(String category, String action, String label) {
        sendEvent(category, action, label, 0);
    }
}
