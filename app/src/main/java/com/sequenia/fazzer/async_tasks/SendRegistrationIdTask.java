package com.sequenia.fazzer.async_tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sequenia.fazzer.requests_data.Device;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

/**
 * Created by chybakut2004 on 18.02.15.
 */
public class SendRegistrationIdTask extends AsyncTask<String, Void, String> {

    private Context context = null;
    private String registrationId = null;
    private String deviceEntity;

    public SendRegistrationIdTask(Context context, String registrationId) {
        this.context = context;
        this.registrationId = registrationId;

        Device device = new Device();
        device.setToken(registrationId);

        deviceEntity = new Gson().toJson(device, Device.class);
    }

    @Override
    protected String doInBackground(String... params) {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(params[0]);
        String response = null;

        try {
            StringEntity se = new StringEntity(deviceEntity);
            post.setEntity(se);

            // setup the request headers
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-Type", "application/json");

            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            response = client.execute(post, responseHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    @Override
    protected void onPostExecute(String response) {
        if(response != null) {
            Toast.makeText(context, response, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Данные не получены", Toast.LENGTH_LONG).show();
        }
    }
}