package com.sven.email.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.sven.email.BaseActivity;
import com.sven.email.R;
import com.sven.email.login.LoginActivity;

public class SettingsActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        getSupportActionBar().hide();

        LinearLayout general_setting_list = findViewById(R.id.general_setting_list);
        general_setting_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, GeneralSettingsActivity.class);
                startActivity(intent);
            }
        });

        View backbutton = findViewById(R.id.backbutton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        LinearLayout accountbutton = findViewById(R.id.account);
        accountbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, AccountActivity.class);
                startActivity(intent);
            }
        });
        GoogleSignInAccount account = LoginActivity.getAccount();
        TextView setting_email = findViewById(R.id.setting_email);
        setting_email.setText(account.getEmail());
    }
}
