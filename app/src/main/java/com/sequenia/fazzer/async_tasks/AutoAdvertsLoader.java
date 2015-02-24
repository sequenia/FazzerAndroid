package com.sequenia.fazzer.async_tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sequenia.fazzer.helpers.ApiHelper;
import com.sequenia.fazzer.helpers.FazzerHelper;
import com.sequenia.fazzer.objects.AutoAdvertMinInfo;
import com.sequenia.fazzer.requests_data.Response;

import java.util.ArrayList;

/**
 * Created by chybakut2004 on 04.02.15.
 */
public abstract class AutoAdvertsLoader extends AsyncTask<Void, Void, String> {

    private Context context;
    private String url;

    public AutoAdvertsLoader(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... params) {
        return ApiHelper.loadJson(url);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        url = ApiHelper.AUTO_ADVERTS_URL + "?auth_token=" + FazzerHelper.getAuthToken(context);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if(s != null) {
            Response r = new Gson().fromJson(s, new TypeToken<Response<ArrayList<AutoAdvertMinInfo>>>() {}.getType());
            ArrayList<AutoAdvertMinInfo> newAdverts = (ArrayList<AutoAdvertMinInfo>) r.getData();
            onPostExecuteCustom(newAdverts);
        } else {
            Toast.makeText(context, "Данные не получены", Toast.LENGTH_LONG).show();
        }
    }

    public abstract void onPostExecuteCustom(ArrayList<AutoAdvertMinInfo> newAdverts);
}
