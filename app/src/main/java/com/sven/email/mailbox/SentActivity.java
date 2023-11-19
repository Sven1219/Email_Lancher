package com.sven.email.mailbox;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
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

public class SentActivity extends BaseActivity {
    private static final int REQUEST_AUTHORIZATION = 1001;
    private Gmail mGmailService;
    private List<Email> emailList;

    private ProgressBar loadingScreen;
    private String from = "";
    private String subject = "";
    private String image = "";
    private String time = "";
    private int index = 0;
    boolean endFlag = false;

    private Thread mainThread;
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

        String category = getString(R.string.sent);
        getGmailSentMessages(0);
        List<Email> emailList = new ArrayList<>();
        loadingScreen = findViewById(R.id.loadingScreen);

        emailRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if(isLastItemVisible(emailLinearLayout)) {
                    Log.d("asdfjkasdjlksddf","asdfsadf");
                    index++;
                    if(!endFlag) getGmailSentMessages(index);
                    else return;
                    emailRecyclerView.setVisibility(View.GONE);
                    loadingScreen.setVisibility(View.VISIBLE);
                }
            }
        });

        categoryText.setText(category);
        setMenuItemBackground(R.id.toSent);
        setNavigationClickListener();
        setNavButtonClickListener();
        setAvataronClickListner();
        setComposebuttonClickListener();
        setEditTextListener();
    }

    private boolean isLastItemVisible(LinearLayoutManager layoutManager) {
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        int totalItemCount = layoutManager.getItemCount();
        return lastVisibleItemPosition == totalItemCount - 1;
    }

    public void getGmailSentMessages(int index) {

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
                        String query = "is:sent";
                        ListMessagesResponse response = mGmailService.users().messages().list("me").setQ(query).execute();
                        int fromIndex = index * 10;
                        int toIndex = (index + 1) * 10;
                        List<Message> messages;
                        try {
                            messages = response.getMessages().subList(fromIndex, toIndex);
                        } catch (Exception e) {
                            messages = response.getMessages().subList(fromIndex, response.size());
                            endFlag = true;
                        }
                        List<MailboxData> mailboxDataList = new ArrayList<>();

                        // Process the fetched messages and extract sender, subject, and content
                        if (messages != null) {
                            for (Message message : messages) {
                                String messageId = message.getId();
                                Message fetchedMessage = mGmailService.users().messages().get("me", messageId).execute();
                                // Retrieve message details such as sender, subject, and content
                                String content = fetchedMessage.getSnippet();
                                List<MessagePartHeader> headers = fetchedMessage.getPayload().getHeaders();
                                if (headers != null) {
                                    for (MessagePartHeader header : headers) {
                                        if (header.getName().equals("From")) {
                                            String fullFrom = header.getValue();
                                            if (fullFrom.indexOf('<') - 1 > 0)
                                                from = fullFrom.substring(0, fullFrom.indexOf('<') - 1);
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
                                Log.d("GmailInboxActivity", "From: " + from + ", Subject: " + subject + ", content: " + content + ", image: " + image + ", time: " + time);
                                MailboxData inboxData = new MailboxData(from, subject, content, image, time);
                                mailboxDataList.add(inboxData);
                            }
                        }
                        if (index == 0) emailList = new ArrayList<>();
                        if (mailboxDataList != null) {
                            for (MailboxData mailData : mailboxDataList) {
                                emailList.add(new Email(mailData.getFrom(), mailData.getSubject(), mailData.getContent(), mailData.getImage(), mailData.getTime()));
                            }
                        }


                        runOnUiThread(() -> {
                            if(index != 0)  mainThread.interrupt();
                            resetupEmailList(emailList, index);
                            Log.d("continuecontinue", "From:");
                            emailRecyclerView.setVisibility(View.VISIBLE);
                            loadingScreen.setVisibility(View.GONE);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_AUTHORIZATION && resultCode == RESULT_OK) {
            getGmailSentMessages(index);
        }
    }
}