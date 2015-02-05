package com.sequenia.fazzer.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sequenia.fazzer.R;
import com.sequenia.fazzer.adverts.AutoAdvertMinInfo;

import java.util.List;

/**
 * Created by chybakut2004 on 04.02.15.
 */
public class AutoAdvertsAdapter extends ArrayAdapter<AutoAdvertMinInfo> {

    private List<AutoAdvertMinInfo> autoAdverts = null;
    private int resourceId;


    public AutoAdvertsAdapter(Context context, int resource, List<AutoAdvertMinInfo> autoAdverts) {
        super(context, resource, autoAdverts);
        this.resourceId = resource;
        this.autoAdverts = autoAdverts;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AutoAdvertMinInfo autoAdvert = autoAdverts.get(position);

        LayoutInflater inflater = ((Activity)getContext()).getLayoutInflater();
        View view = inflater.inflate(resourceId, parent, false);

        String label = autoAdvert.getCarMarkName() + " " + autoAdvert.getCarModelName();
        TextView markAndModel = (TextView) view.findViewById(R.id.mark_and_model);
        markAndModel.setText(label);

        TextView price = (TextView) view.findViewById(R.id.price);
        price.setText(String.valueOf(autoAdvert.getPrice()));

        TextView year = (TextView) view.findViewById(R.id.year);
        year.setText(String.valueOf(autoAdvert.getYear()));

        return view;
    }
}
