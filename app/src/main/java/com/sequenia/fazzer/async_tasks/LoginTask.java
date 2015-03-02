package com.sequenia.fazzer.async_tasks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.sequenia.fazzer.activities.FirstFilterActivity;
import com.sequenia.fazzer.activities.HomeActivity;
import com.sequenia.fazzer.gcm.GcmRegistrationService;
import com.sequenia.fazzer.helpers.ActivityHelper;
import com.sequenia.fazzer.helpers.ApiHelper;
import com.sequenia.fazzer.helpers.FazzerHelper;
import com.sequenia.fazzer.helpers.RealmHelper;
import com.sequenia.fazzer.objects.FilterInfo;

import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.net.ConnectException;

/**
 * Created by chybakut2004 on 04.02.15.
 */
public class LoginTask extends WaitingDialog<String, JSONObject> {
    private static final String LOGGING_ERROR = "Ошибка во время входа";
    private static final String WRONG_LOGIN_PASS = "Неверная пара логин/пароль";

    private String phone;
    private String password;
    private SharedPreferences preferences;
    private Context context;

    public LoginTask(String phone, String password, SharedPreferences preferences, Context context) {
        super(context);
        this.phone = phone;
        this.password = password;
        this.preferences = preferences;
        this.context = context;
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        DefaultHttpClient client = new DefaultHttpClient();
        JSONObject json = new JSONObject();
        JSONObject userObj = new JSONObject();
        JSONObject holder = new JSONObject();
        HttpPost post = new HttpPost(params[0]);
        String response = null;
        try {
            // setup the returned values in case
            // something goes wrong
            json.put("success", false);
            json.put("info", "Something went wrong. Retry!");
            // add the user email and password to
            // the params
            userObj.put("phone", phone);
            userObj.put("password", password);
            holder.put("user", userObj);
            StringEntity se = new StringEntity(holder.toString());
            post.setEntity(se);

            // setup the request headers
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-Type", "application/json");

            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            response = client.execute(post, responseHandler);
            json = new JSONObject(response);
        } catch(HttpResponseException e) {
            e.printStackTrace();
            if(e.getStatusCode() == 401) {
                publishProgress("", WRONG_LOGIN_PASS);
            } else {
                publishProgress("", LOGGING_ERROR);
            }
            return null;
        } catch (ConnectException e) {
            e.printStackTrace();
            publishProgress("", FazzerHelper.NO_CONNECTION);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            publishProgress("", LOGGING_ERROR);
            return null;
        }

        return json;
    }

    @Override
    protected void onPostExecute(JSONObject json) {
        try {
            if(json != null) {
                if (json.getBoolean("success")) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(FazzerHelper.AUTH_TOKEN, json.getJSONObject("data").getString("auth_token"));
                    editor.putString(FazzerHelper.USER_PHONE, this.phone);
                    editor.commit();

                    // Показать обучалку, если пользователь еще не ввел все данные.
                    // Иначе показать объявления.
                    FilterInfo filterInfo = RealmHelper.getFilter(context, this.phone);
                    if (filterInfo == null) {
                        ActivityHelper.showFirstFilterActivity(context);
                    } else {
                        ActivityHelper.showHomeActivity(context);
                    }
                    ((Activity) context).finish();
                } else {
                    Toast.makeText(context, json.getString("info"), Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, LOGGING_ERROR, Toast.LENGTH_LONG).show();
        } finally {
            super.onPostExecute(json);
        }
    }
}
