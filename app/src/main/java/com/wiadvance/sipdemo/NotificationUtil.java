package com.wiadvance.sipdemo;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class NotificationUtil {

    private static final String TAG = "NotificationUtil";

    public static String ACTION_NOTIFICATION = "com.wiadvance.sipdemo.notification";
    public static final String NOTIFY_MESSAGE = "notify_message";

    public static void displayStatus(Context context, String s) {
        Log.d(TAG, "displayStatus: " + s);
        Intent intent = new Intent(ACTION_NOTIFICATION);
        intent.putExtra(NOTIFY_MESSAGE, s);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
