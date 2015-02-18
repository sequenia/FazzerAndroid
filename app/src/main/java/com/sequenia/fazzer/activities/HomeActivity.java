package com.sequenia.fazzer.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sequenia.fazzer.R;
import com.sequenia.fazzer.adapters.AutoAdvertsAdapter;
import com.sequenia.fazzer.async_tasks.SendRegistrationIdTask;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;

public class HomeActivity extends ActionBarActivity {

    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    static final String TAG = "GCM";

    String SENDER_ID = "494765454871";
    GoogleCloudMessaging gcm;
    AtomicInteger msgId = new AtomicInteger();
    Context context;
    String regid;

    private SharedPreferences mPreferences;
    ArrayList<AutoAdvertMinInfo> autoAdverts = null;
    AutoAdvertsAdapter adapter = null;
    ListView autoAdvertsListView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        context = getApplicationContext();

        /*final SharedPreferences prefs = getGCMPreferences(context);
        SharedPreferences.Editor e = prefs.edit();
        e.remove(PROPERTY_REG_ID);
        e.commit();*/

        if (checkPlayServices()) {
            gcm = GoogleCloudMessaging.getInstance(this);
            regid = getRegistrationId(context);

            if (regid.isEmpty()) {
                registerInBackground();
            }
        } else {
            Log.i(TAG, "No valid Google Play Services APK found.");
        }

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
                FazzerHelper.loadAutoAdvertsFromAPI(this);
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

        checkPlayServices();

        if (mPreferences.contains(FazzerHelper.AUTH_TOKEN)) {
            FazzerHelper.updateCatalogs(this);
            FazzerHelper.loadAutoAdvertsFromAPI(this);
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

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    /**
     * Gets the current registration ID for application on GCM service.
     * <p>
     * If result is empty, the app needs to register.
     *
     * @return registration ID, or empty string if there is no existing
     *         registration ID.
     */
    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing registration ID is not guaranteed to work with
        // the new app version.
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }

    /**
     * @return Application's {@code SharedPreferences}.
     */
    private SharedPreferences getGCMPreferences(Context context) {
        // This sample app persists the registration ID in shared preferences, but
        // how you store the registration ID in your app is up to you.
        return getSharedPreferences(HomeActivity.class.getSimpleName(), Context.MODE_PRIVATE);
    }

    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    /**
     * Registers the application with GCM servers asynchronously.
     * <p>
     * Stores the registration ID and app versionCode in the application's
     * shared preferences.
     */
    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    regid = gcm.register(SENDER_ID);
                    msg = "Device registered, registration ID=" + regid;

                    // You should send the registration ID to your server over HTTP,
                    // so it can use GCM/HTTP or CCS to send messages to your app.
                    // The request to your server should be authenticated if your app
                    // is using accounts.
                    sendRegistrationIdToBackend(regid);

                    // Persist the registration ID - no need to register again.
                    storeRegistrationId(context, regid);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                System.out.println(msg);
                //mDisplay.append(msg + "\n");
            }
        }.execute(null, null, null);
    }

    /**
     * Stores the registration ID and app versionCode in the application's
     * {@code SharedPreferences}.
     *
     * @param context application's context.
     * @param regId registration ID
     */
    private void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGCMPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    /**
     * Sends the registration ID to your server over HTTP, so it can use GCM/HTTP
     * or CCS to send messages to your app. Not needed for this demo since the
     * device sends upstream messages to a server that echoes back the message
     * using the 'from' address in the message.
     */
    private void sendRegistrationIdToBackend(String regId) {
        new SendRegistrationIdTask(this, regId).execute(ApiHelper.REGISTRATION_ID_URL + "?auth_token=" + FazzerHelper.getAuthToken(this));
    }
}
