package com.sequenia.fazzer.forms;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sequenia.fazzer.objects.Option;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by chybakut2004 on 25.02.15.
 */
public class FormManager {
    public static final int SELECT_DIALOG_REQUEST_CODE = 1;
    private static final String DIALOG_TAG = "Dialog";

    private HashMap<String, View> selects;
    private HashMap<String, Option> selectResults;
    private Fragment fragment;

    public FormManager(Fragment fragment) {
        this.fragment = fragment;
        selects = new HashMap<String, View>();
        selectResults = new HashMap<String, Option>();
    }

    public void addSelect(TextView view, ImageButton clearButton, final String name, final String title, final SelectDialogListManager listManager) {
        final FragmentActivity activity = fragment.getActivity();
        view.setFocusable(false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Option> options = listManager.getList();
                SelectDialogFragment dialog = SelectDialogFragment.newInstance(title, options, name);
                dialog.setTargetFragment(fragment, SELECT_DIALOG_REQUEST_CODE);
                dialog.show(activity.getSupportFragmentManager(), DIALOG_TAG);
            }
        });
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectResults.remove(name);
                ((TextView) selects.get(name)).setText("");
            }
        });
        selects.put(name, view);
    }

    public void setResult(String name, Option option) {
        selectResults.put(name, option);
        TextView textView = (TextView) selects.get(name);
        if(textView != null && option != null) {
            textView.setText(option.getLabel());
        }
    }

    public Option getResult(String name) {
        return selectResults.get(name);
    }
}
