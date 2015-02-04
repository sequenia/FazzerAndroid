package com.sequenia.fazzer;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sequenia.fazzer.async_tasks.LoginTask;
import com.sequenia.fazzer.async_tasks.RegisterTask;


public class RegisterActivity extends ActionBarActivity {

    private final static String REGISTER_API_ENDPOINT_URL = "http://178.62.184.226/api/v1/registrations";
    private SharedPreferences mPreferences;
    private String mUserPhone;
    private String mUserPassword;
    private String mUserPasswordConfirmation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mPreferences = getSharedPreferences(HomeActivity.CURRENT_USER_PREFERENCES, MODE_PRIVATE);

        Button loginButton = (Button) findViewById(R.id.registerButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewAccount(v);
            }
        });
    }

    public void registerNewAccount(View button) {
        EditText userPhoneField = (EditText) findViewById(R.id.userPhone);
        mUserPhone = userPhoneField.getText().toString();
        EditText userPasswordField = (EditText) findViewById(R.id.userPassword);
        mUserPassword = userPasswordField.getText().toString();
        EditText userPasswordConfirmationField = (EditText) findViewById(R.id.userPasswordConfirmation);
        mUserPasswordConfirmation = userPasswordConfirmationField.getText().toString();

        if (mUserPhone.length() == 0 || mUserPassword.length() == 0 || mUserPasswordConfirmation.length() == 0) {
            // input fields are empty
            Toast.makeText(this, "Please complete all the fields",
                    Toast.LENGTH_LONG).show();
            return;
        } else {
            if (!mUserPassword.equals(mUserPasswordConfirmation)) {
                // password doesn't match confirmation
                Toast.makeText(this, "Your password doesn't match confirmation, check again",
                        Toast.LENGTH_LONG).show();
                return;
            } else {
                // everything is ok!

                RegisterTask registerTask = new RegisterTask(mUserPhone, mUserPassword, mUserPasswordConfirmation, mPreferences, this);
                registerTask.execute(REGISTER_API_ENDPOINT_URL);
            }
        }
    }

}
