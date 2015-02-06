package com.sequenia.fazzer.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sequenia.fazzer.R;
import com.sequenia.fazzer.async_tasks.AutoAdvertUploader;
import com.sequenia.fazzer.requests_data.AutoAdvertFullInfo;


public class AutoAdvertActivity extends ActionBarActivity {

    private static final String AUTO_ADVERT_URL = "http://192.168.0.36:3000/api/v1/auto_adverts/";

    private int autoAdvertId;
    private SharedPreferences mPreferences;
    private AutoAdvertFullInfo autoAdvert = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_advert);

        mPreferences = getSharedPreferences(HomeActivity.CURRENT_USER_PREFERENCES, MODE_PRIVATE);

        autoAdvertId = getIntent().getIntExtra(HomeActivity.AUTO_ADVERT_ID, 0);

        Button showInBrowser = (Button) findViewById(R.id.show_in_browser);
        showInBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAdvertInBrowser();
            }
        });
    }

    private void showAdvertInBrowser() {
        if(autoAdvert != null) {
            String url = autoAdvert.getUrl();
            if(url != null) {
                ActivityHelper.openBrowser(this, autoAdvert.getUrl());
            }
        }
    }

    private void loadAutoAdvertFromAPI(int id) {
        new AutoAdvertUploader(this).execute(AUTO_ADVERT_URL + String.valueOf(autoAdvertId) + ".json" + "?auth_token=" + mPreferences.getString("AuthToken", ""));
    }

    public void showWelcomeActivity() {
        Intent intent = new Intent(AutoAdvertActivity.this, WelcomeActivity.class);
        startActivityForResult(intent, 0);
    }

    public void setAdvertInfo(AutoAdvertFullInfo autoAdvert) {
        this.autoAdvert = autoAdvert;

        ActivityHelper.setText(this, R.id.mark, autoAdvert.getCarMarkName());
        ActivityHelper.setText(this, R.id.model, autoAdvert.getCarModelName());
        ActivityHelper.setText(this, R.id.year, String.valueOf(autoAdvert.getYear()));
        ActivityHelper.setText(this, R.id.price, String.valueOf(autoAdvert.getPrice()));
        ActivityHelper.setText(this, R.id.fuel, autoAdvert.getFuel());
        ActivityHelper.setText(this, R.id.displacement, autoAdvert.getDisplacement());
        ActivityHelper.setText(this, R.id.transmission, autoAdvert.getTransmission());
        ActivityHelper.setText(this, R.id.drive, autoAdvert.getDrive());
        ActivityHelper.setText(this, R.id.mileage, autoAdvert.getMileage());
        ActivityHelper.setText(this, R.id.body, autoAdvert.getBody());
        ActivityHelper.setText(this, R.id.wheel, autoAdvert.getSteeringWheel());
        ActivityHelper.setText(this, R.id.color, autoAdvert.getColor());
        ActivityHelper.setText(this, R.id.city, autoAdvert.getCityName());
        ActivityHelper.setText(this, R.id.description, autoAdvert.getDescription());

        findViewById(R.id.container).setVisibility(View.VISIBLE);
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