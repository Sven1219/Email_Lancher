package com.sven.email.mailbox;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.core.app.ActivityCompat;

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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
import com.google.api.services.gmail.model.Draft;
import com.google.api.services.gmail.model.Message;
import com.sven.email.BaseActivity;
import com.sven.email.MainActivity;
import com.sven.email.R;
import com.sven.email.login.LoginActivity;


public class ComposeActivity extends BaseActivity {
    private static final String APPLICATION_NAME = "Email Launcher";
    private static final int REQUEST_AUTHORIZATION = 1001;
    private static final int REQUEST_DRAFT_AUTHORIZATION = 1002;
    private static final int REQUEST_Attachment_PICKER = 1;
    private static final int PICK_CONTACT_REQUEST = 3;
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
    private EditText composerTo;
    private EditText composerSubject;
    private EditText composerText;
    public String fileName = "";

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
        composerTo = findViewById(R.id.composer_to);
        composerSubject = findViewById(R.id.composer_subject);
        composerText = findViewById(R.id.composer_email_text);
        switch (id) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.attachFile:

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    }

                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    }
                }

                Intent attachIntent = new Intent(Intent.ACTION_GET_CONTENT);
                attachIntent.setType("*/*");
                startActivityForResult(attachIntent, REQUEST_Attachment_PICKER);
                return true;
            case R.id.addcontact:
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, PICK_CONTACT_REQUEST);
                return true;
            case R.id.savedraft:
                try {
                    saveDraft(composerTo.getText().toString(),composerSubject.getText().toString(),composerText.getText().toString());
                } catch (GeneralSecurityException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return true;
            case R.id.discard:
                composerTo.setText("");
                composerSubject.setText("");
                composerText.setText("");
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // The permission was granted, so you can access the file
            } else {
                // The permission was denied, so you cannot access the file
            }
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
                .setApplicationName("Email Launcher")
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
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onClear();
            }
        });
    }

    public void saveDraft(String to, String subject, String body) throws GeneralSecurityException, IOException {
        this.to = to;
        this.subject = subject;
        this.body = body;
        saveDraftThread();
    }

    private void saveDraftThread() {
        GoogleAccountCredential mCredential = GoogleAccountCredential.usingOAuth2(
                this, Arrays.asList(SCOPES));
        mCredential.setSelectedAccount(LoginActivity.getAccount().getAccount());
        String from = mCredential.getSelectedAccountName();
        Gmail service = new Gmail.Builder(
                AndroidHttp.newCompatibleTransport(),
                new GsonFactory(),
                mCredential)
                .setApplicationName("Email Launcher")
                .build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    message = createMessage(from,to, subject, body);
                    Message realmessage = createMessageWithEmail(message);
                    Draft draft = new Draft();
                    draft.setMessage(realmessage);
                    draft  = service.users().drafts().create("me", draft).execute();
                } catch (UserRecoverableAuthIOException e) {
                    System.out.println("Error sending message: " + e.getMessage());
                    try {
                        startActivityForResult(e.getIntent(), REQUEST_DRAFT_AUTHORIZATION);
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
        switch (requestCode) {
            case REQUEST_AUTHORIZATION:
                if (resultCode == RESULT_OK) {
                    sendThread();
                }
                break;
            case REQUEST_DRAFT_AUTHORIZATION:
                if (resultCode == RESULT_OK) {
                    saveDraftThread();
                }
                break;
            case REQUEST_Attachment_PICKER:
                if(resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    fileName = uri.getPath();
//                    edtAttachmentData.setText(fileName);
                }
                break;
        }
    }

    private void onClear() {
        composerTo.setText("");
        composerSubject.setText("");
        composerText.setText("");
    }

    private MimeMessage createMessage(String from, String to,  String subject, String bodyText) throws MessagingException, IOException {
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

        MimeBodyPart textBody = new MimeBodyPart();
        textBody.setContent(bodyText, "text/plain");
        textBody.setHeader("Content-Type", "text/plain; charset=\"UTF-8\"");
        textBody.setText(bodyText);
        multipart.addBodyPart(textBody);

        if (!(this.fileName.equals(""))) {
            // Create new MimeBodyPart object and set DataHandler object to this object
            MimeBodyPart attachmentBody = new MimeBodyPart();
            String filename = this.fileName; // change accordingly
            Uri attachmentUri = Uri.parse(filename);
            InputStream attachmentInputStream = getContentResolver().openInputStream(attachmentUri);
            DataSource source = new InputStreamDataSource(attachmentInputStream, filename);
            attachmentBody.setDataHandler(new DataHandler(source));
            attachmentBody.setFileName(filename);
            attachmentBody.attachFile(new File(filename));
            attachmentBody.setHeader("Content-Type", "text/plain; charset=\"UTF-8\"");
            multipart.addBodyPart(attachmentBody);
        }

        //Set the multipart object to the message object
        email.setContent(multipart);
        return email;
    }

    private Message createMessageWithEmail(MimeMessage email)
            throws MessagingException, IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        email.writeTo(buffer);
        byte[] rawMessageBytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(rawMessageBytes);
        Message message1 = new Message();
        message1.setRaw(encodedEmail);
        return message1;
    }

}


