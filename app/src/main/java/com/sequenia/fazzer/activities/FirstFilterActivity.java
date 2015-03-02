package com.sequenia.fazzer.activities;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.sequenia.fazzer.R;
import com.sequenia.fazzer.async_tasks.UpdateCatalogsTask;
import com.sequenia.fazzer.fragments.FilterFragment;
import com.sequenia.fazzer.fragments.FirstFilterFragment;
import com.sequenia.fazzer.helpers.ActivityHelper;
import com.sequenia.fazzer.helpers.FazzerHelper;

public class FirstFilterActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_filter);

        if (savedInstanceState == null) {
            FilterFragment filterFragment = new FirstFilterFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.filter_container, filterFragment)
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (FazzerHelper.getUserPreferences(this).contains(FazzerHelper.AUTH_TOKEN)) {
            FazzerHelper.updateCatalogs(this);
        }
    }
}
