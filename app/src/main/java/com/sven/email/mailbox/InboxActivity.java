package com.sven.email.mailbox;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.sven.email.R;

public class InboxActivity extends EmailAPI {
    private Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        getSupportActionBar().hide();
        String query = "in:inbox";
        String disFrom = "From";

        categoryText = findViewById(R.id.label);
        emailRecyclerView = findViewById(R.id.emailRecyclerView);
        emptyMailImageView = findViewById(R.id.emptymail);
        composebutton = findViewById(R.id.composebutton);
        inputEditText = findViewById(R.id.filtermail);
        circularImageView = findViewById(R.id.circularImageView);
        loadingScreen = findViewById(R.id.loadingScreen);

        getGmailMessages(0,query, disFrom,context);
        emailRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if(isLastItemVisible(emailLinearLayout)) {
                    index++;
                    if(!endFlag) getGmailMessages(index, mainQuery, displayFrom, context);
                    else return;
//                    emailRecyclerView.setVisibility(View.GONE);
                }
            }
        });

        categoryText.setText(R.string.inbox);
        setNavigationClickListener();
        setMenuItemBackground(R.id.toInbox);
        setNavButtonClickListener();
        setAvataronClickListner();
        setComposebuttonClickListener();
        setEditTextListener();
    }
}