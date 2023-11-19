package com.sven.email.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sven.email.R;

public class GeneralSettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_settings);
        getSupportActionBar().hide();

        LinearLayout display_setting_list = findViewById(R.id.display_setting_list);
        display_setting_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GeneralSettingsActivity.this, DisplaySettingsActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout interaction_setting_list = findViewById(R.id.interaction_setting_list);
        interaction_setting_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GeneralSettingsActivity.this, InteractionSettingsActivity.class);
                startActivity(intent);
            }
        });


        LinearLayout notification_setting_list = findViewById(R.id.notification_setting_list);
        notification_setting_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GeneralSettingsActivity.this, NotificationSettingsActivity.class);
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
    }
}
