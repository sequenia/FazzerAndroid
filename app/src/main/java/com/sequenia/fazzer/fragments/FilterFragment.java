package com.sequenia.fazzer.fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sequenia.fazzer.R;
import com.sequenia.fazzer.async_tasks.SaveFilterTask;
import com.sequenia.fazzer.helpers.ActivityHelper;
import com.sequenia.fazzer.helpers.ApiHelper;
import com.sequenia.fazzer.helpers.FazzerHelper;
import com.sequenia.fazzer.helpers.ObjectsHelper;
import com.sequenia.fazzer.helpers.RealmHelper;
import com.sequenia.fazzer.requests_data.FilterInfo;
import com.sequenia.fazzer.serializers.FilterInfoSerializer;

import io.realm.Realm;

/**
 * Created by chybakut2004 on 12.02.15.
 */
public class FilterFragment extends Fragment {

    FilterInfo filterInfo = null;

    public FilterFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initSaveButton();
    }

    @Override
    public void onResume() {
        super.onResume();
        initFilter();
    }

    private void initSaveButton() {
        Button saveFilterButton = (Button) getActivity().findViewById(R.id.save_filter_button);
        saveFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = getActivity();
                saveFilter();
                if(getActivity().getIntent().getBooleanExtra(FazzerHelper.NEEDS_CLOSE, false)) {
                    ActivityHelper.showHomeActivity(activity);
                    activity.finish();
                };
            }
        });
    }

    private void saveFilter() {
        Activity activity = getActivity();
        readFilterInfoFromForm();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(FilterInfo.class, new FilterInfoSerializer())
                .create();
        String json = gson.toJson(filterInfo, FilterInfo.class);

        new SaveFilterTask(activity, json).execute(ApiHelper.FILTERS_URL + "?auth_token=" + FazzerHelper.getAuthToken(activity));
    }

    public void readFilterInfoFromForm() {
        Activity activity = getActivity();
        Realm realm = Realm.getInstance(activity);
        realm.beginTransaction();

        filterInfo.setCarMarkId(ObjectsHelper.strToIntNoZero(ActivityHelper.getText(activity, R.id.mark)));
        filterInfo.setCarModelId(ObjectsHelper.strToIntNoZero(ActivityHelper.getText(activity, R.id.model)));
        filterInfo.setMinPrice(ObjectsHelper.strToFloatNoZero(ActivityHelper.getText(activity, R.id.min_price)));
        filterInfo.setMaxPrice(ObjectsHelper.strToFloatNoZero(ActivityHelper.getText(activity, R.id.max_price)));
        filterInfo.setMinYear(ObjectsHelper.strToIntNoZero(ActivityHelper.getText(activity, R.id.min_year)));
        filterInfo.setMaxYear(ObjectsHelper.strToIntNoZero(ActivityHelper.getText(activity, R.id.max_year)));

        realm.commitTransaction();
    }

    private void initFilter() {
        Activity activity = getActivity();
        SharedPreferences mPreferences = FazzerHelper.getUserPreferences(activity);
        filterInfo = RealmHelper.findOrCreateFilter(activity, mPreferences.getString(FazzerHelper.USER_PHONE, ""));
        writeFilterToForm();
    }

    public void writeFilterToForm() {
        Activity activity = getActivity();
        if(filterInfo != null) {
            ActivityHelper.setText(activity, R.id.mark, ObjectsHelper.intToStrNoZero(filterInfo.getCarMarkId()));
            ActivityHelper.setText(activity, R.id.model, ObjectsHelper.intToStrNoZero(filterInfo.getCarModelId()));
            ActivityHelper.setText(activity, R.id.min_price, ObjectsHelper.floatToStrNoZero(filterInfo.getMinPrice()));
            ActivityHelper.setText(activity, R.id.max_price, ObjectsHelper.floatToStrNoZero(filterInfo.getMaxPrice()));
            ActivityHelper.setText(activity, R.id.min_year, ObjectsHelper.intToStrNoZero(filterInfo.getMinYear()));
            ActivityHelper.setText(activity, R.id.max_year, ObjectsHelper.intToStrNoZero(filterInfo.getMaxYear()));
        }
    }
}
