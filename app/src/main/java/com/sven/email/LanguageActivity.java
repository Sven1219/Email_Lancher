package com.sven.email;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.sven.email.mailbox.InboxActivity;

import java.util.Locale;

public class LanguageActivity extends AppCompatActivity {

    private void setAppLanguage(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);

        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        // Restart the activity to apply the language change
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.language);
        getSupportActionBar().hide();

        RadioGroup languageRadioGroup = findViewById(R.id.lanGroup);
        languageRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Intent intent = new Intent(LanguageActivity.this, InboxActivity.class);

                switch (checkedId) {
                    case R.id.en_option:
                        setAppLanguage("en");
                        startActivity(intent);
                        break;
                    case R.id.es_option:
                        setAppLanguage("es");
                        startActivity(intent);
                        break;
                    case R.id.fr_option:
                        setAppLanguage("fr");
                        startActivity(intent);
                        break;
                    case R.id.de_option:
                        setAppLanguage("de");
                        startActivity(intent);
                        break;
                    case R.id.it_option:
                        setAppLanguage("it");
                        startActivity(intent);
                        break;
                    case R.id.po_option:
                        setAppLanguage("pt");
                        startActivity(intent);
                        break;
                    case R.id.ko_option:
                        setAppLanguage("ko");
                        startActivity(intent);
                        break;
                }
            }
        });
    }
}
