
package com.sven.email.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sven.email.BaseActivity;
import com.sven.email.R;
import com.sven.email.database.DatabaseQueryClass;
import com.sven.email.database.InteractionData;
import com.sven.email.database.NotificationData;
import com.sven.email.util.Config;

public class NotificationSettingsActivity extends BaseActivity {
    private DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_settings);
        getSupportActionBar().hide();

        NotificationData notificationData = databaseQueryClass.getNotificationData();

        View backbutton = findViewById(R.id.backbutton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        CheckBox noti_time = findViewById(R.id.noti_time);

        noti_time.setChecked(notificationData.getquiet_time() == 1);
        noti_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int val;
                val = notificationData.getquiet_time() == 1 ? 0 : 1;
                databaseQueryClass.updateNotificationData(Config.COLUMN_NOTIFICATION_QUIET_TIME, val);
            }
        });

        CheckBox noti_dis = findViewById(R.id.noti_dis);

        noti_dis.setChecked(notificationData.getdesible_notifications() == 1);
        noti_dis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int val;
                val = notificationData.getdesible_notifications() == 1 ? 0 : 1;
                databaseQueryClass.updateNotificationData(Config.COLUMN_NOTIFICATION_DISIBLE_NOTIFICATIONS, val);
            }
        });
    }

}
