package com.sequenia.fazzer;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.sequenia.fazzer.async_tasks.AutoAdvertsUploader;
import com.sequenia.fazzer.async_tasks.JsonUploader;

public class HomeActivity extends ActionBarActivity {

    private static final String AUTO_ADVERTS_URL = "http://www.json-generator.com/api/json/get/cbEqTCJdQi?indent=2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        loadAutoAdvertsFromAPI(AUTO_ADVERTS_URL);
    }

    private void loadAutoAdvertsFromAPI(String url) {
        ListView autoAdvertsListView = (ListView) findViewById (R.id.auto_adverts_list_view);
        new AutoAdvertsUploader(this, autoAdvertsListView).execute(url);
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
                loadAutoAdvertsFromAPI(AUTO_ADVERTS_URL);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
