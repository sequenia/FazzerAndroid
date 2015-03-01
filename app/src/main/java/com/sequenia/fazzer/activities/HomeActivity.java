package com.sequenia.fazzer.activities;

import android.content.Intent;
import android.content.SharedPreferences;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.sequenia.fazzer.R;
import com.sequenia.fazzer.adapters.HomeActivityPagerAdapter;
import com.sequenia.fazzer.async_tasks.SendRegistrationIdTask;
import com.sequenia.fazzer.gcm.GcmRegistrationService;
import com.sequenia.fazzer.helpers.ActivityHelper;
import com.sequenia.fazzer.helpers.ApiHelper;
import com.sequenia.fazzer.helpers.FazzerHelper;
import com.sequenia.fazzer.helpers.RealmHelper;
import com.sequenia.fazzer.objects.AutoAdvertMinInfo;
import com.sequenia.fazzer.widgets.SlidingTabLayout;

import java.util.ArrayList;

public class HomeActivity extends FragmentActivity {

    public final static String NEEDS_UPDATE_PREF = "NeedsUpdate";

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
        pagerAdapter = new HomeActivityPagerAdapter(getSupportFragmentManager(), this);
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);
        pager.setCurrentItem(1);

        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        slidingTabLayout.setViewPager(pager);
        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.white);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        gcmRegistrationService.checkPlayServices();

        if (mPreferences.contains(FazzerHelper.AUTH_TOKEN)) {
            FazzerHelper.updateCatalogs(this);
            Intent intent = getIntent();
            int advertId = intent.getIntExtra(FazzerHelper.AUTO_ADVERT_ID, 0);
            if(advertId != 0) {
                intent.removeExtra(FazzerHelper.AUTO_ADVERT_ID);
                SharedPreferences.Editor editor = mPreferences.edit();
                editor.putBoolean(NEEDS_UPDATE_PREF, true);
                editor.commit();
                ActivityHelper.showAutoAdvertActivity(this, advertId);
            }
        } else {
            if(mPreferences.getBoolean(FazzerHelper.REGISTERED, false)) {
                ActivityHelper.showLoginActivity(this, FazzerHelper.getUserPhone(this));
            } else {
                ActivityHelper.showRegisterActivity(this);
            }
            finish();
        }
    }

    private void sendRegistrationId(String regId) {
        new SendRegistrationIdTask(this, regId).execute(ApiHelper.REGISTRATION_ID_URL + "?auth_token=" + FazzerHelper.getAuthToken(this));
    }

    public void loadNewAdverts() {
        pagerAdapter.loadNewAdverts();
    }
}
