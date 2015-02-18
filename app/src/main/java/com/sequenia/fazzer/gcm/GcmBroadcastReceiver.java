package com.sequenia.fazzer.gcm;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

/**
 * Created by chybakut2004 on 18.02.15.
 */
public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        /*Intent service = new Intent(context, GcmIntentService.class);

        Log.i("GcmBroadcastReceiver", "Starting service @ " + SystemClock.elapsedRealtime());
        startWakefulService(context, service);*/

        ComponentName comp = new ComponentName(context.getPackageName(),
                GcmIntentService.class.getName());
        // Start the service, keeping the device awake while it is launching.
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
    }
}
