package com.katanemimena.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.katanemimena.R;
import com.katanemimena.adapter.OfflineSongAdapter;
import com.katanemimena.adapter.SongAdapter;
import com.katanemimena.util.MusicFileManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class OfflineMusicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_music);
        setTitle("Saved music");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        MusicFileManager musicFileManager = new MusicFileManager(getApplicationContext());

        List<JSONObject> allSavedSongs = musicFileManager.getAllSavedSongs();


        RecyclerView recyclerView = findViewById(R.id.offline_songs_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new OfflineSongAdapter(allSavedSongs, this, null));


        if(allSavedSongs.isEmpty()){
            TextView emptyListTextView = findViewById(R.id.empty_list_tv);
            emptyListTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
