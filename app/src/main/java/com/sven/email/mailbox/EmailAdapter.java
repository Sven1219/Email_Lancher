package com.sven.email.mailbox;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.sven.email.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class EmailAdapter extends RecyclerView.Adapter<EmailAdapter.EmailViewHolder> {

    private List<Email> emailList;

    public EmailAdapter(List<Email> emailList) {
        this.emailList = emailList;
    }

    @NonNull
    @Override
    public EmailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("TAG", "" + emailList);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_card, parent, false);
        return new EmailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmailViewHolder holder, int position) {

        Email email = emailList.get(position);
        if(email.getFrom().length() < 24)    holder.textFrom.setText(email.getFrom());
        else holder.textFrom.setText(email.getFrom().substring(0, 24).concat("..."));

        if(email.getSubject().length() < 35)    holder.textSubject.setText(email.getSubject());
        else holder.textSubject.setText(email.getSubject().substring(0, 35).concat("..."));

        if(email.getContent().length() < 35)    holder.textContent.setText(email.getContent());
        else holder.textContent.setText(email.getContent().substring(0, 35).concat("..."));

        String imageUrl = email.getImage();
        Picasso.get().load(imageUrl).into(holder.imageProfile);
        holder.textTime.setText(email.getTime());
    }

    @Override
    public int getItemCount() {
        return emailList.size();
    }

    public List<Email> getEmailList() {
        return emailList;
    }

    public static class EmailViewHolder extends RecyclerView.ViewHolder {

        TextView textFrom, textSubject, textContent, textTime;
        CircleImageView imageProfile;


        public EmailViewHolder(@NonNull View itemView) {
            super(itemView);
            textFrom = itemView.findViewById(R.id.from);
            textSubject = itemView.findViewById(R.id.subject);
            textContent = itemView.findViewById(R.id.mailcontent);
            imageProfile = itemView.findViewById(R.id.fromimage);
            textTime = itemView.findViewById(R.id.time);
        }
    }


}

