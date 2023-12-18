package com.sven.email.setting;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.sven.email.BaseActivity;
import com.sven.email.R;
import com.sven.email.database.DatabaseQueryClass;
import com.sven.email.database.DisplayData;
import com.sven.email.database.InteractionData;
import com.sven.email.util.Config;

public class InteractionSettingsActivity extends BaseActivity {
    private TextView languageSettingButton;
    private DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(this);
    private Dialog languageModal;
    private RadioButton radioOption1;
    private RadioButton radioOption2;
    private Button btnApply;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interaction_settings);
        getSupportActionBar().hide();

        InteractionData interactionData = databaseQueryClass.getInteractionData();


        View backbutton = findViewById(R.id.backbutton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        LinearLayout confirmActionButton = findViewById(R.id.confirm_action);
        confirmActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmModal();
            }
        });

        CheckBox interaction_volume = findViewById(R.id.interaction_volume);

        interaction_volume.setChecked(interactionData.getvolume() == 1);
        interaction_volume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int val;
                val = interactionData.getvolume() == 1 ? 0 : 1;
                databaseQueryClass.updateInteractionData(Config.COLUMN_INTERACTION_VOLUME_KEY, val);
            }
        });

        CheckBox interaction_return = findViewById(R.id.interaction_return);

        interaction_return.setChecked(interactionData.getreturn_list() == 1);
        interaction_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int val;
                val = interactionData.getreturn_list() == 1 ? 0 : 1;
                databaseQueryClass.updateInteractionData(Config.COLUMN_INTERACTION_RETURN_TO_LIST, val);
            }
        });

        CheckBox interaction_next = findViewById(R.id.interaction_next);

        interaction_next.setChecked(interactionData.getshow_next() == 1);
        interaction_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int val;
                val = interactionData.getshow_next() == 1 ? 0 : 1;
                databaseQueryClass.updateInteractionData(Config.COLUMN_INTERACTION_SHOW_NEXT_EMAIL, val);
            }
        });
    }

    private void showConfirmModal() {
        // Create and show the modal dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.BottomDialogTheme);
        // Inflate the custom layout for the modal
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.modal_confirm_action, null);
        // Set the custom view to the dialog
        builder.setView(view);
        AlertDialog dialog = builder.create();

        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; // Add fade-in animation
        dialog.getWindow().setGravity(Gravity.BOTTOM); // Position the dialog at the bottom
        dialog.show();
    }
}
