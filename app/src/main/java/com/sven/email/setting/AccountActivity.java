package com.sven.email.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.sven.email.BaseActivity;
import com.sven.email.R;
import com.sven.email.login.LoginActivity;

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

        GoogleSignInAccount account = LoginActivity.getAccount();
        TextView account_email = findViewById(R.id.account_email);
        account_email.setText(account.getEmail());
    }
}
