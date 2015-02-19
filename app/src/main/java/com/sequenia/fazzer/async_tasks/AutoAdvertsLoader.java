package com.sequenia.fazzer.async_tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sequenia.fazzer.activities.HomeActivity;
import com.sequenia.fazzer.adapters.AutoAdvertsAdapter;
import com.sequenia.fazzer.helpers.ActivityHelper;
import com.sequenia.fazzer.helpers.ApiHelper;
import com.sequenia.fazzer.requests_data.AutoAdvertFullInfo;
import com.sequenia.fazzer.requests_data.AutoAdvertMinInfo;
import com.sequenia.fazzer.requests_data.Response;

import java.util.ArrayList;

/**
 * Created by chybakut2004 on 04.02.15.
 */
public class AutoAdvertsLoader extends AsyncTask<String, Void, String> {

    private Context context;
    private ArrayList<AutoAdvertMinInfo> autoAdverts;
    private AutoAdvertsAdapter adapter;

    public AutoAdvertsLoader(Context context, ArrayList<AutoAdvertMinInfo> autoAdverts, AutoAdvertsAdapter adapter) {
        this.context = context;
        this.autoAdverts = autoAdverts;
        this.adapter = adapter;
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
            ArrayList<AutoAdvertMinInfo> newAdverts = (ArrayList<AutoAdvertMinInfo>) r.getData();

            this.autoAdverts.clear();
            this.autoAdverts.addAll(0, newAdverts);
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(context, "Данные не получены", Toast.LENGTH_LONG).show();
        }
    }
}
