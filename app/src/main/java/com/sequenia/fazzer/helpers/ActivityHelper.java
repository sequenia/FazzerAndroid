package com.sequenia.fazzer.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sequenia.fazzer.R;
import com.sequenia.fazzer.activities.FirstFilterActivity;
import com.sequenia.fazzer.activities.HomeActivity;
import com.sequenia.fazzer.activities.RegisterActivity;
import com.sequenia.fazzer.activities.WelcomeActivity;
import com.sequenia.fazzer.dialogs.SelectDialogFragment;
import com.sequenia.fazzer.objects.Option;

import java.util.ArrayList;

/**
 * Created by chybakut2004 on 06.02.15.
 */
public class ActivityHelper {
    private static final String DIALOG_TAG = "Dialog";

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

    public static String getTextTextView(Activity activity, int resourceId) {
        String result = null;
        TextView et = (TextView) activity.findViewById(resourceId);
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

    public static void showHomeActivity(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    public static void showFirstFilterActivity(Context context) {
        Intent intent = new Intent(context, FirstFilterActivity.class);
        context.startActivity(intent);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, LinearLayout.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
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

    public static void setupSelectDialog(View view, final FragmentActivity activity, final String title, final ArrayList<Option> options) {
        view.setFocusable(false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectDialogFragment dialog = SelectDialogFragment.newInstance(title, options);
                dialog.show(activity.getSupportFragmentManager(), DIALOG_TAG);
            }
        });
    }
}
