package com.katanemimena.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.katanemimena.R;
import com.katanemimena.adapter.ArtistAdapter;
import com.katanemimena.util.NetworkManager;

import org.json.JSONObject;

import java.util.List;

public class ArtistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);
        setTitle("Artists");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final RecyclerView recyclerView = findViewById(R.id.artist_recyclerview);
        NetworkManager networkManager = new NetworkManager(getApplicationContext());
        networkManager.callSystemControllerGetInformation().observe(this, new Observer<List<JSONObject>>() {
            @Override
            public void onChanged(List<JSONObject> jsonObjects) {
                if(jsonObjects != null){
                    recyclerView.setLayoutManager(new LinearLayoutManager(ArtistActivity.this)); //kathenas exei adapter
                    recyclerView.setAdapter(new ArtistAdapter(jsonObjects, ArtistActivity.this));
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
