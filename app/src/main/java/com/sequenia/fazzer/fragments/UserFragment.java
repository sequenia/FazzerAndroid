package com.sequenia.fazzer.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sequenia.fazzer.R;
import com.sequenia.fazzer.helpers.ActivityHelper;
import com.sequenia.fazzer.helpers.FazzerHelper;

/**
 * Created by chybakut2004 on 19.02.15.
 */
public class UserFragment extends Fragment {

    public UserFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        return view;
    }

    public void logout() {
        Activity activity = getActivity();
        FazzerHelper.logout(activity);
        ActivityHelper.showWelcomeActivity(activity);
        activity.finish();
    }
}
