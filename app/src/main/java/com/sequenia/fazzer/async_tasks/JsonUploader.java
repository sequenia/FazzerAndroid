package com.sequenia.fazzer.async_tasks;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by chybakut2004 on 04.02.15.
 */
public class JsonUploader extends AsyncTask<String, Void, String> {
    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;
    String resultJson = null;

    @Override
    protected String doInBackground(String... params) {
        String url_str = params[0];
        if(url_str != null) {
            try {
                URL url = new URL(url_str);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                resultJson = buffer.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return resultJson;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
