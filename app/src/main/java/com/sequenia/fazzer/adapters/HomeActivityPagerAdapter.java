package com.sequenia.fazzer.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.sequenia.fazzer.R;
import com.sequenia.fazzer.activities.HomeActivity;
import com.sequenia.fazzer.fragments.AutoAdvertsFragment;
import com.sequenia.fazzer.fragments.FilterFragment;
import com.sequenia.fazzer.fragments.UserFragment;
import com.sequenia.fazzer.objects.AutoAdvertMinInfo;

import java.util.ArrayList;

/**
 * Created by chybakut2004 on 19.02.15.
 */
public class HomeActivityPagerAdapter extends FragmentPagerAdapter {
    AutoAdvertsFragment autoAdvertsFragment;
    Context context;

    public HomeActivityPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new FilterFragment();
                break;
            case 1:
                autoAdvertsFragment = new AutoAdvertsFragment();
                fragment = autoAdvertsFragment;
                break;
            case 2:
                fragment = new UserFragment();
                break;
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        switch (position) {
            case 0:
                title = "ПОИСК";
                break;
            case 1:
                title = "ОБЪЯВЛЕНИЯ";
                break;
            case 2:
                title = "КАБИНЕТ";
                break;
        }
        return title;
    }

    @Override
    public int getCount() {
        return 2;
    }

    public void loadNewAdverts() {
        if(autoAdvertsFragment != null) {
            autoAdvertsFragment.loadNewAdverts();
        }
    }

    public Button getButton() {
        Button button = new Button(context);

        button.setBackgroundResource(R.drawable.refresh);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity) context).loadNewAdverts();
            }
        });

        return button;
    }
}
