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
import com.sequenia.fazzer.async_tasks.LoginTask;
import com.sequenia.fazzer.helpers.ApiHelper;
import com.sequenia.fazzer.helpers.FazzerHelper;

/**
 * Created by chybakut2004 on 12.02.15.
 */
public class LoginFragment extends Fragment {
    private static final String EMPTY_PHONE_ERROR = "Пожалуйста, введите телефон";
    private static final String EMPTY_PASSWORD_ERROR = "Пожалуйста, введите пароль";

    private String mUserPhone;
    private String mUserPassword;

    public LoginFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        String phone = getActivity().getIntent().getStringExtra("phone");
        if (phone != null) {
            EditText phoneEditText = (EditText) view.findViewById(R.id.userPhone);
            phoneEditText.setText(phone);
        }

        Button loginButton = (Button) view.findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(v);
            }
        });

        return view;
    }

    public void login(View v) {
        Activity activity = getActivity();
        SharedPreferences mPreferences = FazzerHelper.getUserPreferences(activity);

        EditText userPhoneField = (EditText) getView().findViewById(R.id.userPhone);
        mUserPhone = userPhoneField.getText().toString();
        EditText userPasswordField = (EditText) getView().findViewById(R.id.userPassword);
        mUserPassword = userPasswordField.getText().toString();

        if (mUserPhone.length() == 0 || mUserPassword.length() == 0) {
            String error = "";
            if(mUserPhone.length() == 0) {
                error += EMPTY_PHONE_ERROR;
            }
            if(mUserPassword.length() == 0) {
                error += "\n" + EMPTY_PASSWORD_ERROR;
            }
            Toast.makeText(activity, error, Toast.LENGTH_LONG).show();
            return;
        } else {
            LoginTask loginTask = new LoginTask(mUserPhone, mUserPassword, mPreferences, activity);
            loginTask.execute(ApiHelper.LOGIN_API_ENDPOINT_URL);
        }
    }
}
