package com.sven.email.setting;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.sven.email.R;

public class DisplaySettingsActivity extends AppCompatActivity {

    private TextView languageSettingButton;
    private Dialog languageModal;
    private RadioButton radioOption1;
    private RadioButton radioOption2;
    private Button btnApply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_settings);
        getSupportActionBar().hide();

        languageSettingButton = findViewById(R.id.languageSettingButton);
        languageSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLanguageModal();
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

    private void showLanguageModal() {
        // Create and show the modal dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.BottomDialogTheme);
        // Inflate the custom layout for the modal
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.modal_lan_setting, null);
        // Set the custom view to the dialog
        builder.setView(view);
        AlertDialog dialog = builder.create();

        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; // Add fade-in animation
        dialog.getWindow().setGravity(Gravity.BOTTOM); // Position the dialog at the bottom
        dialog.show();
    }


}

