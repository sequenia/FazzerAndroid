package com.sequenia.fazzer.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sequenia.fazzer.R;
import com.sequenia.fazzer.activities.LoginActivity;
import com.sequenia.fazzer.activities.RegisterActivity;

/**
 * Created by chybakut2004 on 12.02.15.
 */
public class WelcomeFragment extends Fragment {

    public WelcomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);

        view.findViewById(R.id.registerButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // No account, load new account view
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        view.findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Existing Account, load login view
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        return view;
    }
}
