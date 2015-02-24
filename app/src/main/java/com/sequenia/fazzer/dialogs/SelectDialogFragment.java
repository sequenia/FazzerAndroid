package com.sequenia.fazzer.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.sequenia.fazzer.R;
import com.sequenia.fazzer.adapters.SelectDialogAdapter;
import com.sequenia.fazzer.objects.Option;

import java.util.ArrayList;

/**
 * Created by chybakut2004 on 24.02.15.
 */
public class SelectDialogFragment extends DialogFragment {
    private static final String ARG_TITLE = "DialogTitle";
    private static final String ARG_OPTIONS = "Options";

    private ArrayList<Option> options;
    private ArrayList<Option> shownOptions;
    private SelectDialogAdapter adapter;
    private View searchView;

    public static SelectDialogFragment newInstance(String title, ArrayList<Option> options) {
        SelectDialogFragment fragment = new SelectDialogFragment();

        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putParcelableArrayList(ARG_OPTIONS, options);
        fragment.setArguments(args);

        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();

        String title = args.getString(ARG_TITLE);
        if(title == null) {
            title = "";
        }

        initOptions();
        initSearch();

        return new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setView(searchView)
                .create();
    }

    private void initOptions() {
        options = getArguments().getParcelableArrayList(ARG_OPTIONS);
        shownOptions = new ArrayList<Option>();
        filterOptions("");
        adapter = new SelectDialogAdapter(getActivity(), 0, shownOptions);
    }

    private void initSearch() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        searchView = inflater.inflate(R.layout.select_dialog_search, null, false);
        final EditText search = (EditText) searchView.findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterOptions(s.toString());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void filterOptions(String s) {
        shownOptions.clear();

        String searchText = s.toString().toLowerCase();

        for(Option option : options) {
            String label = option.getLabel().toLowerCase();
            if (label.contains(searchText)) {
                shownOptions.add(option);
            }
        }
    }
}
