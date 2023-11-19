package com.sven.email;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.api.services.gmail.GmailScopes;
import com.sven.email.login.LoginActivity;
import com.sven.email.mailbox.ArchiveActivity;
import com.sven.email.mailbox.ComposeActivity;
import com.sven.email.mailbox.DeleteActivity;
import com.sven.email.mailbox.DraftsActivity;
import com.sven.email.mailbox.Email;
import com.sven.email.mailbox.EmailAdapter;
import com.sven.email.mailbox.InboxActivity;
import com.sven.email.mailbox.JunkActivity;
import com.sven.email.mailbox.OutboxActivity;
import com.sven.email.mailbox.PrimaryActivity;
import com.sven.email.mailbox.SentActivity;
import com.sven.email.mailbox.StarActivity;
import com.sven.email.setting.SettingsActivity;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public abstract class BaseActivity extends AppCompatActivity {
    protected DrawerLayout drawerLayout;
    protected TextView categoryText;
    protected RecyclerView emailRecyclerView;
    protected EmailAdapter emailAdapter;
    protected ImageView emptyMailImageView;
    protected LinearLayoutManager emailLinearLayout;
    protected Button composebutton;
    protected EditText inputEditText;
    protected CircleImageView circularImageView;
    protected static final String[] SCOPES = {
            GmailScopes.GMAIL_LABELS,
            GmailScopes.GMAIL_COMPOSE,
            GmailScopes.GMAIL_INSERT,
            GmailScopes.GMAIL_MODIFY,
            GmailScopes.GMAIL_READONLY,
            GmailScopes.MAIL_GOOGLE_COM
    };


    protected void setupEmailList(List<Email> emailList) {

        if(emailList.size() == 0) {
            emptyMailImageView.setVisibility(View.VISIBLE);
            emailRecyclerView.setVisibility(View.GONE);
            composebutton.setVisibility(View.VISIBLE);
        } else {
            emptyMailImageView.setVisibility(View.GONE);
            emailRecyclerView.setVisibility(View.VISIBLE);
            composebutton.setVisibility(View.VISIBLE);
            // Set up the RecyclerView
            emailAdapter = new EmailAdapter(emailList);
            emailLinearLayout = new LinearLayoutManager(this);
            emailRecyclerView.setLayoutManager(emailLinearLayout);
            emailRecyclerView.setAdapter(emailAdapter);
        }
    }

    protected  void resetupEmailList(List<Email> emailList, int mode) {
        if(emailList.size() == 0) {
            emptyMailImageView.setVisibility(View.VISIBLE);
            emailRecyclerView.setVisibility(View.GONE);
            composebutton.setVisibility(View.VISIBLE);
        } else {
            emptyMailImageView.setVisibility(View.GONE);
            emailRecyclerView.setVisibility(View.VISIBLE);
            composebutton.setVisibility(View.VISIBLE);
            // Set up the RecyclerView
                emailAdapter = new EmailAdapter(emailList);
                emailLinearLayout = new LinearLayoutManager(this);
                emailRecyclerView.setLayoutManager(emailLinearLayout);
                emailRecyclerView.setAdapter(emailAdapter);
        }
    }

    protected void setComposebuttonClickListener () {
        composebutton = findViewById(R.id.composebutton);
        composebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BaseActivity.this, ComposeActivity.class));
            }
        });
    }

    protected void setNavButtonClickListener() {
        drawerLayout = findViewById(R.id.drawerLayout);
        ImageButton btnShowNavigation = findViewById(R.id.navButton);

        btnShowNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
    protected void setMenuItemBackground(int menuItemId) {
        RelativeLayout toInbox = findViewById(R.id.toInbox);
        RelativeLayout toSent = findViewById(R.id.toSent);
        RelativeLayout toOutbox = findViewById(R.id.toOutbox);
        RelativeLayout toArchive = findViewById(R.id.toArchive);
        RelativeLayout toDrafts = findViewById(R.id.toDrafts);
        RelativeLayout toJunk = findViewById(R.id.toJunk);
        RelativeLayout toDelete = findViewById(R.id.toDelete);
        RelativeLayout toPrimary = findViewById(R.id.toPrimary);
        RelativeLayout toStar = findViewById(R.id.toStar);
        RelativeLayout toSettings = findViewById(R.id.toSettings);

        toInbox.setBackgroundResource(0);
        toSent.setBackgroundResource(0);
        toOutbox.setBackgroundResource(0);
        toArchive.setBackgroundResource(0);
        toDrafts.setBackgroundResource(0);
        toJunk.setBackgroundResource(0);
        toDelete.setBackgroundResource(0);
        toPrimary.setBackgroundResource(0);
        toStar.setBackgroundResource(0);
        toSettings.setBackgroundResource(0);

        switch (menuItemId) {
            case R.id.toInbox:
                toInbox.setBackgroundResource(R.drawable.bg_menu_pink);
                break;
            case R.id.toSent:
                toSent.setBackgroundResource(R.drawable.bg_menu_pink);
                break;
            case R.id.toOutbox:
                toOutbox.setBackgroundResource(R.drawable.bg_menu_pink);
                break;
            case R.id.toArchive:
                toArchive.setBackgroundResource(R.drawable.bg_menu_pink);
                break;
            case R.id.toDrafts:
                toDrafts.setBackgroundResource(R.drawable.bg_menu_pink);
                break;
            case R.id.toJunk:
                toJunk.setBackgroundResource(R.drawable.bg_menu_pink);
                break;
            case R.id.toDelete:
                toDelete.setBackgroundResource(R.drawable.bg_menu_pink);
                break;
            case R.id.toPrimary:
                toPrimary.setBackgroundResource(R.drawable.bg_menu_pink);
                break;
            case R.id.toStar:
                toStar.setBackgroundResource(R.drawable.bg_menu_pink);
                break;
            case R.id.toSettings:
                toSettings.setBackgroundResource(R.drawable.bg_menu_pink);
                break;
        }
    }

    protected void setNavigationClickListener() {
        GoogleSignInAccount account = LoginActivity.getAccount();
        TextView navmail = findViewById(R.id.navmail);
        navmail.setText(account.getEmail());

        RelativeLayout toInbox = findViewById(R.id.toInbox);
        RelativeLayout toSent = findViewById(R.id.toSent);
        RelativeLayout toOutbox = findViewById(R.id.toOutbox);
        RelativeLayout toArchive = findViewById(R.id.toArchive);
        RelativeLayout toDrafts = findViewById(R.id.toDrafts);
        RelativeLayout toJunk = findViewById(R.id.toJunk);
        RelativeLayout toDelete = findViewById(R.id.toDelete);
        RelativeLayout toPrimary = findViewById(R.id.toPrimary);
        RelativeLayout toStar = findViewById(R.id.toStar);
        RelativeLayout toSettings = findViewById(R.id.toSettings);

        toInbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BaseActivity.this, InboxActivity.class));
            }
        });

        toSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BaseActivity.this, SentActivity.class));
            }
        });

        toOutbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BaseActivity.this, OutboxActivity.class));
            }
        });

        toArchive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BaseActivity.this, ArchiveActivity.class));
            }
        });

        toDrafts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BaseActivity.this, DraftsActivity.class));
            }
        });

        toJunk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BaseActivity.this, JunkActivity.class));
            }
        });

        toDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BaseActivity.this, DeleteActivity.class));
            }
        });

        toPrimary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BaseActivity.this, PrimaryActivity.class));
            }
        });

        toStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BaseActivity.this, StarActivity.class));
            }
        });

        toSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BaseActivity.this, SettingsActivity.class));
            }
        });
    }

    protected void setEditTextListener() {
        inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Filter the email list when text is changed
                String filterText = s.toString();
                filterEmailList(filterText);
            }

            @Override
            public void afterTextChanged(Editable s) {}

        });
    }

    protected void filterEmailList(String filterText) {
        List<Email> filteredList = new ArrayList<>();
        filteredList.clear();
        for (Email email : emailAdapter.getEmailList()) {
            if (email.getFrom().toLowerCase().contains(filterText.toLowerCase())) {
                filteredList.add(email);
                Log.d("Tag", "1" + emailAdapter.getEmailList());
            }
        }
        setupEmailList(filteredList);
    }

    protected void setAvataronClickListner () {
        GoogleSignInAccount account = LoginActivity.getAccount();
        Uri photoUrl = account.getPhotoUrl();
        if (photoUrl != null) {
//            circularImageView.setImageResource(R.drawable.avatar);
            Glide.with(this)
                    .load(photoUrl)
                    .placeholder(R.drawable.blank_avatar) // Optional placeholder image while loading
                    .error(R.drawable.blank_avatar) // Optional error image if the load fails
                    .into(circularImageView);

        }
        circularImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show the modal
//                showModal(v);
                logout();

            }
        });


    }

    protected void logout() {
        Context packageContext = this;
        GoogleSignInClient mGoogleSignInClient = LoginActivity.mGoogleSignInClient;
        mGoogleSignInClient.signOut()
            .addOnCompleteListener(this, task -> {
                // Handle sign out success
//                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(packageContext, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            });
    }

    protected void showModal(View v) {
        // Create and show the modal dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Inflate the custom layout for the modal
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.modal_avatar_click, null);

        // Set the custom view to the dialog
        builder.setView(view);



        AlertDialog dialog = builder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                RelativeLayout currentAccount = findViewById(R.id.currentAccount);
                currentAccount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.w("logout","success");

                    }
                });
            }
        });

        dialog.show();
    }

}
