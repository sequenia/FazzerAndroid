package com.sequenia.fazzer.activities;

import android.content.SharedPreferences;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.MenuItem;

import com.sequenia.fazzer.R;
import com.sequenia.fazzer.adapters.HomeActivityPagerAdapter;
import com.sequenia.fazzer.async_tasks.SendRegistrationIdTask;
import com.sequenia.fazzer.fragments.AutoAdvertsFragment;
import com.sequenia.fazzer.gcm.GcmRegistrationService;
import com.sequenia.fazzer.helpers.ActivityHelper;
import com.sequenia.fazzer.helpers.ApiHelper;
import com.sequenia.fazzer.helpers.FazzerHelper;
import com.sequenia.fazzer.helpers.RealmHelper;
import com.sequenia.fazzer.requests_data.AutoAdvertMinInfo;
import com.sequenia.fazzer.widgets.SlidingTabLayout;

import java.util.ArrayList;

public class HomeActivity extends FragmentActivity {

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
        } else {
            ActivityHelper.showWelcomeActivity(this);
            finish();
        }
    }

    private void sendRegistrationId(String regId) {
        new SendRegistrationIdTask(this, regId).execute(ApiHelper.REGISTRATION_ID_URL + "?auth_token=" + FazzerHelper.getAuthToken(this));
    }

    public void showNewAdverts(ArrayList<AutoAdvertMinInfo> newAdverts) {
        pagerAdapter.showNewAdverts(newAdverts);
    }
}
