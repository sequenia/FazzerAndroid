package com.sequenia.fazzer.async_tasks;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sequenia.fazzer.HomeActivity;
import com.sequenia.fazzer.adverts.AutoAdvertMinInfo;
import com.sequenia.fazzer.requests.Response;

import java.util.ArrayList;

/**
 * Created by chybakut2004 on 04.02.15.
 */
public class AutoAdvertsUploader extends JsonUploader {

    Context context;

    public AutoAdvertsUploader(Context context) {
        this.context = context;
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
