package com.sven.email.mailbox;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePartHeader;
import com.sven.email.BaseActivity;
import com.sven.email.R;
import com.sven.email.login.LoginActivity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DraftsActivity extends EmailAPI {
    private Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        getSupportActionBar().hide();
        String query = "in:draft";
        String disFrom = "From";

        categoryText = findViewById(R.id.label);
        emailRecyclerView = findViewById(R.id.emailRecyclerView);
        emptyMailImageView = findViewById(R.id.emptymail);
        composebutton = findViewById(R.id.composebutton);
        inputEditText = findViewById(R.id.filtermail);
        circularImageView = findViewById(R.id.circularImageView);
        loadingScreen = findViewById(R.id.loadingScreen);

        getGmailMessages(0,query, disFrom, context);
        emailRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if(isLastItemVisible(emailLinearLayout)) {
                    index++;
                    if(!endFlag) getGmailMessages(index, mainQuery, displayFrom, context);
                }
            }
        });

        categoryText.setText(R.string.drafts);
        setNavigationClickListener();
        setMenuItemBackground(R.id.toDrafts);
        setNavButtonClickListener();
        setAvataronClickListner();
        setComposebuttonClickListener();
        setEditTextListener();
    }
}