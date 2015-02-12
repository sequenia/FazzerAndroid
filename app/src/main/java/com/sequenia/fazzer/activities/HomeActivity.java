package com.sequenia.fazzer.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sequenia.fazzer.R;
import com.sequenia.fazzer.adapters.AutoAdvertsAdapter;
import com.sequenia.fazzer.async_tasks.UpdateCatalogsTask;
import com.sequenia.fazzer.async_tasks.AutoAdvertsLoader;
import com.sequenia.fazzer.async_tasks.SaveFilterTask;
import com.sequenia.fazzer.fragments.FilterFragment;
import com.sequenia.fazzer.fragments.LoginFragment;
import com.sequenia.fazzer.helpers.ActivityHelper;
import com.sequenia.fazzer.helpers.ApiHelper;
import com.sequenia.fazzer.helpers.FazzerHelper;
import com.sequenia.fazzer.helpers.ObjectsHelper;
import com.sequenia.fazzer.helpers.RealmHelper;
import com.sequenia.fazzer.requests_data.AutoAdvertMinInfo;
import com.sequenia.fazzer.requests_data.FilterInfo;
import com.sequenia.fazzer.serializers.FilterInfoSerializer;

import java.util.ArrayList;

import io.realm.Realm;

public class HomeActivity extends ActionBarActivity {

    private SharedPreferences mPreferences;
    ArrayList<AutoAdvertMinInfo> autoAdverts = null;
    AutoAdvertsAdapter adapter = null;
    ListView autoAdvertsListView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mPreferences = getSharedPreferences(FazzerHelper.CURRENT_USER_PREFERENCES, MODE_PRIVATE);
        autoAdverts = new ArrayList<AutoAdvertMinInfo>();
        adapter = new AutoAdvertsAdapter(this, R.layout.auto_advert_info, autoAdverts);

        RealmHelper.migrate(this);

        if (savedInstanceState == null) {
            FilterFragment filterFragment = new FilterFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.filter_container, filterFragment)
                    .commit();
        }

        initListView();
    }

    private void initListView() {
        autoAdvertsListView = (ListView) findViewById (R.id.auto_adverts_list_view);
        autoAdvertsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showAdvert(position);
            }
        });
        autoAdvertsListView.setAdapter(adapter);
    }

    private void loadAutoAdvertsFromAPI() {
        new AutoAdvertsLoader(this).execute(ApiHelper.AUTO_ADVERTS_URL + "?auth_token=" + mPreferences.getString("AuthToken", ""));
    }

    private void showAdvert(int position) {
        Intent intent = new Intent(HomeActivity.this,
                AutoAdvertActivity.class);
        intent.putExtra(FazzerHelper.AUTO_ADVERT_ID, autoAdverts.get(position).getId());
        startActivityForResult(intent, 0);
    }

    public void addAdverts(ArrayList<AutoAdvertMinInfo> autoAdverts) {
        this.autoAdverts.clear();
        this.autoAdverts.addAll(0, autoAdverts);
        adapter.notifyDataSetChanged();
        ActivityHelper.setListViewHeightBasedOnChildren(autoAdvertsListView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.refresh:
                loadAutoAdvertsFromAPI();
                return true;

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

        if (mPreferences.contains(FazzerHelper.AUTH_TOKEN)) {
            if(ActivityHelper.isNetworkAvailable(this)) {
                new UpdateCatalogsTask(this).execute();
            }

            loadAutoAdvertsFromAPI();
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
}
