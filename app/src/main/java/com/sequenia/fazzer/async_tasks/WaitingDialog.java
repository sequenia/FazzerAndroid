package com.sequenia.fazzer.async_tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Created by chybakut2004 on 09.02.15.
 */
public class WaitingDialog<T1, T2> extends AsyncTask<T1, String, T2> {

    private ProgressDialog pd = null;
    private Context context = null;
    private String startMessage = null;

    public WaitingDialog(Context context) {
        this.context = context;
        createProgressDialog();
    }

    public WaitingDialog(Context context, String startMessage) {
        this.startMessage = startMessage;
        this.context = context;
        createProgressDialog();
    }

    private void createProgressDialog() {
        String m = null;
        if(this.startMessage == null) {
            m = "Пожалуйста, подождите";
        } else {
            m = this.startMessage;
        }
        pd = new ProgressDialog(context);
        pd.setMessage(m);
        pd.setCancelable(false);
    }

    public void setMessage(String s) {
        publishProgress(s);
    }



    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        pd.setMessage(values[0]);

        if(values[1] != null) {
            Toast.makeText(context, values[1], Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected T2 doInBackground(T1... params) {
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd.show();
    }

    @Override
    protected void onPostExecute(T2 t3) {
        super.onPostExecute(t3);
        if(pd.isShowing()) {
            pd.dismiss();
        }
    }

    public Context getContext() {
        return context;
    }
}
