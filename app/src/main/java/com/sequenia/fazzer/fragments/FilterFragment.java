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
import android.widget.ImageButton;
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
import com.sequenia.fazzer.objects.CarMark;
import com.sequenia.fazzer.objects.CarModel;
import com.sequenia.fazzer.objects.City;
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
    public static final String FILTER_SAVING_ERROR = "Ошибка при сохранении фильтра";
    private FilterInfo filterInfo = null;
    private FormManager formManager;
    private static final String MARK = "mark";
    private static final String MODEL = "model";
    private static final String CITY = "city";

    private Button saveFilterButton;

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
        saveFilterButton = (Button) getActivity().findViewById(R.id.save_filter_button);
        saveFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFilter();
            }
        });
    }

    private void initDialogs(View view) {
        final FragmentActivity activity = getActivity();
        formManager = new FormManager(this);

        TextView mark = (TextView) view.findViewById(R.id.mark);
        ImageButton clearMark = (ImageButton) view.findViewById(R.id.clear_mark);
        formManager.addSelect(mark, clearMark, MARK, activity.getResources().getString(R.string.mark),
                new SelectDialogListManager() {
                    @Override
                    public ArrayList<Option> getList() {
                        return ObjectsHelper.genCarMarkOptions(RealmHelper.getCarMarks(activity));
                    }
                });

        TextView model = (TextView) view.findViewById(R.id.model);
        ImageButton clearModel = (ImageButton) view.findViewById(R.id.clear_model);
        formManager.addSelect(model, clearModel, MODEL, activity.getResources().getString(R.string.model),
                new SelectDialogListManager() {
                    @Override
                    public ArrayList<Option> getList() {
                        return ObjectsHelper.genCarModelOptions(RealmHelper.getCarModels(activity));
                    }
                });

        TextView city = (TextView) view.findViewById(R.id.city);
        ImageButton clearCity = (ImageButton) view.findViewById(R.id.clear_city);
        formManager.addSelect(city, clearCity, CITY, activity.getResources().getString(R.string.city),
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
            formManager.setResult(data.getStringExtra(SelectDialogFragment.ARG_NAME),
                    (Option) data.getParcelableExtra(SelectDialogFragment.VALUE));
        }
    }

    private void saveFilter() {
        final Activity activity = getActivity();

        saveFilterButton.setText(getResources().getString(R.string.saving));
        saveFilterButton.setEnabled(false);

        readFilterInfoFromForm();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(FilterInfo.class, new FilterInfoSerializer())
                .create();
        String json = gson.toJson(filterInfo, FilterInfo.class);

        new SaveFilterTask(activity) {
            @Override
            public void onPostExecuteCustom(Response<String> response) {
                saveFilterButton.setText(getResources().getString(R.string.save_filter));
                saveFilterButton.setEnabled(true);
                showResultMessage(response);

                if(activity.getIntent().getBooleanExtra(FazzerHelper.NEEDS_CLOSE, false)) {
                    ActivityHelper.showHomeActivity(activity);
                    activity.finish();
                };

                loadNewAdverts(response);
            }
        }.execute(json);
    }

    private void showResultMessage(Response<String> response) {
        if(response != null) {
            if(response.getSuccess()) {
                Toast.makeText(getActivity(), "Фильтр сохранен", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), FILTER_SAVING_ERROR, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void loadNewAdverts(Response<String> response) {
        if(response != null) {
            try {
                ((HomeActivity)getActivity()).loadNewAdverts();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void readFilterInfoFromForm() {
        Activity activity = getActivity();
        Realm realm = Realm.getInstance(activity);
        realm.beginTransaction();

        filterInfo.setCityId(ObjectsHelper.OptionToIntNoZero(formManager.getResult(CITY)));
        filterInfo.setCarMarkId(ObjectsHelper.OptionToIntNoZero(formManager.getResult(MARK)));
        filterInfo.setCarModelId(ObjectsHelper.OptionToIntNoZero(formManager.getResult(MODEL)));
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
            CarMark carMark = RealmHelper.getCarMarkById(getActivity(), filterInfo.getCarMarkId());
            if(carMark != null) {
                formManager.setResult(MARK, new Option(String.valueOf(carMark.getId()), carMark.getName()));
            }

            CarModel carModel = RealmHelper.getCarModelById(getActivity(), filterInfo.getCarModelId());
            if(carModel != null) {
                formManager.setResult(MODEL, new Option(String.valueOf(carModel.getId()), carModel.getName()));
            }

            City city = RealmHelper.getCityById(getActivity(), filterInfo.getCityId());
            if(city != null) {
                formManager.setResult(CITY, new Option(String.valueOf(city.getId()), city.getName()));
            }

            ActivityHelper.setText(activity, R.id.min_price, ObjectsHelper.floatToStrNoZero(filterInfo.getMinPrice()));
            ActivityHelper.setText(activity, R.id.max_price, ObjectsHelper.floatToStrNoZero(filterInfo.getMaxPrice()));
            ActivityHelper.setText(activity, R.id.min_year, ObjectsHelper.intToStrNoZero(filterInfo.getMinYear()));
            ActivityHelper.setText(activity, R.id.max_year, ObjectsHelper.intToStrNoZero(filterInfo.getMaxYear()));
        }
    }
}
