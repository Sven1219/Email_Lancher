package com.sven.email.mailbox;

import android.content.Context;
import android.content.Intent;
import android.credentials.CredentialManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.android.gms.tasks.Task;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;
import com.sven.email.BaseActivity;
import com.sven.email.login.LoginActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;

public class EmailSender extends BaseActivity {

    private static final String APPLICATION_NAME = "Email Launcher";
    private static final int REQUEST_AUTHORIZATION = 1001;
    private Message message;

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    private static final String CREDENTIALS_FILE_PATH = "/google-services.json";
    private Context context;

    private static final String[] SCOPES = {
            GmailScopes.GMAIL_LABELS,
            GmailScopes.GMAIL_COMPOSE,
            GmailScopes.GMAIL_INSERT,
            GmailScopes.GMAIL_MODIFY,
            GmailScopes.GMAIL_READONLY,
            GmailScopes.MAIL_GOOGLE_COM
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    public void sendEmail(Context context, String to, String subject, String body) throws GeneralSecurityException, IOException {
        this.context = context;
        message = createMessage(to, subject, body);
        sendThread();
    }

    private void sendThread() {
        GoogleAccountCredential mCredential = GoogleAccountCredential.usingOAuth2(
                context, Arrays.asList(SCOPES));
        mCredential.setSelectedAccount(LoginActivity.getAccount().getAccount());
        String from = mCredential.getSelectedAccountName();
        Gmail service = new Gmail.Builder(
                AndroidHttp.newCompatibleTransport(),
                new GsonFactory(),
                mCredential)
                .setApplicationName(APPLICATION_NAME)
                .build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Message sentMessage = service.users().messages().send("me", message).execute();
                    System.out.println("Message sent: " + sentMessage.getId());
                } catch (UserRecoverableAuthIOException e) {
                    System.out.println("Error sending message: " + e.getMessage());
                    try {
                        startActivityForResult(e.getIntent(), REQUEST_AUTHORIZATION);
                    } catch(Exception k) {
                        System.out.println("Error sending message: " + k.getMessage());
                    }
                }  catch (Exception e) {
                    System.out.println("Error sending message all: " + e.getMessage());
                }
            }
        }).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_AUTHORIZATION && resultCode == RESULT_OK) {
            sendThread();
        }
    }

    private Message createMessage(String to, String subject, String body) {
        Message message = new Message();
        message.setRaw(encodeEmail(to, subject, body));
        return message;
    }

    private String encodeEmail(String to, String subject, String body) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("From:").append(to).append(LoginActivity.getAccount().getEmail());
        stringBuilder.append("To: ").append(to).append("\r\n");
        stringBuilder.append("Subject: ").append(subject).append("\r\n");
        stringBuilder.append("\r\n").append(body);
        return android.util.Base64.encodeToString(stringBuilder.toString().getBytes(), android.util.Base64.URL_SAFE | android.util.Base64.NO_WRAP);
    }
}
