package com.sequenia.fazzer.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.sequenia.fazzer.R;
import com.sequenia.fazzer.adapters.AutoAdvertsAdapter;
import com.sequenia.fazzer.async_tasks.SaveFilterTask;
import com.sequenia.fazzer.requests_data.AutoAdvertMinInfo;
import com.sequenia.fazzer.async_tasks.AutoAdvertsUploader;
import com.sequenia.fazzer.requests_data.FilterInfo;

import java.util.ArrayList;

public class HomeActivity extends ActionBarActivity {

    public static final String CURRENT_USER_PREFERENCES = "CurrentUser";
    public static final String AUTH_TOKEN = "AuthToken";
    private static final String AUTO_ADVERTS_URL = "http://192.168.0.36:3000/api/v1/auto_adverts.json";
    private static final String FILTERS_URL = "http://192.168.0.36:3000/api/v1/auto_filters.json";
    public static final String AUTO_ADVERT_ID = "AutoAdvertId";

    private SharedPreferences mPreferences;
    ArrayList<AutoAdvertMinInfo> autoAdverts = null;
    AutoAdvertsAdapter adapter = null;
    ListView autoAdvertsListView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mPreferences = getSharedPreferences(CURRENT_USER_PREFERENCES, MODE_PRIVATE);
        autoAdverts = new ArrayList<AutoAdvertMinInfo>();
        adapter = new AutoAdvertsAdapter(this, R.layout.auto_advert_info, autoAdverts);

        autoAdvertsListView = (ListView) findViewById (R.id.auto_adverts_list_view);
        autoAdvertsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showAdvert(position);
            }
        });
        autoAdvertsListView.setAdapter(adapter);

        Button saveFilterButton = (Button) findViewById(R.id.save_filter_button);
        saveFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFilter();
            }
        });
    }

    private void loadAutoAdvertsFromAPI() {
        new AutoAdvertsUploader(this).execute(AUTO_ADVERTS_URL + "?auth_token=" + mPreferences.getString("AuthToken", ""));
    }

    private void saveFilter() {
        new SaveFilterTask(this, getFilterInfo()).execute(FILTERS_URL + "?auth_token=" + mPreferences.getString("AuthToken", ""));
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
        setListViewHeightBasedOnChildren(autoAdvertsListView);
    }

    public FilterInfo getFilterInfo() {
        FilterInfo filterInfo = new FilterInfo();

        String car_mark_id = ActivityHelper.getText(this, R.id.mark);
        if(car_mark_id != null && !car_mark_id.equals("")) {
            filterInfo.setCarMarkId(Integer.parseInt(car_mark_id));
        }

        String car_model_id = ActivityHelper.getText(this, R.id.model);
        if(car_model_id != null && !car_model_id.equals("")) {
            filterInfo.setCarModelId(Integer.parseInt(car_model_id));
        }

        String min_year = ActivityHelper.getText(this, R.id.min_year);
        if(min_year != null && !min_year.equals("")) {
            filterInfo.setMinYear(Integer.parseInt(min_year));
        }

        String max_year = ActivityHelper.getText(this, R.id.max_year);
        if(max_year != null && !max_year.equals("")) {
            filterInfo.setMaxYear(Integer.parseInt(max_year));
        }

        String min_price = ActivityHelper.getText(this, R.id.min_price);
        if(min_price != null && !min_price.equals("")) {
            filterInfo.setMinPrice(Integer.parseInt(min_price));
        }

        String max_price = ActivityHelper.getText(this, R.id.max_price);
        if(max_price != null && !max_price.equals("")) {
            filterInfo.setMaxPrice(Integer.parseInt(max_price));
        }

        return filterInfo;
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
            showWelcomeActivity();
        }
    }

    public void logout() {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.remove(HomeActivity.AUTH_TOKEN);
        editor.commit();

        showWelcomeActivity();
    }

    public void showWelcomeActivity() {
        Intent intent = new Intent(HomeActivity.this, WelcomeActivity.class);
        startActivityForResult(intent, 0);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, LinearLayout.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
