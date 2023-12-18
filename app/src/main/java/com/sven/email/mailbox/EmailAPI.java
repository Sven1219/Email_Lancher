package com.sven.email.mailbox;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePart;
import com.google.api.services.gmail.model.MessagePartHeader;
import com.sven.email.BaseActivity;
import com.sven.email.login.LoginActivity;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EmailAPI extends BaseActivity {

    private static final int REQUEST_AUTHORIZATION = 1001;
    private Gmail mGmailService;

    public static List<Email>  emailList;
    protected String mainQuery;
    protected String displayFrom;
    protected static int messageNum = 0;
    private Context context = null;

    protected boolean isLastItemVisible(LinearLayoutManager layoutManager) {
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        int totalItemCount = layoutManager.getItemCount();
        return lastVisibleItemPosition == totalItemCount - 1;
    }
    protected void getGmailMessages(int index, String query, String disFrom, Context context) {
        mainQuery = query;
        displayFrom = disFrom;

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
                        ListMessagesResponse response = mGmailService.users().messages().list("me").setQ(query).execute();
                        List<Message> messages = null;
                        int fromIndex = index * 10;
                        int toIndex = (index + 1) * 10;
                        if (index == 0) emailList = new ArrayList<>();
                        if(endFlag)    return;
                        if(response.getMessages() != null) {
                            messageNum = response.getMessages().size();
//                            if(index != 0) {
//                                toIndex = messageNum;
//                                endFlag = true;
//                            }
                            try {
                                messages = response.getMessages().subList(fromIndex, toIndex);//toIndex
//                                endFlag = true;
                            } catch (Exception e) {
                                messages = response.getMessages().subList(fromIndex, messageNum);
                                endFlag = true;
                            }
                        }
                        List<MailboxData> mailboxDataList = new ArrayList<>();

                        // Process the fetched messages and extract sender, subject, and content
                        if (messages != null) {
                            for (Message message : messages) {
                                String messageId = message.getId();
                                Message fetchedMessage = mGmailService.users().messages().get("me", messageId).execute();
                                // Retrieve message details such as sender, subject, and content
                                String content = fetchedMessage.getSnippet();
                                String mailBody = fetchedMessage.getSnippet();

                                List<MessagePartHeader> headers = fetchedMessage.getPayload().getHeaders();
                                String compare = "From";
                                if(disFrom == "To") compare = "From";
                                else compare = "To";
                                if (headers != null) {
                                    for (MessagePartHeader header : headers) {
                                        if (header.getName().equals(disFrom)) {
                                            from = header.getValue();
                                            image = "https://www.gravatar.com/avatar/" + MD5Util.md5Hex(from.toLowerCase().trim()) + "?d=identicon";
                                        } else if (header.getName().equals(compare)) {
                                            to = header.getValue();

                                        } else if (header.getName().equals("Subject")) {
                                            subject = header.getValue();
                                        }
                                    }
                                }

                                long timestamp = fetchedMessage.getInternalDate();
                                time = timestamp;


//                                MessagePart payload = fetchedMessage.getPayload();
//                                String mailBody = "";
//                                if (payload != null && payload.getParts() != null) {
//                                    for (MessagePart part : payload.getParts()) {
//                                        // Find the part containing the email body (usually with mimeType "text/plain" or "text/html")
//                                        if (part.getMimeType().equals("text/plain") || part.getMimeType().equals("text/html")) {
//                                            // Decode and obtain the email body content
//                                            byte[] bytes = android.util.Base64.decode(part.getBody().getData(), android.util.Base64.DEFAULT);
//                                            mailBody = new String(bytes, StandardCharsets.UTF_8);
//                                            // You may need to handle HTML content differently, e.g., displaying it in a WebView
//                                            break;
//                                        }
//                                    }
//                                }

                                // Now, you can use 'from', 'subject', and 'snippet' to display the message details in the app's UI.
                                MailboxData inboxData = new MailboxData(messageId,from, to,  subject, content, image, time, mailBody);
                                mailboxDataList.add(inboxData);
                            }
                        }
                        if (mailboxDataList != null) {
                            for (MailboxData mailData : mailboxDataList) {
                                emailList.add(new Email(mailData.getId(),mailData.getFrom(), mailData.getTo(), mailData.getSubject(), mailData.getContent(), mailData.getImage(), mailData.getTime(), mailData.getBody()));
                            }
                        }

                        runOnUiThread(() -> {
                            if(index != 0) {
                                mainThread.interrupt();
                            } else {
                                emailRecyclerView.setVisibility(View.VISIBLE);
                                loadingScreen.setVisibility(View.GONE);
                            }
                            setupEmailList(emailList, messageNum, context);
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
            getGmailMessages(index, mainQuery, displayFrom, context);
        }
    }

}
