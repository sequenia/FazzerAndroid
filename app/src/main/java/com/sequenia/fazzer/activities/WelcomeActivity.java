package com.sequenia.fazzer.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;

import com.sequenia.fazzer.R;
import com.sequenia.fazzer.helpers.FazzerHelper;


public class WelcomeActivity extends ActionBarActivity {

    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mPreferences = getSharedPreferences(FazzerHelper.CURRENT_USER_PREFERENCES, MODE_PRIVATE);

        findViewById(R.id.registerButton).setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // No account, load new account view
                    Intent intent = new Intent(WelcomeActivity.this,
                            RegisterActivity.class);
                    startActivityForResult(intent, 0);
                }
            });

        findViewById(R.id.loginButton).setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Existing Account, load login view
                    Intent intent = new Intent(WelcomeActivity.this,
                            LoginActivity.class);
                    startActivityForResult(intent, 0);
                }
            });
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mPreferences.contains(FazzerHelper.AUTH_TOKEN)) {
            finish();
        }
    }
}
