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
import com.sequenia.fazzer.objects.AutoAdvertMinInfo;
import com.sequenia.fazzer.requests_data.Response;

import java.net.ConnectException;
import java.util.ArrayList;

/**
 * Created by chybakut2004 on 04.02.15.
 */
public abstract class AutoAdvertLoader extends AsyncTask<Void, String, Response<AutoAdvertFullInfo>> {
    public static final String ADVERT_LOADING_ERROR = "Ошибка загрузки объявления";

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
    protected Response<AutoAdvertFullInfo> doInBackground(Void... params) {
        try {
            String json = ApiHelper.loadJson(url);
            return new Gson().fromJson(json, new TypeToken<Response<AutoAdvertFullInfo>>() {}.getType());
        } catch (ConnectException e) {
            e.printStackTrace();
            publishProgress(FazzerHelper.NO_CONNECTION);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            publishProgress(ADVERT_LOADING_ERROR);
            return null;
        }
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        Toast.makeText(context, values[0], Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPostExecute(Response<AutoAdvertFullInfo> response) {
        onPostExecuteCustom(response);
    }

    public abstract void onPostExecuteCustom(Response<AutoAdvertFullInfo> response);
}
