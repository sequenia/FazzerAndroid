package com.sequenia.fazzer.fragments;

import android.app.Activity;

import com.sequenia.fazzer.helpers.ActivityHelper;
import com.sequenia.fazzer.requests_data.Response;

/**
 * Created by chybakut2004 on 02.03.15.
 */
public class FirstFilterFragment extends FilterFragment {
    @Override
    public void onFilterSave(Response<String> response) {
        Activity activity = getActivity();

        ActivityHelper.showHomeActivity(activity);
        activity.finish();
    }
}
