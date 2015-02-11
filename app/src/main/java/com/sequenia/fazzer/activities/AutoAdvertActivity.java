package com.sequenia.fazzer.activities;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sequenia.fazzer.R;
import com.sequenia.fazzer.async_tasks.AutoAdvertLoader;
import com.sequenia.fazzer.helpers.ActivityHelper;
import com.sequenia.fazzer.helpers.ApiHelper;
import com.sequenia.fazzer.helpers.FazzerHelper;
import com.sequenia.fazzer.requests_data.AutoAdvertFullInfo;


public class AutoAdvertActivity extends ActionBarActivity {

    private int autoAdvertId;
    private SharedPreferences mPreferences;
    private AutoAdvertFullInfo autoAdvert = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_advert);
        hideContent();

        mPreferences = getSharedPreferences(FazzerHelper.CURRENT_USER_PREFERENCES, MODE_PRIVATE);
        autoAdvertId = getIntent().getIntExtra(FazzerHelper.AUTO_ADVERT_ID, 0);

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
        new AutoAdvertLoader(this).execute(ApiHelper.AUTO_ADVERT_URL + String.valueOf(autoAdvertId) + ".json" + "?auth_token=" + mPreferences.getString("AuthToken", ""));
    }

    public void setAdvertInfo(AutoAdvertFullInfo autoAdvert) {
        this.autoAdvert = autoAdvert;

        setText(R.id.mark, autoAdvert.getCarMarkName());
        setText(R.id.model, autoAdvert.getCarModelName());
        setText(R.id.year, String.valueOf(autoAdvert.getYear()));
        setText(R.id.price, String.valueOf(autoAdvert.getPrice()));
        setText(R.id.fuel, autoAdvert.getFuel());
        setText(R.id.displacement, autoAdvert.getDisplacement());
        setText(R.id.transmission, autoAdvert.getTransmission());
        setText(R.id.drive, autoAdvert.getDrive());
        setText(R.id.mileage, autoAdvert.getMileage());
        setText(R.id.body, autoAdvert.getBody());
        setText(R.id.wheel, autoAdvert.getSteeringWheel());
        setText(R.id.color, autoAdvert.getColor());
        setText(R.id.city, autoAdvert.getCityName());
        setText(R.id.description, autoAdvert.getDescription());
        setText(R.id.exchange, autoAdvert.getExchange());

        showContent();
    }

    private void showContent() {
        findViewById(R.id.container).setVisibility(View.VISIBLE);
    }

    private void hideContent() {
        findViewById(R.id.container).setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mPreferences.contains(FazzerHelper.AUTH_TOKEN)) {
            loadAutoAdvertFromAPI(autoAdvertId);
        } else {
            ActivityHelper.showWelcomeActivity(AutoAdvertActivity.this, this);
        }
    }

    private void setText(int resourceId, String text) {
        TextView tv = ActivityHelper.setText(this, resourceId, text);
        if(tv != null && text == null) {
            ((View) (tv.getParent())).setVisibility(View.GONE);
        }
    }
}