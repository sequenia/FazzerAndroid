package com.sequenia.fazzer.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sequenia.fazzer.R;
import com.sequenia.fazzer.async_tasks.LoginTask;
import com.sequenia.fazzer.fragments.LoginFragment;
import com.sequenia.fazzer.helpers.ApiHelper;
import com.sequenia.fazzer.helpers.FazzerHelper;


public class LoginActivity extends FragmentActivity {

    public LoginActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sessions);

        if (savedInstanceState == null) {
            LoginFragment loginFragment = new LoginFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.session_content, loginFragment)
                    .commit();
        }
    }

}
