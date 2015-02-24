package com.sequenia.fazzer.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sequenia.fazzer.R;
import com.sequenia.fazzer.activities.AutoAdvertActivity;
import com.sequenia.fazzer.adapters.AutoAdvertsAdapter;
import com.sequenia.fazzer.async_tasks.AutoAdvertsLoader;
import com.sequenia.fazzer.helpers.ActivityHelper;
import com.sequenia.fazzer.helpers.ApiHelper;
import com.sequenia.fazzer.helpers.FazzerHelper;
import com.sequenia.fazzer.requests_data.AutoAdvertMinInfo;
import com.sequenia.fazzer.requests_data.Response;

import java.util.ArrayList;

/**
 * Created by chybakut2004 on 19.02.15.
 */
public class AutoAdvertsFragment extends Fragment {

    ArrayList<AutoAdvertMinInfo> autoAdverts = null;
    AutoAdvertsAdapter adapter = null;
    ListView autoAdvertsListView = null;

    public AutoAdvertsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auto_adverts, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        autoAdverts = new ArrayList<AutoAdvertMinInfo>();
        adapter = new AutoAdvertsAdapter(getActivity(), R.layout.auto_advert_info, autoAdverts);

        initListView();
    }

    private void initListView() {
        autoAdvertsListView = (ListView) getActivity().findViewById (R.id.auto_adverts_list_view);
        autoAdvertsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showAdvert(position);
            }
        });
        autoAdvertsListView.setAdapter(adapter);
    }

    private void showAdvert(int position) {
        Intent intent = new Intent(getActivity(),
                AutoAdvertActivity.class);
        intent.putExtra(FazzerHelper.AUTO_ADVERT_ID, autoAdverts.get(position).getId());
        startActivityForResult(intent, 0);
    }

    @Override
    public void onResume() {
        super.onResume();
        Activity activity = getActivity();
        new AutoAdvertsLoader(activity) {
            @Override
            public void onPostExecuteCustom(ArrayList<AutoAdvertMinInfo> newAdverts) {
                showNewAdverts(newAdverts);
            }
        }.execute();
    }

    public void showNewAdverts(ArrayList<AutoAdvertMinInfo> newAdverts) {
        autoAdverts.clear();
        autoAdverts.addAll(0, newAdverts);
        adapter.notifyDataSetChanged();
    }
}
