package com.sven.email.mailbox;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Properties;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.Base64;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;
import com.sven.email.BaseActivity;
import com.sven.email.R;
import com.sven.email.login.LoginActivity;


public class ComposeActivity extends BaseActivity {
    private static final String APPLICATION_NAME = "Email Launcher";
    private static final int REQUEST_AUTHORIZATION = 1001;
    private MimeMessage message;

    private static final String[] SCOPES = {
            GmailScopes.GMAIL_LABELS,
            GmailScopes.GMAIL_COMPOSE,
            GmailScopes.GMAIL_INSERT,
            GmailScopes.GMAIL_MODIFY,
            GmailScopes.GMAIL_READONLY,
            GmailScopes.MAIL_GOOGLE_COM
    };
    private String to;
    private String subject;
    private String body;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compose);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Compose");
        EditText composer_from = findViewById(R.id.composer_from);
        composer_from.setText(LoginActivity.getAccount().getEmail().toString());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        EditText composerTo = findViewById(R.id.composer_to);;
        EditText composerSubject = findViewById(R.id.composer_subject);;
        EditText composerText = findViewById(R.id.composer_email_text);
        switch (id) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.sendmail:
                try {
                    sendEmail(composerTo.getText().toString(),composerSubject.getText().toString(),composerText.getText().toString());
                } catch (GeneralSecurityException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void sendEmail(String to, String subject, String body) throws GeneralSecurityException, IOException {
        this.to = to;
        this.subject = subject;
        this.body =  body;
        sendThread();
    }

    private void sendThread() {
        GoogleAccountCredential mCredential = GoogleAccountCredential.usingOAuth2(
                this, Arrays.asList(SCOPES));
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
                    message = createMessage(from,to, subject, body);
                    Message realmessage = createMessageWithEmail(message);
                    Message sentMessage = service.users().messages().send("me", realmessage).execute();
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

    private MimeMessage createMessage(String from, String to,  String subject, String bodyText) throws MessagingException {
        Message message = new Message();
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);
        InternetAddress tAddress = new InternetAddress(to);
        InternetAddress fAddress = new InternetAddress(from);

        email.setFrom(fAddress);
        email.addRecipient(javax.mail.Message.RecipientType.TO, tAddress);
        email.setSubject(subject);

        // Create Multipart object and add MimeBodyPart objects to this object
        Multipart multipart = new MimeMultipart();

        // Changed for adding attachment and text
        // email.setText(bodyText);

        BodyPart textBody = new MimeBodyPart();
        textBody.setText(bodyText);
        multipart.addBodyPart(textBody);

//        if (!(activity.fileName.equals(""))) {
//            // Create new MimeBodyPart object and set DataHandler object to this object
//            MimeBodyPart attachmentBody = new MimeBodyPart();
//            String filename = activity.fileName; // change accordingly
//            DataSource source = new FileDataSource(filename);
//            attachmentBody.setDataHandler(new DataHandler(source));
//            attachmentBody.setFileName(filename);
//            multipart.addBodyPart(attachmentBody);
//        }

        //Set the multipart object to the message object
        email.setContent(multipart);
        return email;
    }

    private Message createMessageWithEmail(MimeMessage email)
            throws MessagingException, IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        email.writeTo(bytes);
        String encodedEmail = Base64.encodeBase64URLSafeString(bytes.toByteArray());
        Message message1 = new Message();
        message1.setRaw(encodedEmail);
        return message1;
    }

}


