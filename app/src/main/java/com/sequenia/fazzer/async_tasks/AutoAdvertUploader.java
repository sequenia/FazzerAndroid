package com.sequenia.fazzer.async_tasks;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sequenia.fazzer.R;
import com.sequenia.fazzer.adapters.AutoAdvertsAdapter;
import com.sequenia.fazzer.adverts.AutoAdvertFullInfo;
import com.sequenia.fazzer.adverts.AutoAdvertMinInfo;
import com.sequenia.fazzer.requests.AutoAdvertResponseData;
import com.sequenia.fazzer.requests.AutoAdvertsResponseData;
import com.sequenia.fazzer.requests.Response;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by chybakut2004 on 04.02.15.
 */
public class AutoAdvertUploader extends JsonUploader {

    Context context;

    public AutoAdvertUploader(Context context) {
        this.context = context;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if(s != null) {
            Response r = new Gson().fromJson(s, new TypeToken<Response<AutoAdvertResponseData>>() {}.getType());
            AutoAdvertResponseData data = (AutoAdvertResponseData) r.getData();
            AutoAdvertFullInfo autoAdvert = data.getAutoAdvert();

            Activity activity = (Activity) context;

            setText(activity, R.id.mark, autoAdvert.getCarMarkName());
            setText(activity, R.id.model, autoAdvert.getCarModelName());
            setText(activity, R.id.year, String.valueOf(autoAdvert.getYear()));
            setText(activity, R.id.price, String.valueOf(autoAdvert.getPrice()));
            setText(activity, R.id.fuel, autoAdvert.getFuel());
            setText(activity, R.id.displacement, autoAdvert.getDisplacement());
            setText(activity, R.id.transmission, autoAdvert.getTransmission());
            setText(activity, R.id.drive, autoAdvert.getDrive());
            setText(activity, R.id.mileage, autoAdvert.getMileage());
            setText(activity, R.id.body, autoAdvert.getBody());
            setText(activity, R.id.wheel, autoAdvert.getSteeringWheel());
            setText(activity, R.id.color, autoAdvert.getColor());
            setText(activity, R.id.city, autoAdvert.getCityName());
            setText(activity, R.id.description, autoAdvert.getDescription());

            activity.findViewById(R.id.container).setVisibility(View.VISIBLE);

        } else {
            Toast.makeText(context, "Данные не получены", Toast.LENGTH_LONG).show();
        }
    }

    private void setText(Activity activity, int resourceId, String text) {
        TextView tv = (TextView) activity.findViewById(resourceId);

        if(text != null) {
            tv.setText(text);
        } else {
            ((View)(tv.getParent())).setVisibility(View.GONE);
        }
    }
}
