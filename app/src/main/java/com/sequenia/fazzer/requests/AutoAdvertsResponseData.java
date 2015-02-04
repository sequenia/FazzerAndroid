package com.sequenia.fazzer.requests;

import com.sequenia.fazzer.adverts.AutoAdvert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chybakut2004 on 04.02.15.
 */
public class AutoAdvertsResponseData {
    private List<AutoAdvert> auto_adverts;

    public AutoAdvertsResponseData() {
        auto_adverts = new ArrayList<AutoAdvert>();
    }

    public List<AutoAdvert> getAutoAdverts() {
        return auto_adverts;
    }

    public void addAutoAdvert(AutoAdvert autoAdvert) {
        this.auto_adverts.add(autoAdvert);
    }
}
