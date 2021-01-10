package com.katanemimena.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.katanemimena.R;

public class MenuActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Pick action");

        setOnClickListeners();

//        startActivity(new Intent(this, ArtistActivity.class));
    }

    private void setOnClickListeners(){
        findViewById(R.id.artist_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(MenuActivity.this, ArtistActivity.class));
            }
        });

        findViewById(R.id.offline_music_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, OfflineMusicActivity.class));
            }
        });
    }


}
