package com.sequenia.fazzer.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sequenia.fazzer.R;
import com.sequenia.fazzer.helpers.ObjectsHelper;
import com.sequenia.fazzer.objects.AutoAdvertMinInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by chybakut2004 on 04.02.15.
 */
public class AutoAdvertsAdapter extends ArrayAdapter<AutoAdvertMinInfo> {

    private List<AutoAdvertMinInfo> autoAdverts = null;
    private int resourceId;
    private Context context;


    public AutoAdvertsAdapter(Context context, int resource, List<AutoAdvertMinInfo> autoAdverts) {
        super(context, resource, autoAdverts);
        this.resourceId = resource;
        this.autoAdverts = autoAdverts;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AutoAdvertMinInfo autoAdvert = autoAdverts.get(position);
        View view = null;

        if(convertView == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            view = inflater.inflate(resourceId, parent, false);
        } else {
            view = convertView;
        }

        String label = autoAdvert.getCarMarkName() + " " + autoAdvert.getCarModelName();
        TextView markAndModel = (TextView) view.findViewById(R.id.mark_and_model);
        markAndModel.setText(label);

        TextView price = (TextView) view.findViewById(R.id.price);
        price.setText(ObjectsHelper.prettifyNumber(String.valueOf(autoAdvert.getPrice()), " р."));

        ImageView preview = (ImageView) view.findViewById(R.id.photo);
        Picasso.with(context)
                .load(autoAdvert.getPhoto_preview_url())
                .placeholder(R.drawable.no_photo_min)
                .resizeDimen(R.dimen.photo_preview_size, R.dimen.photo_preview_size)
                .centerCrop()
                .into(preview);

        return view;
    }
}
