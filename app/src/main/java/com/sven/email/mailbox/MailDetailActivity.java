package com.sven.email.mailbox;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.ModifyMessageRequest;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.sven.email.BaseActivity;
import com.sven.email.R;
import com.sven.email.login.LoginActivity;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class MailDetailActivity extends BaseActivity  {
    private String message_id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mail_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        Intent intent = getIntent();
        String from = intent.getStringExtra("from");
        String to = intent.getStringExtra("to");
        String subject = intent.getStringExtra("subject");
        String emailTime = intent.getStringExtra("emailTime");
        String text = intent.getStringExtra("text");
        String imageUrl = intent.getStringExtra("imageUrl");
        String body = intent.getStringExtra("body");
        message_id = intent.getStringExtra("id");

        TextView detailSubject = findViewById(R.id.detailSubject);
        TextView detailFrom = findViewById(R.id.detailFrom);
        TextView detailTo = findViewById(R.id.detailTo);
        TextView detailTime = findViewById(R.id.detailTime);
        CircleImageView detailImage = findViewById(R.id.detailImage);
        TextView detailContentFrom = findViewById(R.id.detailContentFrom);
        TextView detailContentSubject = findViewById(R.id.detailContentSubject);
        CircleImageView detailContentImage = findViewById(R.id.detailContentImage);
        TextView detailContentTo = findViewById(R.id.detailContentTo);
        WebView detailContent = findViewById(R.id.detailContent);

        // Process email data for display in WebView
        String emailContent = "<html><body>" +
                "<div>" + body + "</div>" +
                "</body></html>";

        detailSubject.setText(subject);
        detailFrom.setText(to);
        detailTo.setText("To:  " + from);
        detailTime.setText(emailTime);
        Picasso.get().load(imageUrl).into(detailImage);
        detailContent.loadData(emailContent, "text/html", "UTF-8");
        detailContentFrom.setText(from);
        Picasso.get().load(imageUrl).into(detailContentImage);
        detailContentTo.setText(to);
        detailContentSubject.setText(subject);

        Log.d("asdfasdf",intent.getStringExtra("id"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.detailArchive:
                onArchive();
                return true;
            case R.id.detail_delete:
                onDelete();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void onArchive() {
        GoogleSignInAccount account = LoginActivity.getAccount();
        if (account != null) {
            GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(
                    this, Arrays.asList(SCOPES));
            credential.setSelectedAccount(account.getAccount());

            mGmailService = new Gmail.Builder(
                    AndroidHttp.newCompatibleTransport(),
                    new GsonFactory(),
                    credential)
                    .setApplicationName("Email Launcher")
                    .build();
            mainThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        List<String> labelsToAdd = Collections.singletonList("CATEGORY_PERSONAL");  // Add the appropriate label for archiving
                        List<String> labelsToRemove = Collections.singletonList("INBOX");
                        ModifyMessageRequest modifyRequest = new ModifyMessageRequest().setAddLabelIds(labelsToAdd).setRemoveLabelIds(labelsToRemove);

                        Message modifiedMessage = mGmailService.users().messages().modify("me", message_id, modifyRequest).execute();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Email archieved successfully", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (UserRecoverableAuthIOException e) {
                        startActivityForResult(e.getIntent(), REQUEST_AUTHORIZATION);
                        Log.d("FetchMessageError", e.toString());
                        // Handle error while fetching or processing messages
                    } catch (IOException e) {
                        Log.e("TAG", "Error retrieving inbox data: " + e.getMessage());
                    }
                }
            });

            mainThread.start();

        }
    }

    public void onDelete() {
        GoogleSignInAccount account = LoginActivity.getAccount();
        if (account != null) {
            GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(
                    this, Arrays.asList(SCOPES));
            credential.setSelectedAccount(account.getAccount());

            mGmailService = new Gmail.Builder(
                    AndroidHttp.newCompatibleTransport(),
                    new GsonFactory(),
                    credential)
                    .setApplicationName("Email Launcher")
                    .build();
            mainThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        mGmailService.users().messages().delete("me", message_id).execute();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Email deleted successfully", Toast.LENGTH_SHORT).show();
                            }
                        });

                    } catch (UserRecoverableAuthIOException e) {
                        startActivityForResult(e.getIntent(), REQUEST_AUTHORIZATION);
                        Log.d("FetchMessageError", e.toString());
                        // Handle error while fetching or processing messages
                    } catch (IOException e) {
                        Log.e("TAG", "Error retrieving inbox data: " + e.getMessage());
                    }
                }
            });

            mainThread.start();

        }
    }


}
