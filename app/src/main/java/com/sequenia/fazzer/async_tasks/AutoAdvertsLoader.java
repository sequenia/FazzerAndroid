package com.sequenia.fazzer.async_tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sequenia.fazzer.fragments.FilterFragment;
import com.sequenia.fazzer.gson.RealmStrategy;
import com.sequenia.fazzer.helpers.ApiHelper;
import com.sequenia.fazzer.helpers.FazzerHelper;
import com.sequenia.fazzer.objects.AutoAdvertMinInfo;
import com.sequenia.fazzer.requests_data.Response;

import java.net.ConnectException;
import java.util.ArrayList;

import io.realm.RealmObject;

/**
 * Created by chybakut2004 on 04.02.15.
 */
public abstract class AutoAdvertsLoader extends AsyncTask<Void, String, Response<ArrayList<AutoAdvertMinInfo>>> {

    public static final String ADVERTS_LOADING_ERROR = "Ошибка загрузки объявлений";

    private Context context;
    private String url;

    public AutoAdvertsLoader(Context context) {
        this.context = context;
    }

    @Override
    protected Response<ArrayList<AutoAdvertMinInfo>> doInBackground(Void... params) {
        Response<ArrayList<AutoAdvertMinInfo>> result = null;
        try {
            String json = ApiHelper.loadJson(url);
            Gson gson = new GsonBuilder().setExclusionStrategies(new RealmStrategy()).create();
            result = gson.fromJson(json, new TypeToken<Response<ArrayList<AutoAdvertMinInfo>>>() {}.getType());
        } catch (ConnectException e) {
            e.printStackTrace();
            publishProgress(FazzerHelper.NO_CONNECTION);
        } catch (Exception e) {
            e.printStackTrace();
            publishProgress(ADVERTS_LOADING_ERROR);
        }
        return result;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        url = ApiHelper.AUTO_ADVERTS_URL + "?auth_token=" + FazzerHelper.getAuthToken(context);
    }

    @Override
    protected void onPostExecute(Response<ArrayList<AutoAdvertMinInfo>> response) {
        onPostExecuteCustom(response);
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        Toast.makeText(context, values[0], Toast.LENGTH_LONG).show();
    }

    public abstract void onPostExecuteCustom(Response<ArrayList<AutoAdvertMinInfo>> response);
}
