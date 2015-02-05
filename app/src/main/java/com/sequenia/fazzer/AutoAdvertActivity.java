package com.sequenia.fazzer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import com.sequenia.fazzer.async_tasks.AutoAdvertUploader;


public class AutoAdvertActivity extends ActionBarActivity {

    private static final String AUTO_ADVERT_URL = "http://192.168.0.36:3000/api/v1/auto_adverts/";

    private int autoAdvertId;
    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_advert);

        mPreferences = getSharedPreferences(HomeActivity.CURRENT_USER_PREFERENCES, MODE_PRIVATE);

        autoAdvertId = getIntent().getIntExtra(HomeActivity.AUTO_ADVERT_ID, 0);
    }

    private void loadAutoAdvertFromAPI(int id) {
        new AutoAdvertUploader(this).execute(AUTO_ADVERT_URL + String.valueOf(autoAdvertId) + ".json" + "?auth_token=" + mPreferences.getString("AuthToken", ""));
    }

    public void showWelcomeActivity() {
        Intent intent = new Intent(AutoAdvertActivity.this, WelcomeActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mPreferences.contains(HomeActivity.AUTH_TOKEN)) {
            loadAutoAdvertFromAPI(autoAdvertId);
        } else {
            showWelcomeActivity();
        }
    }
}