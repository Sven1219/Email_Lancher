package com.sven.email.setting;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import com.sven.email.BaseActivity;
import com.sven.email.LanguageActivity;
import com.sven.email.R;
import com.sven.email.database.DatabaseQueryClass;
import com.sven.email.database.DisplayData;
import com.sven.email.mailbox.InboxActivity;
import com.sven.email.util.Config;

import java.util.Locale;

public class DisplaySettingsActivity extends BaseActivity {

    private TextView languageSettingButton;
    private DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(this);
    private Dialog languageModal;
    private RadioButton radioOption1;
    private RadioButton radioOption2;
    private Button btnApply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_settings);
        getSupportActionBar().hide();

        DisplayData displayData = databaseQueryClass.getDisplayData();

        /*---------------add clickListener----------------*/
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
//----------------animation-------------
        CheckBox display_animation_cb = findViewById(R.id.display_animation_cb);

        display_animation_cb.setChecked(displayData.getAnimation() == 1);
        display_animation_cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int val;
                val = displayData.getAnimation() == 1 ? 0 : 1;
                databaseQueryClass.updateDisplayData(Config.COLUMN_DISPLAY_ANIMATION, val);
            }
        });
//----------end_animation-----------------
//----------start show_correspondent------------
        CheckBox display_corre_cb = findViewById(R.id.display_corres_cb);

        display_corre_cb.setChecked(displayData.getCorres() == 1);
        display_corre_cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int val;
                val = displayData.getCorres() == 1 ? 0 : 1;
                databaseQueryClass.updateDisplayData(Config.COLUMN_DISPLAY_SHOW_CORRES_NAMES, val);
            }
        });
//---------------end correspondent--------------
//---------------start colorize_contacts----------------
        CheckBox display_colorize_contacts_cb = findViewById(R.id.display_color_name_cb);

        display_colorize_contacts_cb.setChecked(displayData.getcolorize_contacts() == 1);
        display_colorize_contacts_cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int val;
                val = displayData.getcolorize_contacts() == 1 ? 0 : 1;
                databaseQueryClass.updateDisplayData(Config.COLUMN_DISPLAY_COLORIZE_CONTACTS, val);
            }
        });
//--------------end corloize_contacts--------

//--------------start_show_contact_pictures-------------
        CheckBox display_show_contact_pictures_cb = findViewById(R.id.display_show_contact_cb);

        display_show_contact_pictures_cb.setChecked(displayData.getshow_contact_pictures() == 1);
        display_show_contact_pictures_cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int val;
                val = displayData.getshow_contact_pictures() == 1 ? 0 : 1;
                databaseQueryClass.updateDisplayData(Config.COLUMN_DISPLAY_SHOW_CONTACT_PICTURES, val);
            }
        });
//--------------end_show_contact_pictures-------------
//-----start_colorize_contact_picture--------
        CheckBox display_color_picture_cb = findViewById(R.id.display_color_picture_cb);

        display_color_picture_cb.setChecked(displayData.getcolorize_contact_pictures() == 1);
        display_color_picture_cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int val;
                val = displayData.getcolorize_contact_pictures() == 1 ? 0 : 1;
                databaseQueryClass.updateDisplayData(Config.COLUMN_DISPLAY_COLORIZE_CONTACT_PICTURES, val);
            }
        });
//------end_colorize_contact_picture---------

// -----start_change_color--------
        CheckBox display_change_color_cb = findViewById(R.id.display_change_color_cb);

        display_change_color_cb.setChecked(displayData.getchange_color_when_read() == 1);
        display_change_color_cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int val;
                val = displayData.getchange_color_when_read() == 1 ? 0 : 1;
                databaseQueryClass.updateDisplayData(Config.COLUMN_DISPLAY_CHANGE_COLOR_WHEN_READ, val);
            }
        });
//------end_change_color---------
// -----start_threaded_view--------
        CheckBox display_threaded_view_cb = findViewById(R.id.display_threaded_view_cb);

        display_threaded_view_cb.setChecked(displayData.getthreaded_view() == 1);
        display_threaded_view_cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int val;
                val = displayData.getthreaded_view() == 1 ? 0 : 1;
                databaseQueryClass.updateDisplayData(Config.COLUMN_DISPLAY_THREADED_VIEW, val);
            }
        });
