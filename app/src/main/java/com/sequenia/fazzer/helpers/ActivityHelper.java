package com.sequenia.fazzer.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sequenia.fazzer.activities.AutoAdvertActivity;
import com.sequenia.fazzer.activities.FirstFilterActivity;
import com.sequenia.fazzer.activities.HomeActivity;
import com.sequenia.fazzer.activities.LoginActivity;
import com.sequenia.fazzer.activities.RegisterActivity;
import com.sequenia.fazzer.activities.WelcomeActivity;

/**
 * Created by chybakut2004 on 06.02.15.
 */
public class ActivityHelper {

    public static TextView setText(Activity activity, int resourceId, String text) {
        TextView tv = (TextView) activity.findViewById(resourceId);

        if(text != null) {
            tv.setText(text);
        }

        return tv;
    }

    public static String getText(Activity activity, int resourceId) {
        String result = null;
        EditText et = (EditText) activity.findViewById(resourceId);
        if(et != null) {
            result = et.getText().toString();
        }
        return result;
    }

    public static void openBrowser(Activity activity, String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        activity.startActivity(browserIntent);
    }

    public static void showWelcomeActivity(Context packageContext) {
        Intent intent = new Intent(packageContext, WelcomeActivity.class);
        ((Activity) packageContext).startActivityForResult(intent, 0);
    }

    public static void showRegisterActivity(Context packageContext) {
        Intent intent = new Intent(packageContext, RegisterActivity.class);
        packageContext.startActivity(intent);
    }

    public static void showLoginActivity(Context packageContext, String phone) {
        Intent intent = new Intent(packageContext, LoginActivity.class);
        if(phone != null) {
            intent.putExtra("phone", phone);
        }
        packageContext.startActivity(intent);
    }

    public static void showHomeActivity(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    public static void showFirstFilterActivity(Context context) {
        Intent intent = new Intent(context, FirstFilterActivity.class);
        context.startActivity(intent);
    }

    public static void showAutoAdvertActivity(Context context, int advertId) {
        Intent intent = new Intent(context, AutoAdvertActivity.class);
        intent.putExtra(FazzerHelper.AUTO_ADVERT_ID, advertId);
        ((Activity)context).startActivityForResult(intent, 0);
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.widthPixels;
    }

    public static void hideKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(((Activity)context).getWindow().getDecorView().findViewById(android.R.id.content).getWindowToken(), 0);
    }
}
