package com.sequenia.fazzer.async_tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sequenia.fazzer.fragments.FilterFragment;
import com.sequenia.fazzer.helpers.ApiHelper;
import com.sequenia.fazzer.helpers.FazzerHelper;
import com.sequenia.fazzer.requests_data.Response;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.net.ConnectException;
import java.util.logging.Filter;

/**
 * Created by chybakut2004 on 06.02.15.
 */
public abstract class SaveFilterTask extends AsyncTask<String, String, Response<String>> {

    private Context context = null;
    private String url = null;

    public SaveFilterTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        url = ApiHelper.FILTERS_URL + "?auth_token=" + FazzerHelper.getAuthToken(context);
    }

    @Override
    protected Response<String> doInBackground(String... params) {
        String filterInfoJson = params[0];
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        Response<String> response = null;

        try {
            StringEntity se = new StringEntity(filterInfoJson);
            post.setEntity(se);

            // setup the request headers
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-Type", "application/json");

            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String s = client.execute(post, responseHandler);
            response = new Gson().fromJson(s, new TypeToken<Response<String>>() {}.getType());
        } catch (ConnectException e) {
            e.printStackTrace();
            publishProgress(FazzerHelper.NO_CONNECTION);
        } catch (Exception e) {
            e.printStackTrace();
            publishProgress(FilterFragment.FILTER_SAVING_ERROR);
        }

        return response;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        Toast.makeText(context, values[0], Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPostExecute(Response<String> response) {
        onPostExecuteCustom(response);
    }

    public abstract void onPostExecuteCustom(Response<String> response);
}
