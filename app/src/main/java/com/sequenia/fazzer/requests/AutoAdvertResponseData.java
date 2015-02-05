package com.sequenia.fazzer.requests;

import com.sequenia.fazzer.adverts.AutoAdvertFullInfo;

/**
 * Created by chybakut2004 on 05.02.15.
 */
public class AutoAdvertResponseData {
    private AutoAdvertFullInfo auto_advert;

    public void setAutoAdvert(AutoAdvertFullInfo auto_advert) {
        this.auto_advert = auto_advert;
    }

    public AutoAdvertFullInfo getAutoAdvert() {
        return auto_advert;
    }
}
