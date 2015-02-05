package com.sequenia.fazzer.async_tasks;

import android.content.Context;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sequenia.fazzer.R;
import com.sequenia.fazzer.adapters.AutoAdvertsAdapter;
import com.sequenia.fazzer.adverts.AutoAdvert;
import com.sequenia.fazzer.requests.AutoAdvertsResponseData;
import com.sequenia.fazzer.requests.Response;

import java.util.ArrayList;

/**
 * Created by chybakut2004 on 04.02.15.
 */
public class AutoAdvertsUploader extends GetUploader {

    ListView autoAdvertsListVew;
    Context context;

    public AutoAdvertsUploader(Context context, ListView autoAdvertsListVew) {
        this.autoAdvertsListVew = autoAdvertsListVew;
        this.context = context;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if(s != null) {
            Response r = new Gson().fromJson(s, new TypeToken<Response<AutoAdvertsResponseData>>() {}.getType());
            AutoAdvertsResponseData data = (AutoAdvertsResponseData) r.getData();
            ArrayList<AutoAdvert> autoAdverts = (ArrayList<AutoAdvert>) data.getAutoAdverts();

            if (autoAdvertsListVew != null && autoAdverts != null) {
                AutoAdvertsAdapter adapter = new AutoAdvertsAdapter(context, R.layout.auto_advert_info, autoAdverts);
                autoAdvertsListVew.setAdapter(adapter);
            }
        } else {
            Toast.makeText(context, "Данные не получены", Toast.LENGTH_LONG).show();
        }
    }
}