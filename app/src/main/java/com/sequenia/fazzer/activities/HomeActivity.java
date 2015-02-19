package com.sequenia.fazzer.activities;

import android.content.SharedPreferences;

import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.sequenia.fazzer.R;
import com.sequenia.fazzer.adapters.HomeActivityPagerAdapter;
import com.sequenia.fazzer.async_tasks.SendRegistrationIdTask;
import com.sequenia.fazzer.gcm.GcmRegistrationService;
import com.sequenia.fazzer.helpers.ActivityHelper;
import com.sequenia.fazzer.helpers.ApiHelper;
import com.sequenia.fazzer.helpers.FazzerHelper;
import com.sequenia.fazzer.helpers.RealmHelper;

public class HomeActivity extends ActionBarActivity {

    private SharedPreferences mPreferences;
    GcmRegistrationService gcmRegistrationService = null;

    HomeActivityPagerAdapter pagerAdapter;
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initGcm();

        mPreferences = getSharedPreferences(FazzerHelper.CURRENT_USER_PREFERENCES, MODE_PRIVATE);

        RealmHelper.migrate(this);

        initPager();
    }

    private void initGcm() {
        gcmRegistrationService = new GcmRegistrationService(getApplicationContext(), this) {
            @Override
            public void sendRegistrationIdToBackend(String regId) {
                sendRegistrationId(regId);
            }
        };
        gcmRegistrationService.tryRegister();
    }

    private void initPager() {
        pagerAdapter = new HomeActivityPagerAdapter(getSupportFragmentManager());
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.logout:
                logout();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        gcmRegistrationService.checkPlayServices();

        if (mPreferences.contains(FazzerHelper.AUTH_TOKEN)) {
            FazzerHelper.updateCatalogs(this);
        } else {
            if(mPreferences.getBoolean(FazzerHelper.REGISTERED, false) == false) {
                ActivityHelper.showRegisterActivity(this);
                finish();
            } else {
                ActivityHelper.showWelcomeActivity(this);
                finish();
            }
        }
    }

    public void logout() {
        FazzerHelper.logout(this);
        ActivityHelper.showWelcomeActivity(this);
        this.finish();
    }

    private void sendRegistrationId(String regId) {
        new SendRegistrationIdTask(this, regId).execute(ApiHelper.REGISTRATION_ID_URL + "?auth_token=" + FazzerHelper.getAuthToken(this));
    }
}
