package com.sequenia.fazzer.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sequenia.fazzer.R;
import com.sequenia.fazzer.async_tasks.RegisterTask;
import com.sequenia.fazzer.helpers.ActivityHelper;
import com.sequenia.fazzer.helpers.ApiHelper;
import com.sequenia.fazzer.helpers.FazzerHelper;

/**
 * Created by chybakut2004 on 12.02.15.
 */
public class RegisterFragment extends Fragment {
    private static final String EMPTY_PHONE_ERROR = "Пожалуйста, введите телефон";

    private String mUserPhone;

    public RegisterFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        Button registerButton = (Button) view.findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewAccount(v);
            }
        });

        return view;
    }

    public void registerNewAccount(View button) {
        Activity activity = getActivity();
        SharedPreferences mPreferences = FazzerHelper.getUserPreferences(activity);

        EditText userPhoneField = (EditText) getView().findViewById(R.id.userPhone);
        mUserPhone = userPhoneField.getText().toString();

        if (mUserPhone.length() == 0) {
            Toast.makeText(activity, EMPTY_PHONE_ERROR, Toast.LENGTH_LONG).show();
            return;
        } else {
            RegisterTask registerTask = new RegisterTask(mUserPhone, mPreferences, activity);
            registerTask.execute(ApiHelper.REGISTER_API_ENDPOINT_URL);
        }
    }
}
