package com.sequenia.fazzer.async_tasks.json_loaders;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sequenia.fazzer.activities.AutoAdvertActivity;
import com.sequenia.fazzer.requests_data.AutoAdvertFullInfo;
import com.sequenia.fazzer.requests_data.Response;

/**
 * Created by chybakut2004 on 04.02.15.
 */
public class AutoAdvertLoader extends JsonLoader {

    Context context;

    public AutoAdvertLoader(Context context) {
        this.context = context;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if(s != null) {
            Response r = new Gson().fromJson(s, new TypeToken<Response<AutoAdvertFullInfo>>() {}.getType());
            AutoAdvertFullInfo autoAdvert = (AutoAdvertFullInfo) r.getData();

            AutoAdvertActivity activity = (AutoAdvertActivity) context;
            activity.setAdvertInfo(autoAdvert);

        } else {
            Toast.makeText(context, "Данные не получены", Toast.LENGTH_LONG).show();
        }
    }
}
