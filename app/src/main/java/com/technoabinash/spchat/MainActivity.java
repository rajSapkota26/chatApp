package com.technoabinash.spchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.technoabinash.spchat.adapter.FragmentsAdapter;
import com.technoabinash.spchat.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
ActivityMainBinding binding;
//    ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
//    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
//        database = FirebaseDatabase.getInstance();

        binding.mainViewPager.setAdapter(new FragmentsAdapter(getSupportFragmentManager()));
        binding.maintabLayout.setupWithViewPager(binding.mainViewPager);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_item,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case  R.id.logout:
                mAuth.signOut();
                Intent intent=new Intent(MainActivity.this,SignInActivity.class);
                startActivity(intent);
                break;
                case  R.id.setting:
                    Intent intent2=new Intent(MainActivity.this,SettingActivity.class);
                    startActivity(intent2);
                break;
                case  R.id.groupChat:
                    Intent intent1=new Intent(MainActivity.this,GroupChatActivity.class);
                    startActivity(intent1);
                break;
        }
        return true;
    }
}