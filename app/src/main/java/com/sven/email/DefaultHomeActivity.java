package com.sven.email;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.OpenableColumns;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;

import com.sven.email.customLauncher.HomeScreenFragment;
import com.sven.email.login.LoginActivity;

import java.util.Locale;

public class DefaultHomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.default_home);
        getSupportActionBar().hide();

        RadioGroup radioGroup = findViewById(R.id.launcerchoice);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.emailRadiobutton:
                        // Navigate to Screen 1
                        Intent intent1 = new Intent(DefaultHomeActivity.this, LoginActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.systemRadiobutton:
                        // Navigate to Screen 2
                        Intent intent2 = new Intent(DefaultHomeActivity.this, CustomActivity.class);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent2);
                        break;
                }
            }
        });
    }
}
