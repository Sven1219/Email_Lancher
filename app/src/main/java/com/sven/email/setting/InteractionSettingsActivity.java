package com.sven.email.setting;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.sven.email.R;

public class InteractionSettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interaction_settings);
        getSupportActionBar().hide();

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
