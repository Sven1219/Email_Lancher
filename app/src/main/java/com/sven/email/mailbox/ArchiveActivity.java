package com.sven.email.mailbox;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
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
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ArchiveActivity extends BaseActivity {

    private static final int REQUEST_AUTHORIZATION = 1001;
    private Gmail mGmailService;
    private List<Email> emailList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        getSupportActionBar().hide();

        categoryText = findViewById(R.id.label);
        emailRecyclerView = findViewById(R.id.emailRecyclerView);
        emptyMailImageView = findViewById(R.id.emptymail);
        composebutton = findViewById(R.id.composebutton);
        inputEditText = findViewById(R.id.filtermail);
        circularImageView = findViewById(R.id.circularImageView);

        String category = getString(R.string.archive);
        getGmailArchiveMessages();
        List<Email> emailList = new ArrayList<>();

        categoryText.setText(category);
        setMenuItemBackground(R.id.toArchive);
        setNavigationClickListener();
        setNavButtonClickListener();
        setComposebuttonClickListener();
        setEditTextListener();
        setAvataronClickListner();
    }

    public void getGmailArchiveMessages() {

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
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String query = "in:archive";
                        ListMessagesResponse response = mGmailService.users().messages().list("me").setQ(query).execute();
                        List<Message> messages = response.getMessages();
                        List<MailboxData> mailboxDataList = new ArrayList<>();

                        // Process the fetched messages and extract sender, subject, and content
                        if(messages != null) {
                            for (Message message : messages) {
                                String messageId = message.getId();
                                Message fetchedMessage = mGmailService.users().messages().get("me", messageId).execute();
                                // Retrieve message details such as sender, subject, and content
                                String from = "";
                                String subject = "";
                                String image = "";
                                String time = "";
                                String content = fetchedMessage.getSnippet();
                                List<MessagePartHeader> headers = fetchedMessage.getPayload().getHeaders();
                                if (headers != null) {
                                    for (MessagePartHeader header : headers) {
                                        if (header.getName().equals("From")) {
                                            String fullFrom = header.getValue();
                                            if(fullFrom.indexOf('<') - 1 > 0) from = fullFrom.substring(0, fullFrom.indexOf('<') - 1);
                                            else from = fullFrom;
                                            image = "https://www.gravatar.com/avatar/" + MD5Util.md5Hex(from.toLowerCase().trim()) + "?d=identicon";
                                        } else if (header.getName().equals("Subject")) {
                                            subject = header.getValue();
                                        }
                                    }
                                }

                                long timestamp = fetchedMessage.getInternalDate();
                                time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date(timestamp));

                                // Now, you can use 'from', 'subject', and 'snippet' to display the message details in the app's UI.
                                Log.d("GmailInboxActivity", "From: " + from + ", Subject: " + subject + ", content: " + content+  ", image: " + image + ", time: " + time);
                                MailboxData inboxData = new MailboxData(from, subject, content, image, time);
                                mailboxDataList.add(inboxData);
                            }
                        }
                        emailList = new ArrayList<>();
                        if(mailboxDataList != null) {
                            for (MailboxData mailData : mailboxDataList) {
                                emailList.add(new Email(mailData.getFrom(), mailData.getSubject(), mailData.getContent(), mailData.getImage(), mailData.getTime()));
                            }
                        }


                        runOnUiThread(() -> {
                            setupEmailList(emailList);
                        });

                    } catch (UserRecoverableAuthIOException e) {
                        startActivityForResult(e.getIntent(), REQUEST_AUTHORIZATION);
                        Log.d("FetchMessageError", e.toString());
                        // Handle error while fetching or processing messages
                    } catch (IOException e) {
                        Log.e("TAG", "Error retrieving inbox data: " + e.getMessage());
                    }

                }
            }).start();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_AUTHORIZATION && resultCode == RESULT_OK) {
            getGmailArchiveMessages();
        }
    }
}