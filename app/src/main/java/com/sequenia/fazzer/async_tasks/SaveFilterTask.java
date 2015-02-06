package com.sequenia.fazzer.async_tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sequenia.fazzer.requests_data.FilterInfo;
import com.sequenia.fazzer.requests_data.Response;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

/**
 * Created by chybakut2004 on 06.02.15.
 */
public class SaveFilterTask extends AsyncTask<String, Void, Response<String>> {

    private Context context = null;
    private FilterInfo filterInfo = null;

    public SaveFilterTask(Context context, FilterInfo filterInfo) {
        this.context = context;
        this.filterInfo = filterInfo;
    }

    @Override
    protected Response<String> doInBackground(String... params) {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(params[0]);
        Response<String> response = null;

        try {
            Gson gson = new GsonBuilder().create();
            String json = gson.toJson(filterInfo);

            StringEntity se = new StringEntity(json);
            post.setEntity(se);

            // setup the request headers
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-Type", "application/json");

            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String s = client.execute(post, responseHandler);
            response = new Gson().fromJson(s, new TypeToken<Response<String>>() {}.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    @Override
    protected void onPostExecute(Response<String> response) {
        if(response != null) {
            Toast.makeText(context, response.getInfo(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Данные не получены", Toast.LENGTH_LONG).show();
        }
    }
}
