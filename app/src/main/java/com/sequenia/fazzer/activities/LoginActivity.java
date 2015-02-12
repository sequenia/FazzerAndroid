package com.sequenia.fazzer.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sequenia.fazzer.R;
import com.sequenia.fazzer.async_tasks.LoginTask;
import com.sequenia.fazzer.helpers.ApiHelper;
import com.sequenia.fazzer.helpers.FazzerHelper;


public class LoginActivity extends Activity {

    private SharedPreferences mPreferences;
    private String mUserPhone;
    private String mUserPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mPreferences = getSharedPreferences(FazzerHelper.CURRENT_USER_PREFERENCES, MODE_PRIVATE);

        Button loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(v);
            }
        });
    }

    public void login(View v) {
        EditText userPhoneField = (EditText) findViewById(R.id.userPhone);
        mUserPhone = userPhoneField.getText().toString();
        EditText userPasswordField = (EditText) findViewById(R.id.userPassword);
        mUserPassword = userPasswordField.getText().toString();

        if (mUserPhone.length() == 0 || mUserPassword.length() == 0) {
            Toast.makeText(this, "Пожалуйста, заполните поля",
                    Toast.LENGTH_LONG).show();
            return;
        } else {
            LoginTask loginTask = new LoginTask(mUserPhone, mUserPassword, mPreferences, this);
            loginTask.execute(ApiHelper.LOGIN_API_ENDPOINT_URL);
        }
    }

}
