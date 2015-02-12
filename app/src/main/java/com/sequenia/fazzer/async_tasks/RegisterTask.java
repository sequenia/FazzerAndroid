package com.sequenia.fazzer.async_tasks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.sequenia.fazzer.activities.LoginActivity;
import com.sequenia.fazzer.helpers.FazzerHelper;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

/**
 * Created by chybakut2004 on 04.02.15.
 */
public class RegisterTask extends WaitingDialog<String, JSONObject> {

    private String phone;
    private SharedPreferences preferences;
    private Context context;

    public RegisterTask(String phone, SharedPreferences preferences, Context context) {
        super(context);
        this.phone = phone;
        this.preferences = preferences;
        this.context = context;
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(params[0]);
        JSONObject holder = new JSONObject();
        JSONObject userObj = new JSONObject();
        String response = null;
        JSONObject json = new JSONObject();

        try {
            json.put("success", false);
            json.put("info", "Something went wrong. Retry!");

            userObj.put("phone", phone);
            holder.put("user", userObj);
            StringEntity se = new StringEntity(holder.toString());
            post.setEntity(se);

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
                editor.putBoolean(FazzerHelper.REGISTERED, true);
                editor.commit();

                Intent intent = new Intent(context.getApplicationContext(), LoginActivity.class);
                intent.putExtra("phone", json.getJSONObject("data").getJSONObject("user").getString("phone"));
                context.startActivity(intent);
                ((Activity)context).finish();
            }
            Toast.makeText(context, json.getString("info"), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            super.onPostExecute(json);
        }
    }
}
