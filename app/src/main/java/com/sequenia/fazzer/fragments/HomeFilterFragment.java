package com.sequenia.fazzer.fragments;

import com.sequenia.fazzer.activities.HomeActivity;
import com.sequenia.fazzer.requests_data.Response;

/**
 * Created by chybakut2004 on 02.03.15.
 */
public class HomeFilterFragment extends FilterFragment {
    @Override
    public void onFilterSave(Response<String> response) {
        if(response != null) {
            try {
                ((HomeActivity)getActivity()).afterFilterSave();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
