package com.technoabinash.spchat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.technoabinash.spchat.databinding.ActivitySettingBinding;
import com.technoabinash.spchat.models.Users;

import java.util.HashMap;

public class SettingActivity extends AppCompatActivity {
    ActivitySettingBinding binding;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    FirebaseStorage mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mStorageRef = FirebaseStorage.getInstance();


        binding.sBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status=binding.setxStatus.getText().toString();
                String userName=binding.setxUserName.getText().toString();

                HashMap<String,Object> obj=new HashMap<>();
                obj.put("userName",userName);
                obj.put("userStatus",status);
                database.getReference().child("Users").child(mAuth.getUid()).updateChildren(obj);
                Toast.makeText(SettingActivity.this, "Profile Updated....", Toast.LENGTH_SHORT).show();
            }
        });

        database.getReference().child("Users").child(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users=snapshot.getValue(Users.class);
                Picasso.get().load(users.getUserProfile()).placeholder(R.drawable.profile).into(binding.viewProfile);
                binding.setxUserName.setText(users.getUserName());
                binding.setxStatus.setText(users.getUserStatus());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        binding.addProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                startActivityForResult(i, 26);
            }
        });

         binding.sbtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotToMain();
            }
        });
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data.getData() != null) {
            Uri uri = data.getData();
            binding.viewProfile.setImageURI(uri);

            final StorageReference reference = mStorageRef.getReference().child("Profile").child(mAuth.getUid());
            reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri1) {

                            database.getReference().child("Users").child(mAuth.getUid())
                                    .child("userProfile").setValue(uri1.toString());
                            Toast.makeText(SettingActivity.this, "profile updated", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }

    private void gotToMain() {
        Intent intent = new Intent(SettingActivity.this, MainActivity.class);
        startActivity(intent);
    }


}