package com.sequenia.fazzer.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sequenia.fazzer.R;
import com.sequenia.fazzer.activities.HomeActivity;
import com.sequenia.fazzer.async_tasks.AutoAdvertsLoader;
import com.sequenia.fazzer.async_tasks.SaveFilterTask;
import com.sequenia.fazzer.forms.FormManager;
import com.sequenia.fazzer.forms.SelectDialogFragment;
import com.sequenia.fazzer.forms.SelectDialogListManager;
import com.sequenia.fazzer.helpers.ActivityHelper;
import com.sequenia.fazzer.helpers.FazzerHelper;
import com.sequenia.fazzer.helpers.ObjectsHelper;
import com.sequenia.fazzer.helpers.RealmHelper;
import com.sequenia.fazzer.objects.AutoAdvertMinInfo;
import com.sequenia.fazzer.objects.FilterInfo;
import com.sequenia.fazzer.gson.FilterInfoSerializer;
import com.sequenia.fazzer.objects.Option;
import com.sequenia.fazzer.requests_data.Response;

import java.util.ArrayList;

import io.realm.Realm;

/**
 * Created by chybakut2004 on 12.02.15.
 */
public class FilterFragment extends Fragment {
    private FilterInfo filterInfo = null;
    private FormManager dialogManager;
    private static final String MARK = "mark";
    private static final String MODEL = "model";
    private static final String CITY = "city";

    public FilterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter, container, false);

        initDialogs(view);

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

    private void initDialogs(View view) {
        final FragmentActivity activity = getActivity();
        dialogManager = new FormManager(this);

        TextView mark = (TextView) view.findViewById(R.id.mark);
        dialogManager.addSelect(mark, MARK, activity.getResources().getString(R.string.mark),
                new SelectDialogListManager() {
                    @Override
                    public ArrayList<Option> getList() {
                        return ObjectsHelper.genCarMarkOptions(RealmHelper.getCarMarks(activity));
                    }
                });

        TextView model = (TextView) view.findViewById(R.id.model);
        dialogManager.addSelect(model, MODEL, activity.getResources().getString(R.string.model),
                new SelectDialogListManager() {
                    @Override
                    public ArrayList<Option> getList() {
                        return ObjectsHelper.genCarModelOptions(RealmHelper.getCarModels(activity));
                    }
                });

        TextView city = (TextView) view.findViewById(R.id.city);
        dialogManager.addSelect(city, CITY, activity.getResources().getString(R.string.city),
                new SelectDialogListManager() {
                    @Override
                    public ArrayList<Option> getList() {
                        return ObjectsHelper.genCityOptions(RealmHelper.getCities(activity));
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == FormManager.SELECT_DIALOG_REQUEST_CODE) {
            dialogManager.setResult(data.getStringExtra(SelectDialogFragment.ARG_NAME),
                    (Option) data.getParcelableExtra(SelectDialogFragment.VALUE));
        }
    }

    private void saveFilter() {
        Activity activity = getActivity();
        readFilterInfoFromForm();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(FilterInfo.class, new FilterInfoSerializer())
                .create();
        String json = gson.toJson(filterInfo, FilterInfo.class);

        new SaveFilterTask(activity) {
            @Override
            public void onPostExecuteCustom(Response<String> response) {
                showResultMessage(response);
                showNewAdverts(response);
            }
        }.execute(json);
    }

    private void showResultMessage(Response<String> response) {
        if(response != null) {
            Toast.makeText(getActivity(), response.getInfo(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), "Данные не получены", Toast.LENGTH_LONG).show();
        }
    }

    private void showNewAdverts(Response<String> response) {
        final HomeActivity activity = (HomeActivity) getActivity();
        if(response != null) {
            new AutoAdvertsLoader(activity) {
                @Override
                public void onPostExecuteCustom(ArrayList<AutoAdvertMinInfo> newAdverts) {
                    activity.showNewAdverts(newAdverts);
                }
            }.execute();
        }
    }

    public void readFilterInfoFromForm() {
        Activity activity = getActivity();
        Realm realm = Realm.getInstance(activity);
        realm.beginTransaction();

        filterInfo.setCarMarkId(ObjectsHelper.strToIntNoZero(ActivityHelper.getTextTextView(activity, R.id.mark)));
        filterInfo.setCarModelId(ObjectsHelper.strToIntNoZero(ActivityHelper.getTextTextView(activity, R.id.model)));
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
