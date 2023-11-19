package com.sven.email.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.sven.email.BaseActivity;
import com.sven.email.R;

public class AccountActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_settings);
        getSupportActionBar().hide();

        View backbutton = findViewById(R.id.backbutton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        LinearLayout fetchingmail = findViewById(R.id.fetchingmail);
        fetchingmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountActivity.this, FetchingmailActivity.class));
            }
        });

        LinearLayout sendingmail = findViewById(R.id.sendingmail);
        sendingmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountActivity.this, SendingmailActivity.class));
            }
        });

        LinearLayout account_notification = findViewById(R.id.account_notification);
        account_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountActivity.this, AccountNotificationActivity.class));
            }
        });
    }
}
