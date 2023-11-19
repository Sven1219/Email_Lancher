package com.sven.email.setting;

import android.os.Bundle;
import android.view.View;

import com.sven.email.BaseActivity;
import com.sven.email.R;

public class AccountNotificationActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_notification);
        getSupportActionBar().hide();

        View backbutton = findViewById(R.id.backbutton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
