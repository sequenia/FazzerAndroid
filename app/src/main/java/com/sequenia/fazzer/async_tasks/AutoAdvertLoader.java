package com.sequenia.fazzer.async_tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sequenia.fazzer.activities.AutoAdvertActivity;
import com.sequenia.fazzer.helpers.ApiHelper;
import com.sequenia.fazzer.helpers.FazzerHelper;
import com.sequenia.fazzer.objects.AutoAdvertFullInfo;
import com.sequenia.fazzer.requests_data.Response;

/**
 * Created by chybakut2004 on 04.02.15.
 */
public abstract class AutoAdvertLoader extends AsyncTask<Void, Void, String> {

    private Context context;
    private int id;
    private String url;

    public AutoAdvertLoader(Context context, int id) {
        this.context = context;
        this.id = id;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        url = ApiHelper.AUTO_ADVERT_URL + String.valueOf(id) + ".json" + "?auth_token=" + FazzerHelper.getAuthToken(context);
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            return ApiHelper.loadJson(url);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if(s != null) {
            Response r = new Gson().fromJson(s, new TypeToken<Response<AutoAdvertFullInfo>>() {}.getType());
            AutoAdvertFullInfo autoAdvert = (AutoAdvertFullInfo) r.getData();
            onPostExecuteCustom(autoAdvert);
        } else {
            Toast.makeText(context, "Данные не получены", Toast.LENGTH_LONG).show();
        }
    }

    public abstract void onPostExecuteCustom(AutoAdvertFullInfo autoAdvert);
}
