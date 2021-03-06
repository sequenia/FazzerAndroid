package com.sequenia.fazzer.forms;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.sequenia.fazzer.R;
import com.sequenia.fazzer.adapters.SelectDialogAdapter;
import com.sequenia.fazzer.helpers.ActivityHelper;
import com.sequenia.fazzer.objects.Option;

import java.util.ArrayList;

/**
 * Created by chybakut2004 on 24.02.15.
 */
public class SelectDialogFragment extends DialogFragment {
    private static final String ARG_TITLE = "DialogTitle";
    private static final String ARG_OPTIONS = "Options";
    public static final String ARG_NAME = "Name";
    public static final String VALUE = "Value";

    private ArrayList<Option> options;
    private ArrayList<Option> shownOptions;
    private SelectDialogAdapter adapter;
    private View view;
    private String name;

    public static SelectDialogFragment newInstance(String title, ArrayList<Option> options, String name) {
        SelectDialogFragment fragment = new SelectDialogFragment();

        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putParcelableArrayList(ARG_OPTIONS, options);
        args.putString(ARG_NAME, name);
        fragment.setArguments(args);

        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();

        name = args.getString(ARG_NAME);
        String title = args.getString(ARG_TITLE);
        if(title == null) {
            title = "";
        }

        initContent();
        initOptions();
        initFilter();

        return new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setView(view)
                .create();
    }

    private void initContent() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.select_dialog_view, null, false);
    }

    private void initOptions() {
        options = getArguments().getParcelableArrayList(ARG_OPTIONS);
        shownOptions = new ArrayList<Option>();
        filterOptions("");
        adapter = new SelectDialogAdapter(getActivity(), 0, shownOptions);

        ListView listView = (ListView) view.findViewById(R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent data = new Intent();
                data.putExtra(VALUE, shownOptions.get(position));
                data.putExtra(ARG_NAME, name);
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, data);
                ActivityHelper.hideKeyboard(getActivity());
                dismiss();
            }
        });
        listView.setAdapter(adapter);
    }

    private void initFilter() {
        final EditText search = (EditText) view.findViewById(R.id.search);
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
