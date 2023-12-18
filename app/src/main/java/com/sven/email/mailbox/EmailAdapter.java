package com.sven.email.mailbox;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.util.Log;

import com.squareup.picasso.Picasso;
import com.sven.email.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class EmailAdapter extends RecyclerView.Adapter<EmailAdapter.EmailViewHolder> {

    private List<Email> emailList;
    private OnItemClickListener listener;
    private  Context context;
    private boolean onLongFlag = false;

    public EmailAdapter(List<Email> emailList, OnItemClickListener listener, Context context) {
        this.emailList = emailList;
        this.listener = listener;
        this.context = context;
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
        String from;
        if (email.getFrom().indexOf('<') - 1 > 0)
            from = email.getFrom().substring(0, email.getFrom().indexOf('<') - 1);
        else from = email.getFrom();
        if(from.length() < 20)    holder.textFrom.setText(from);
        else holder.textFrom.setText(from.substring(0, 20).concat("..."));

        if(email.getSubject().length() < 35)    holder.textSubject.setText(email.getSubject());
        else holder.textSubject.setText(email.getSubject().substring(0, 35).concat("..."));

        if(email.getContent().length() < 35)    holder.textContent.setText(email.getContent());
        else holder.textContent.setText(email.getContent().substring(0, 35).concat("..."));

        String time;
        time = onCalcTime(email.getTime());
        String imageUrl = email.getImage();
        Picasso.get().load(imageUrl).into(holder.imageProfile);
        holder.textTime.setText(time);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                listener.onItemClick(email);
//                if(onLongFlag) {
//
//                } else {
                    String emailTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date(email.getTime()));
                    Context context = v.getContext();
                    Intent intent = new Intent(context, MailDetailActivity.class);
                    intent.setType("text/plain");
                    intent.putExtra("from", email.getFrom());
                    intent.putExtra("to", email.getTo());
                    intent.putExtra("subject", email.getSubject());
                    intent.putExtra("text", email.getContent());
                    intent.putExtra("imageUrl", email.getImage());
                    intent.putExtra("imageUrl", email.getImage());
                    intent.putExtra("body", email.getBody());
                    intent.putExtra("id", email.getId());
                    context.startActivity(intent);
//                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d("asdfasdfas", "asdfsdfasdf");
                onLongFlag = true;
                return false;
            }
        });
    }

    private String onCalcTime(long timestamp) {
        String messagetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date(timestamp));
        Date currentdate = new Date();
        long diff = currentdate.getTime() - timestamp;
        if(diff/1000/60/60/24 >= 1) {
            return String.format("%.0fd ago", Math.floor(diff/1000/60/60/24));
        } else if(diff/1000/60/60 >= 1) {
            return String.format("%.0fh ago", Math.floor(diff/1000/60/60));
        } else if(diff/1000/60 >= 1) {
            return String.format("%.0fm ago", Math.floor(diff/1000/60));
        } else {
            return "just now";
        }
    }

    @Override
    public int getItemCount() {
        return emailList.size();
    }

    public List<Email> getEmailList() {
        return emailList;
    }

    public interface OnItemClickListener {
        void onItemClick(String value);
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

