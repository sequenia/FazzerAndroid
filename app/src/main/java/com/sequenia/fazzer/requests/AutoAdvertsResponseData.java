package com.sequenia.fazzer.requests;

import com.sequenia.fazzer.adverts.AutoAdvertMinInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chybakut2004 on 04.02.15.
 */
public class AutoAdvertsResponseData {
    private List<AutoAdvertMinInfo> auto_adverts;

    public AutoAdvertsResponseData() {
        auto_adverts = new ArrayList<AutoAdvertMinInfo>();
    }

    public List<AutoAdvertMinInfo> getAutoAdverts() {
        return auto_adverts;
    }

    public void addAutoAdvert(AutoAdvertMinInfo autoAdvert) {
        this.auto_adverts.add(autoAdvert);
    }
}
