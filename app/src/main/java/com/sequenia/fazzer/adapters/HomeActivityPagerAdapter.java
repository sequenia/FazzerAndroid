package com.sequenia.fazzer.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

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

    public HomeActivityPagerAdapter(FragmentManager fm) {
        super(fm);
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
        return 3;
    }

    public void loadNewAdverts() {
        if(autoAdvertsFragment != null) {
            autoAdvertsFragment.loadNewAdverts();
        }
    }
}
