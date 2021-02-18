package com.technoabinash.spchat.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.technoabinash.spchat.ChatDetailsActivity;
import com.technoabinash.spchat.R;
import com.technoabinash.spchat.models.Users;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.viewHolder> {
    ArrayList<Users> contactList;
    Context context;

    public UsersAdapter(ArrayList<Users> contactList, Context context) {
        this.contactList = contactList;
        this.context = context;
    }

    @NonNull
    @Override
    public UsersAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_show_user, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersAdapter.viewHolder holder, int position) {
        final Users userList = contactList.get(position);
        Picasso.get().load(userList.getUserProfile()).placeholder(R.drawable.profile).into(holder.profile);
        holder.userName.setText(userList.getUserName());
        holder.lastMessage.setText(userList.getUserLastMessage());

        FirebaseDatabase.getInstance().getReference().child("Chats").
                child(FirebaseAuth.getInstance().getUid()+userList.getUserId())
                .orderByChild("timeStamp").limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()){
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        holder.lastMessage.setText(dataSnapshot.child("message").getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ChatDetailsActivity.class);
            intent.putExtra("userName", userList.getUserName());
            intent.putExtra("userId", userList.getUserId());
            intent.putExtra("profile", userList.getUserProfile());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView profile;
        TextView userName, lastMessage;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            profile = itemView.findViewById(R.id.userProfile);
            userName = itemView.findViewById(R.id.mUserName);
            lastMessage = itemView.findViewById(R.id.mLastMessage);
        }
    }
}
