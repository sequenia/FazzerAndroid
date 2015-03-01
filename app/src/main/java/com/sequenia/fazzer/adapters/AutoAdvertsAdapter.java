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
import com.squareup.picasso.Callback;
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

        final View progressBar = view.findViewById(R.id.image_progress_bar);

        String label = autoAdvert.getCar_mark_name() + " " + autoAdvert.getCar_model_name();
        TextView markAndModel = (TextView) view.findViewById(R.id.mark_and_model);
        markAndModel.setText(label);

        TextView price = (TextView) view.findViewById(R.id.price);
        price.setText(ObjectsHelper.prettifyNumber(String.valueOf(autoAdvert.getPrice()), " р."));

        ImageView preview = (ImageView) view.findViewById(R.id.photo);
        String url = autoAdvert.getPhoto_preview_url();
        if(ObjectsHelper.isEmpty(url)) {
            progressBar.setVisibility(View.GONE);
            Picasso.with(context)
                    .load(R.drawable.no_photo_min)
                    .resizeDimen(R.dimen.photo_preview_size, R.dimen.photo_preview_size)
                    .centerCrop()
                    .into(preview);
        } else {
            progressBar.setVisibility(View.VISIBLE);
            Picasso.with(context)
                    .load(url)
                    .placeholder(R.drawable.loading_photo_min)
                    .resizeDimen(R.dimen.photo_preview_size, R.dimen.photo_preview_size)
                    .centerCrop()
                    .into(preview, new Callback() {
                        @Override
                        public void onSuccess() {
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {

                        }
                    });
        }

        return view;
    }
}
