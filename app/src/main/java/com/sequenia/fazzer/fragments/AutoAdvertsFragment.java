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
import com.sequenia.fazzer.helpers.ApiHelper;
import com.sequenia.fazzer.helpers.FazzerHelper;
import com.sequenia.fazzer.helpers.RealmHelper;
import com.sequenia.fazzer.listeners.EndlessScrollListener;
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

        Activity activity = getActivity();

        initProgressBar();

        autoAdvertsListView = (ListView) activity.findViewById (R.id.auto_adverts_list_view);
        autoAdverts = RealmHelper.toArrayList(AutoAdvertMinInfo.class, RealmHelper.getAllAutoAdvertMinInfos(getActivity()));
        adapter = new AutoAdvertsAdapter(activity, R.layout.auto_advert_info, autoAdverts);

        autoAdvertsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showAdvert(position);
            }
        });
        setEndlessScrollListener(activity);
        autoAdvertsListView.setAdapter(adapter);

        if(autoAdverts.size() == 0) {
            reloadAdverts();
        }
    }

    private void setEndlessScrollListener(Activity activity) {
        autoAdvertsListView.setOnScrollListener(new EndlessScrollListener(activity) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                loadAdverts(page * ApiHelper.ADVERTS_LIMIT);
            }
        });
    }

    private void initProgressBar() {
        progressBar = getActivity().findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
    }

    private void showAdvert(int position) {
        ActivityHelper.showAutoAdvertActivity(getActivity(), autoAdverts.get(position).getId());
    }

    public void loadAdverts(int offset) {
        new AutoAdvertsLoader(getActivity()) {
            @Override
            public void onPostExecuteCustom(Response<ArrayList<AutoAdvertMinInfo>> response) {
                if(response != null) {
                    if(response.getSuccess()) {
                        ArrayList<AutoAdvertMinInfo> newAdverts = response.getData();
                        autoAdverts.addAll(newAdverts);
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getActivity(), response.getInfo(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }.execute(offset, ApiHelper.ADVERTS_LIMIT);
    }


    public void reloadAdverts() {
        final Activity activity = getActivity();

        progressBar.setVisibility(View.VISIBLE);
        autoAdvertsListView.setVisibility(View.GONE);

        new AutoAdvertsLoader(activity) {
            @Override
            public void onPostExecuteCustom(Response<ArrayList<AutoAdvertMinInfo>> response) {
                progressBar.setVisibility(View.GONE);
                autoAdvertsListView.setVisibility(View.VISIBLE);

                if(response != null) {
                    if(response.getSuccess()) {
                        ArrayList<AutoAdvertMinInfo> newAdverts = response.getData();

                        RealmHelper.deleteAllAutoAdvertMinInfos(activity);
                        RealmHelper.saveAutoAdvertMinInfos(activity, newAdverts);
                        autoAdverts.clear();
                        autoAdverts.addAll(newAdverts);

                        setEndlessScrollListener(activity);
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getActivity(), response.getInfo(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }.execute(0, ApiHelper.ADVERTS_LIMIT);
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences pref = FazzerHelper.getUserPreferences(getActivity());
        if(pref.getBoolean(FazzerHelper.NEEDS_UPDATE_PREF, false)) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean(FazzerHelper.NEEDS_UPDATE_PREF, false);
            editor.commit();
            reloadAdverts();
        }
    }
}
