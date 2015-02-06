package com.sequenia.fazzer.activities;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by chybakut2004 on 06.02.15.
 */
public class ActivityHelper {
    public static void setText(Activity activity, int resourceId, String text) {
        TextView tv = (TextView) activity.findViewById(resourceId);

        if(text != null) {
            tv.setText(text);
        } else {
            ((View)(tv.getParent())).setVisibility(View.GONE);
        }
    }

    public static String getText(Activity activity, int resourceId) {
        String result = null;
        EditText et = (EditText) activity.findViewById(resourceId);
        if(et != null) {
            result = et.getText().toString();
        }
        return result;
    }
}
