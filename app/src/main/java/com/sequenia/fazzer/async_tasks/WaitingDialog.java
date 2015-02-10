package com.sequenia.fazzer.async_tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by chybakut2004 on 09.02.15.
 */
public class WaitingDialog<T1, T2, T3> extends AsyncTask<T1, T2, T3> {

    private ProgressDialog pd = null;
    private Context context = null;
    private String message = null;

    public WaitingDialog(Context context) {
        this.context = context;
        createProgressDialog();
    }

    public WaitingDialog setMessage(String message) {
        this.message = message;
        return this;
    }

    private void createProgressDialog() {
        String m = null;
        if(this.message == null) {
            m = "Пожалуйста, подождите";
        } else {
            m = this.message;
        }
        pd = new ProgressDialog(context);
        pd.setMessage(m);
        pd.setCancelable(false);
    }

    @Override
    protected T3 doInBackground(T1... params) {
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd.show();
    }

    @Override
    protected void onPostExecute(T3 t3) {
        super.onPostExecute(t3);
        pd.dismiss();
    }

    public Context getContext() {
        return context;
    }
}
