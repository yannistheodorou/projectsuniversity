package com.katanemimena.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.katanemimena.R;
import com.katanemimena.adapter.SongAdapter;
import com.katanemimena.util.NetworkManager;

import org.json.JSONObject;

import java.util.List;

public class SongActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final String artist = getIntent().getExtras().getString("artist");
        setTitle(artist+"'s songs");

        NetworkManager networkManager = new NetworkManager(getApplicationContext());
        networkManager.callSearchForMusic(artist).observe(this, new Observer<List<JSONObject>>() {
            @Override
            public void onChanged(List<JSONObject> jsonObjects) {
                if(jsonObjects != null){
                    RecyclerView recyclerView = findViewById(R.id.recycler_view_songs);
                    recyclerView.setLayoutManager(new LinearLayoutManager(SongActivity.this));
                    recyclerView.setAdapter(new SongAdapter(jsonObjects, SongActivity.this, artist));
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
