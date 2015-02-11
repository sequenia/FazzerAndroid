package com.sequenia.fazzer.async_tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sequenia.fazzer.activities.HomeActivity;
import com.sequenia.fazzer.helpers.ApiHelper;
import com.sequenia.fazzer.requests_data.AutoAdvertMinInfo;
import com.sequenia.fazzer.requests_data.Response;

import java.util.ArrayList;

/**
 * Created by chybakut2004 on 04.02.15.
 */
public class AutoAdvertsLoader extends AsyncTask<String, Void, String> {

    Context context;

    public AutoAdvertsLoader(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        return ApiHelper.loadJson(params[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if(s != null) {
            Response r = new Gson().fromJson(s, new TypeToken<Response<ArrayList<AutoAdvertMinInfo>>>() {}.getType());
            ArrayList<AutoAdvertMinInfo> autoAdverts = (ArrayList<AutoAdvertMinInfo>) r.getData();

            ((HomeActivity) context).addAdverts(autoAdverts);
        } else {
            Toast.makeText(context, "Данные не получены", Toast.LENGTH_LONG).show();
        }
    }
}
