package com.sequenia.fazzer.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sequenia.fazzer.R;
import com.sequenia.fazzer.async_tasks.AutoAdvertLoader;
import com.sequenia.fazzer.helpers.ActivityHelper;
import com.sequenia.fazzer.helpers.ApiHelper;
import com.sequenia.fazzer.helpers.FazzerHelper;
import com.sequenia.fazzer.helpers.ObjectsHelper;
import com.sequenia.fazzer.helpers.RealmHelper;
import com.sequenia.fazzer.objects.AutoAdvertFullInfo;
import com.sequenia.fazzer.objects.AutoAdvertMinInfo;
import com.sequenia.fazzer.requests_data.Response;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class AutoAdvertActivity extends ActionBarActivity {

    private int autoAdvertId;
    private SharedPreferences mPreferences;
    private AutoAdvertFullInfo autoAdvert = null;
    View progressBar = null;
    View imageProgressBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_advert);
        hideContent();

        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        imageProgressBar = findViewById(R.id.image_progress_bar);
        imageProgressBar.setVisibility(View.GONE);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

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
        final Activity activity = this;

        progressBar.setVisibility(View.VISIBLE);
        hideContent();

        new AutoAdvertLoader(this, id) {
            @Override
            public void onPostExecuteCustom(Response<AutoAdvertFullInfo> response) {
                progressBar.setVisibility(View.GONE);
                if(response != null) {
                    if(response.getSuccess()) {
                        AutoAdvertFullInfo info = response.getData();
                        RealmHelper.saveAutoAdvertFullInfo(activity, info);
                        setAdvertInfo(info);
                        showContent();
                    } else {
                        Toast.makeText(activity, response.getInfo(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }.execute();
    }

    public void setAdvertInfo(AutoAdvertFullInfo autoAdvert) {
        this.autoAdvert = autoAdvert;

        int imageWidth = ActivityHelper.getScreenWidth(this);
        int imageHeight = (int)((float) imageWidth * (0.75));

        ImageView photo = (ImageView) findViewById(R.id.photo);
        String url = autoAdvert.getPhoto_url();
        if(ObjectsHelper.isEmpty(url)) {
            Picasso.with(this)
                    .load(R.drawable.no_photo)
                    .resize(imageWidth, imageHeight)
                    .centerCrop()
                    .into(photo);
        } else {
            imageProgressBar.setVisibility(View.VISIBLE);
            Picasso.with(this)
                    .load(url)
                    .resize(imageWidth, imageHeight)
                    .placeholder(R.drawable.loading_photo)
                    .centerCrop()
                    .into(photo, new Callback() {
                        @Override
                        public void onSuccess() {
                            imageProgressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {

                        }
                    });
        }

        setText(R.id.mark_and_model, autoAdvert.getCar_mark_name() + " " + autoAdvert.getCar_model_name());
        setText(R.id.year, String.valueOf(autoAdvert.getYear()));
        setText(R.id.price, ObjectsHelper.prettifyNumber(String.valueOf(autoAdvert.getPrice()), " р."));
        setText(R.id.fuel, ObjectsHelper.prettifyFuel(autoAdvert.getFuel()));
        setText(R.id.displacement, ObjectsHelper.prettifyDisplacement(autoAdvert.getDisplacement()));
        setText(R.id.transmission, ObjectsHelper.prettifyTransmission(autoAdvert.getTransmission()));
        setText(R.id.drive, ObjectsHelper.prettifyDrive(autoAdvert.getDrive()));
        setText(R.id.mileage, ObjectsHelper.prettifyNumber(autoAdvert.getMileage(), " км"));
        setText(R.id.body, ObjectsHelper.prettifyBody(autoAdvert.getBody()));
        setText(R.id.wheel, ObjectsHelper.prettifyWheel(autoAdvert.getSteering_wheel()));
        setText(R.id.color, autoAdvert.getColor());
        setText(R.id.city, autoAdvert.getCity_name());
        setText(R.id.description, autoAdvert.getDescription());
        setText(R.id.exchange, autoAdvert.getExchange());

        if(autoAdvert.getUrl() == null) {
            ((Button) findViewById(R.id.show_in_browser)).setVisibility(View.GONE);
        }
    }

    private void showContent() {
        findViewById(R.id.auto_advert_full_info).setVisibility(View.VISIBLE);
    }

    private void hideContent() {
        findViewById(R.id.auto_advert_full_info).setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mPreferences.contains(FazzerHelper.AUTH_TOKEN)) {
            AutoAdvertFullInfo autoAdvertFullInfo = RealmHelper.getAllAutoAdvertFullInfoById(this, autoAdvertId);
            if(autoAdvertFullInfo == null) {
                loadAutoAdvertFromAPI(autoAdvertId);
            } else {
                setAdvertInfo(autoAdvertFullInfo);
                showContent();
            }
        } else {
            ActivityHelper.showWelcomeActivity(this);
        }
    }

    private void setText(int resourceId, String text) {
        TextView tv = ActivityHelper.setText(this, resourceId, text);
        if(tv != null && text == null) {
            ((View) (tv.getParent())).setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}