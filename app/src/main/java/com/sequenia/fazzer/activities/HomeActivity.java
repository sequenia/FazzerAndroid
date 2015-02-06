package com.sequenia.fazzer.activities;

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
import com.sequenia.fazzer.async_tasks.SaveFilterTask;
import com.sequenia.fazzer.helpers.ActivityHelper;
import com.sequenia.fazzer.helpers.ObjectsHelper;
import com.sequenia.fazzer.helpers.RealmHelper;
import com.sequenia.fazzer.requests_data.AutoAdvertMinInfo;
import com.sequenia.fazzer.async_tasks.AutoAdvertsUploader;
import com.sequenia.fazzer.requests_data.FilterInfo;
import com.sequenia.fazzer.serializers.FilterInfoSerializer;

import java.util.ArrayList;

import io.realm.Realm;

public class HomeActivity extends ActionBarActivity {

    public static final String CURRENT_USER_PREFERENCES = "CurrentUser";
    public static final String AUTH_TOKEN = "AuthToken";
    private static final String AUTO_ADVERTS_URL = "http://192.168.0.36:3000/api/v1/auto_adverts.json";
    private static final String FILTERS_URL = "http://192.168.0.36:3000/api/v1/auto_filters.json";
    public static final String AUTO_ADVERT_ID = "AutoAdvertId";

    private SharedPreferences mPreferences;
    ArrayList<AutoAdvertMinInfo> autoAdverts = null;
    FilterInfo filterInfo = null;
    AutoAdvertsAdapter adapter = null;
    ListView autoAdvertsListView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mPreferences = getSharedPreferences(CURRENT_USER_PREFERENCES, MODE_PRIVATE);
        autoAdverts = new ArrayList<AutoAdvertMinInfo>();
        adapter = new AutoAdvertsAdapter(this, R.layout.auto_advert_info, autoAdverts);

        RealmHelper.migrate(this);

        initListView();
        initSaveButton();
        initFilter();
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

    private void initSaveButton() {
        Button saveFilterButton = (Button) findViewById(R.id.save_filter_button);
        saveFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFilter();
            }
        });
    }

    private void initFilter() {
        filterInfo = RealmHelper.findOrCreateFilter(this);
        writeFilterToForm();
    }

    private void loadAutoAdvertsFromAPI() {
        new AutoAdvertsUploader(this).execute(AUTO_ADVERTS_URL + "?auth_token=" + mPreferences.getString("AuthToken", ""));
    }

    private void saveFilter() {
        readFilterInfoFromForm();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(FilterInfo.class, new FilterInfoSerializer())
                .create();
        String json = gson.toJson(filterInfo, FilterInfo.class);

        new SaveFilterTask(this, json).execute(FILTERS_URL + "?auth_token=" + mPreferences.getString("AuthToken", ""));
    }

    private void showAdvert(int position) {
        Intent intent = new Intent(HomeActivity.this,
                AutoAdvertActivity.class);
        intent.putExtra(AUTO_ADVERT_ID, autoAdverts.get(position).getId());
        startActivityForResult(intent, 0);
    }

    public void addAdverts(ArrayList<AutoAdvertMinInfo> autoAdverts) {
        this.autoAdverts.clear();
        this.autoAdverts.addAll(0, autoAdverts);
        adapter.notifyDataSetChanged();
        ActivityHelper.setListViewHeightBasedOnChildren(autoAdvertsListView);
    }

    public void readFilterInfoFromForm() {
        Realm realm = Realm.getInstance(this);
        realm.beginTransaction();

        filterInfo.setCarMarkId(ObjectsHelper.strToIntNoZero(ActivityHelper.getText(this, R.id.mark)));
        filterInfo.setCarModelId(ObjectsHelper.strToIntNoZero(ActivityHelper.getText(this, R.id.model)));
        filterInfo.setMinPrice(ObjectsHelper.strToFloatNoZero(ActivityHelper.getText(this, R.id.min_price)));
        filterInfo.setMaxPrice(ObjectsHelper.strToFloatNoZero(ActivityHelper.getText(this, R.id.max_price)));
        filterInfo.setMinYear(ObjectsHelper.strToIntNoZero(ActivityHelper.getText(this, R.id.min_year)));
        filterInfo.setMaxYear(ObjectsHelper.strToIntNoZero(ActivityHelper.getText(this, R.id.max_year)));

        realm.commitTransaction();
    }

    public void writeFilterToForm() {
        if(filterInfo != null) {
            ActivityHelper.setText(this, R.id.mark, ObjectsHelper.intToStrNoZero(filterInfo.getCarMarkId()));
            ActivityHelper.setText(this, R.id.model, ObjectsHelper.intToStrNoZero(filterInfo.getCarModelId()));
            ActivityHelper.setText(this, R.id.min_price, ObjectsHelper.floatToStrNoZero(filterInfo.getMinPrice()));
            ActivityHelper.setText(this, R.id.max_price, ObjectsHelper.floatToStrNoZero(filterInfo.getMaxPrice()));
            ActivityHelper.setText(this, R.id.min_year, ObjectsHelper.intToStrNoZero(filterInfo.getMinYear()));
            ActivityHelper.setText(this, R.id.max_year, ObjectsHelper.intToStrNoZero(filterInfo.getMaxYear()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

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

        if (mPreferences.contains(AUTH_TOKEN)) {
            loadAutoAdvertsFromAPI();
        } else {
            ActivityHelper.showWelcomeActivity(HomeActivity.this, this);
        }
    }

    public void logout() {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.remove(HomeActivity.AUTH_TOKEN);
        editor.commit();

        ActivityHelper.showWelcomeActivity(HomeActivity.this, this);
    }
}
