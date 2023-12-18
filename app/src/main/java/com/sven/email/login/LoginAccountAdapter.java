package com.sven.email.login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.sven.email.R;

import java.util.List;

public class LoginAccountAdapter extends RecyclerView.Adapter<LoginAccountAdapter.ViewHolder> {

    private List<GoogleSignInAccount> accounts;
    public LoginAccountAdapter(List<GoogleSignInAccount> accounts) {
        this.accounts = accounts;
    }

    @NonNull
    @Override
    public LoginAccountAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.login_account, parent, false);
        return new LoginAccountAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoginAccountAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
        }
    }
}
