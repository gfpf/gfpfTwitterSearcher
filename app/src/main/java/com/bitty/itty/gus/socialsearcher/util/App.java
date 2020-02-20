package com.bitty.itty.gus.socialsearcher.util;

/**
 * Created by Gus on 24/8/17.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.bitty.itty.gus.socialsearcher.R;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.StandardExceptionParser;
import com.google.android.gms.analytics.Tracker;

import androidx.core.content.ContextCompat;

/**
 * Created by Gus on 16/8/17.
 */

public class App extends Application {

    public static String LOG_TAG;
    private static App app;

    @Override
    public void onCreate() {
        super.onCreate();

        if (app == null) {
            app = this;
            LOG_TAG = getPackageName();
            getTracker();
        }
    }

    public static App get() {
        return app;
    }

    public int[] getSwipeRefreshColorScheme(Activity act) {
        return new int[]{
                ContextCompat.getColor(act, android.R.color.holo_orange_dark),
                ContextCompat.getColor(act, android.R.color.holo_green_dark),
                ContextCompat.getColor(act, android.R.color.holo_red_dark),
                ContextCompat.getColor(act, android.R.color.holo_blue_dark)
        };
    }

    public boolean hasInternetService() {
        try {
            ConnectivityManager conn = (ConnectivityManager) app.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = conn.getActiveNetworkInfo();
            return info != null && info.isConnected();
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
            return false;
        }
    }

    public void suggestEnableInternet() {
        AlertDialog.Builder builder = new AlertDialog.Builder(app);
        builder.setCancelable(false);
        builder.setMessage(app.getString(R.string.enable_internet_service));
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                app.startActivity(new Intent(Settings.ACTION_SETTINGS));
            }
        });

        builder.setNegativeButton(app.getString(android.R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        builder.create().show();
    }

    public static Tracker tracker;

    public static Tracker getTracker() {
        if (tracker == null) {
            tracker = getAnalytics().newTracker(R.xml.global_tracker);
            tracker.enableAdvertisingIdCollection(true);
        }
        return tracker;
    }

    public static GoogleAnalytics analytics;

    public static GoogleAnalytics getAnalytics() {
        if (analytics == null) {
            analytics = GoogleAnalytics.getInstance(App.get());
            analytics.enableAutoActivityReports(App.get());
        }
        return analytics;
    }

    public static void logException(Context context, Exception exception) {
        try {
            Log.e(App.LOG_TAG, exception.getMessage());
            getTracker().send(new HitBuilders.ExceptionBuilder()
                    .setDescription(new StandardExceptionParser(context, null)
                            .getDescription(Thread.currentThread().getName(), exception))
                    .setFatal(true)
                    .build());
        } catch (Exception ex) {
            Log.e(App.LOG_TAG, "Error in LogException: " + ex.getMessage());
        }
    }

    public static void showToast(final Activity ctx, final String msg) {
        ctx.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void hideKeyboard(Activity act) {
        InputMethodManager imm = (InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(act.getWindow().getDecorView().getWindowToken(), 0);
    }

}
