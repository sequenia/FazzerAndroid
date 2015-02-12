package com.sequenia.fazzer.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;

import com.sequenia.fazzer.R;
import com.sequenia.fazzer.fragments.LoginFragment;
import com.sequenia.fazzer.fragments.WelcomeFragment;
import com.sequenia.fazzer.helpers.FazzerHelper;


public class WelcomeActivity extends FragmentActivity {

    public WelcomeActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sessions);

        if (savedInstanceState == null) {
            WelcomeFragment welcomeFragment = new WelcomeFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.session_content, welcomeFragment)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences mPreferences = FazzerHelper.getUserPreferences(this);

        if (mPreferences.contains(FazzerHelper.AUTH_TOKEN)) {
            finish();
        }
    }
}
