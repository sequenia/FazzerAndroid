package com.sequenia.fazzer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.sequenia.fazzer.async_tasks.AutoAdvertsUploader;

public class HomeActivity extends ActionBarActivity {

    public static final String CURRENT_USER_PREFERENCES = "CurrentUser";
    public static final String AUTH_TOKEN = "AuthToken";
    private static final String AUTO_ADVERTS_URL = "http://192.168.0.36:3000/api/v1/auto_adverts.json";

    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mPreferences = getSharedPreferences(CURRENT_USER_PREFERENCES, MODE_PRIVATE);
    }

    private void loadAutoAdvertsFromAPI() {
        ListView autoAdvertsListView = (ListView) findViewById (R.id.auto_adverts_list_view);
        new AutoAdvertsUploader(this, autoAdvertsListView).execute(AUTO_ADVERTS_URL + "?auth_token=" + mPreferences.getString("AuthToken", ""));
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
}
