package com.technoabinash.spchat.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.technoabinash.spchat.R;
import com.technoabinash.spchat.models.MessagesModel;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter {

    ArrayList<MessagesModel> messagesList;
    Context context;
    int SENDER_VIEW_TYPE = 1;
    int RECEIVER_VIEW_TYPE = 2;
    String recId;

    public ChatAdapter(ArrayList<MessagesModel> messagesList, Context context) {
        this.messagesList = messagesList;
        this.context = context;
    }

    public ChatAdapter(ArrayList<MessagesModel> messagesList, Context context, String recId) {
        this.messagesList = messagesList;
        this.context = context;
        this.recId = recId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == SENDER_VIEW_TYPE) {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_sender_message, parent, false);
            return new SenderviewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_receiver_message, parent, false);
            return new ReceiverviewHolder(view);
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (messagesList.get(position).getmId().equals(FirebaseAuth.getInstance().getUid())) {
            return SENDER_VIEW_TYPE;
        } else {
            return RECEIVER_VIEW_TYPE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessagesModel messagesModel=messagesList.get(position);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(context).setTitle("Delete Message").setMessage("You want to delete this Message?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseDatabase database=FirebaseDatabase.getInstance();
                                String sender=FirebaseAuth.getInstance().getUid()+recId;
                                database.getReference().child("Chats").child(sender).child(messagesModel.getDmId())
                                        .setValue(null);
                            }
                        })
                        .setNegativeButton("no", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                              dialog.dismiss();
                            }
                        }).show();
                return false;
            }
        });






        if (holder.getClass()==SenderviewHolder.class){
            ((SenderviewHolder)holder).sendMessage.setText(messagesModel.getMessage());
//            ((SenderviewHolder)holder).sendTime.setText(messagesModel.getTimeStamp().toString());
        }else {
            ((ReceiverviewHolder)holder).receiveMessage.setText(messagesModel.getMessage());
//            ((ReceiverviewHolder)holder).receiveTime.setText(messagesModel.getTimeStamp().toString());
        }






    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    public class ReceiverviewHolder extends RecyclerView.ViewHolder {

        TextView receiveMessage, receiveTime;

        public ReceiverviewHolder(@NonNull View itemView) {
            super(itemView);

            receiveMessage = itemView.findViewById(R.id.receiverText);
            receiveTime = itemView.findViewById(R.id.receiveTime);
        }
    }

    public class SenderviewHolder extends RecyclerView.ViewHolder {

        TextView sendMessage, sendTime;

        public SenderviewHolder(@NonNull View itemView) {
            super(itemView);

            sendMessage = itemView.findViewById(R.id.senderText);
            sendTime = itemView.findViewById(R.id.senderTime);
        }
    }
}
