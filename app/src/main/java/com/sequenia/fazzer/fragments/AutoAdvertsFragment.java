package com.sequenia.fazzer.fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sequenia.fazzer.R;
import com.sequenia.fazzer.activities.HomeActivity;
import com.sequenia.fazzer.adapters.AutoAdvertsAdapter;
import com.sequenia.fazzer.async_tasks.AutoAdvertsLoader;
import com.sequenia.fazzer.helpers.ActivityHelper;
import com.sequenia.fazzer.helpers.FazzerHelper;
import com.sequenia.fazzer.helpers.RealmHelper;
import com.sequenia.fazzer.objects.AutoAdvertMinInfo;
import com.sequenia.fazzer.requests_data.Response;

import java.util.ArrayList;

/**
 * Created by chybakut2004 on 19.02.15.
 */
public class AutoAdvertsFragment extends Fragment {

    ArrayList<AutoAdvertMinInfo> autoAdverts = null;
    AutoAdvertsAdapter adapter = null;
    ListView autoAdvertsListView = null;
    View progressBar = null;

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

        initProgressBar();
        autoAdvertsListView = (ListView) getActivity().findViewById (R.id.auto_adverts_list_view);

        autoAdverts = RealmHelper.toArrayList(AutoAdvertMinInfo.class, RealmHelper.getAllAutoAdvertMinInfos(getActivity()));
        if(autoAdverts.size() == 0) {
            loadNewAdverts();
        }
        adapter = new AutoAdvertsAdapter(getActivity(), R.layout.auto_advert_info, autoAdverts);

        initListView();
    }

    private void initListView() {
        autoAdvertsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showAdvert(position);
            }
        });
        autoAdvertsListView.setAdapter(adapter);
    }

    private void initProgressBar() {
        progressBar = getActivity().findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
    }

    private void showAdvert(int position) {
        ActivityHelper.showAutoAdvertActivity(getActivity(), autoAdverts.get(position).getId());
    }

    public void loadNewAdverts() {
        progressBar.setVisibility(View.VISIBLE);
        autoAdvertsListView.setVisibility(View.GONE);
        new AutoAdvertsLoader(getActivity()) {
            @Override
            public void onPostExecuteCustom(Response<ArrayList<AutoAdvertMinInfo>> response) {
                progressBar.setVisibility(View.GONE);
                autoAdvertsListView.setVisibility(View.VISIBLE);
                if(response != null) {
                    if(response.getSuccess()) {
                        ArrayList<AutoAdvertMinInfo> newAdverts = response.getData();
                        showNewAdverts(newAdverts);
                    } else {
                        Toast.makeText(getActivity(), response.getInfo(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }.execute();
    }

    public void showNewAdverts(ArrayList<AutoAdvertMinInfo> newAdverts) {
        Activity activity = getActivity();

        RealmHelper.deleteAllAutoAdvertMinInfos(activity);

        autoAdverts.clear();
        autoAdverts.addAll(0, newAdverts);

        RealmHelper.saveAutoAdvertMinInfos(activity, newAdverts);

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences pref = FazzerHelper.getUserPreferences(getActivity());
        if(pref.getBoolean(HomeActivity.NEEDS_UPDATE_PREF, false)) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean(HomeActivity.NEEDS_UPDATE_PREF, false);
            editor.commit();
            loadNewAdverts();
        }
    }
}
