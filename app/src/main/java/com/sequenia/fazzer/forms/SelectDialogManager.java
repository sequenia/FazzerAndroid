package com.sequenia.fazzer.forms;

import com.sequenia.fazzer.objects.Option;

import java.util.ArrayList;

/**
 * Created by chybakut2004 on 25.02.15.
 */
public abstract class SelectDialogManager {
    public abstract ArrayList<Option> getList();

    public void onSelect() {

    }
}
