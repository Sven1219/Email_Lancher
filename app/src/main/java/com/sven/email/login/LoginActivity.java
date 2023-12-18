package com.sven.email.login;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.sven.email.LanguageActivity;
import com.sven.email.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.sven.email.customLauncher.AppsDrawerAdapter;

import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends AppCompatActivity {

    private  static  final  int RC_SIGN_IN = 100;
    private FirebaseAuth mAuth;
    public static GoogleSignInClient mGoogleSignInClient;
    private static GoogleSignInAccount account;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);
        getSupportActionBar().hide();

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//        List<GoogleSignInAccount> accounts = new ArrayList<>();
//        Intent inte = mGoogleSignInClient.getSignInIntent();

//        RecyclerView loginAccountRecyclerView = findViewById(R.id.loginAccountRecyclerView);
//        LoginAccountAdapter adapter = new LoginAccountAdapter(accounts);
//        loginAccountRecyclerView.setAdapter(adapter);

        // Set click listener for the Google Sign-In button
        findViewById(R.id.sign_in_button).setOnClickListener((View.OnClickListener) view -> {
            // Initialize sign in intent
            Intent intent = mGoogleSignInClient.getSignInIntent();
            // Start activity for result
            startActivityForResult(intent, 100);
        });
    }


//    private void signIn() {
//        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(@NonNull Task<GoogleSignInAccount> completedTask) {
        try {
            account = completedTask.getResult(ApiException.class);
            Intent intent1 = new Intent(LoginActivity.this, LanguageActivity.class);
            startActivity(intent1);
            firebaseAuthWithGoogle(account.getIdToken());
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason
            Log.w("TAG", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("TAG", "signInWithCredential:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        // Proceed to handle authenticated user
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("TAG", "signInWithCredential:failure", task.getException());
                        // Notify user of authentication failure
                    }
                });
    }

    public static GoogleSignInAccount getAccount() {
        return account;
    }



}





