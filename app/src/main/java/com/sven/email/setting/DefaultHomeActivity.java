package com.sven.email.setting;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.sven.email.MainScreenActivity;
import com.sven.email.R;
import com.sven.email.login.LoginActivity;

public class DefaultHomeActivity extends AppCompatActivity {

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
                        Intent intent2 = new Intent(DefaultHomeActivity.this, MainScreenActivity.class);
                        startActivity(intent2);
                        break;
                }
            }
        });
    }
}