//------end_threaded_view---------
// -----start_show_split--------
        CheckBox display_show_split_cb = findViewById(R.id.display_show_split_cb);

        display_show_split_cb.setChecked(displayData.getshow_split_screen() == 1);
        display_show_split_cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int val;
                val = displayData.getshow_split_screen() == 1 ? 0 : 1;
                databaseQueryClass.updateDisplayData(Config.COLUMN_DISPLAY_SHOW_SPLIT_SCREEN, val);
            }
        });
//------end_show_split---------
// -----start_fixed_width--------
        CheckBox display_fixed_width_cb = findViewById(R.id.display_fixed_width_cb);

        display_fixed_width_cb.setChecked(displayData.getfixed_width_fonts() == 1);
        display_fixed_width_cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int val;
                val = displayData.getfixed_width_fonts() == 1 ? 0 : 1;
                databaseQueryClass.updateDisplayData(Config.COLUMN_DISPLAY_FIXED_WIDTH_FONTS, val);
            }
        });
//------end_fixed_width---------
// -----start_visible_email_cb--------
        CheckBox display_visible_email_cb = findViewById(R.id.display_visible_email_cb);

        display_visible_email_cb.setChecked(displayData.getvisible_email_actions() == 1);
        display_visible_email_cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int val;
                val = displayData.getvisible_email_actions() == 1 ? 0 : 1;
                databaseQueryClass.updateDisplayData(Config.COLUMN_DISPLAY_VISIBLE_EMAIL_ACTIONS, val);
            }
        });
//------end_visible_email_cb---------
// -----auto_fit--------
        CheckBox display_auto_fit_cb = findViewById(R.id.display_auto_fit_cb);

        display_auto_fit_cb.setChecked(displayData.getauto_fit_emails() == 1);
        display_auto_fit_cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int val;
                val = displayData.getauto_fit_emails() == 1 ? 0 : 1;
                databaseQueryClass.updateDisplayData(Config.COLUMN_DISPLAY_AUTO_FIT_EMAILS, val);
            }
        });
//------end_auto_fit---------
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
        DisplayData displayData = databaseQueryClass.getDisplayData();

        RadioButton defaultsys = dialog.findViewById(R.id.defaultsys);
        RadioButton spain = dialog.findViewById(R.id.spain);
        RadioButton franch = dialog.findViewById(R.id.franch);
        RadioButton german = dialog.findViewById(R.id.german);
        RadioButton italian = dialog.findViewById(R.id.italian);
        RadioButton portugues = dialog.findViewById(R.id.portugues);
        RadioButton ko = dialog.findViewById(R.id.ko);

        String lan = displayData.getLang();
        DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(this);
        switch(lan){
            case "en" : defaultsys.setChecked(true);
                        break;
            case "es" : spain.setChecked(true);
                        break;
            case "fr" : franch.setChecked(true);
                break;
            case "de" : german.setChecked(true);
                break;
            case "it" : italian.setChecked(true);
                break;
            case "pt" : portugues.setChecked(true);
                break;
            case "ko" : ko.setChecked(true);
                break;
            default: break;
        }
        LanguageActivity languageActivity = new LanguageActivity();

        defaultsys.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    databaseQueryClass.updateLanguage("en");
                    setLocale("en");
                    dialog.hide();
                } else {
                    // The RadioButton is not selected
                }
            }
        });
        spain.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    databaseQueryClass.updateLanguage("es");
                    setLocale("es");
                    dialog.hide();
                } else {
                    // The RadioButton is not selected
                }
            }
        });
        franch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    databaseQueryClass.updateLanguage("fr");
                    setLocale("fr");
                    dialog.hide();
                } else {
                    // The RadioButton is not selected
                }
            }
        });
        german.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    databaseQueryClass.updateLanguage("de");
                    setLocale("de");
                    dialog.hide();
                } else {
                    // The RadioButton is not selected
                }
            }
        });
        italian.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    databaseQueryClass.updateLanguage("it");
                    setLocale("it");
                    dialog.hide();
                } else {
                    // The RadioButton is not selected
                }
            }
        });
        portugues.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    databaseQueryClass.updateLanguage("pt");
                    setLocale("pt");
                    dialog.hide();
                } else {
                    // The RadioButton is not selected
                }
            }
        });
        ko.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    databaseQueryClass.updateLanguage("ko");
                    setLocale("ko");
                    dialog.hide();
                } else {
                    // The RadioButton is not selected
                }
            }
        });

    }
    public void setLocale(String languageCode) {
        Configuration configuration = new Configuration();
        configuration.setLocale(new Locale(languageCode));
        Resources resources = getResources();
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        recreate();
    }


}

