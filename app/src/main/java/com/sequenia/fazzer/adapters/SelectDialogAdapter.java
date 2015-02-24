package com.sequenia.fazzer.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.sequenia.fazzer.R;
import com.sequenia.fazzer.objects.Option;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by chybakut2004 on 24.02.15.
 */
public class SelectDialogAdapter extends ArrayAdapter<Option> {
    private ArrayList<Option> options;
    private Context context;

    public SelectDialogAdapter(Context context, int resource, List<Option> objects) {
        super(context, resource, objects);
        this.options = (ArrayList<Option>) objects;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view =  null;

        if(convertView == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            view = inflater.inflate(R.layout.select_dialog_item, parent, false);
        } else {
            view = convertView;
        }

        TextView textView = (TextView) view.findViewById(R.id.text);
        textView.setText(options.get(position).getLabel());

        return view;
    }
}
