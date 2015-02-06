package com.sequenia.fazzer.async_tasks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.sequenia.fazzer.activities.HomeActivity;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

/**
 * Created by chybakut2004 on 04.02.15.
 */
public class LoginTask extends AsyncTask<String, Void, JSONObject> {

    private String phone;
    private String password;
    private SharedPreferences preferences;
    private Context context;

    public LoginTask(String phone, String password, SharedPreferences preferences, Context context) {
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

        } catch (Exception e) {
            e.printStackTrace();
        }

        return json;
    }

    @Override
    protected void onPostExecute(JSONObject json) {
        try {
            if (json.getBoolean("success")) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(HomeActivity.AUTH_TOKEN, json.getJSONObject("data").getString("auth_token"));
                editor.commit();

                // launch the HomeActivity and close this one
                Intent intent = new Intent(context.getApplicationContext(), HomeActivity.class);
                context.startActivity(intent);
                ((Activity) context).finish();
            }
            Toast.makeText(context, json.getString("info"), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            super.onPostExecute(json);
        }
    }
}
