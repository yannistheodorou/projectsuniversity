package com.katanemimena.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.katanemimena.R;
import com.katanemimena.util.MusicFileManager;
import com.katanemimena.util.NetworkManager;

import java.io.FileDescriptor;
import java.io.IOException;

public class MusicPlayerActivity extends AppCompatActivity {
    /**
     * State of activity
     */
    boolean isOfflineMode = false;
    NetworkManager.DataHolder musicData;
    boolean isPlayingMusic;
    boolean isActivityReadyToPlaySong = false;
    String artistName;
    String songName;

    /**
     * Managers
     */
    MusicFileManager musicFileManager;
    NetworkManager networkManager;
    MediaPlayer mediaPlayer;

    /**
     * Views
     */
    Button startStopBtn;
    TextView songTextView;
    TextView artistTextView;
    SeekBar seekBar;
    FloatingActionButton saveMusicFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        setTitle("");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //FindView
        findAllViews();

        //Get incoming data
        getIncomingData();

        //Update UI with names
        songTextView.setText(songName);
        artistTextView.setText(artistName);
        seekBar.setProgress(0);

        //Logic of activity
        initializeManagers();

        if(!isOfflineMode){
            setTitle("Online streaming");
            setupOnlineMode();
        }else{
            setTitle("Offline music");
            setupOfflineMode();
        }

        setClickListenersForViews();
    }

    private void setClickListenersForViews() {
        startStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPlayingMusic){
                    isPlayingMusic = false;
                    mediaPlayer.pause();
                    startStopBtn.setText("START");
                }else{
                    isPlayingMusic = true;
                    mediaPlayer.start();
                    startStopBtn.setText("STOP");
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (!isActivityReadyToPlaySong){
                    Toast.makeText(MusicPlayerActivity.this, "Buffering song..", Toast.LENGTH_SHORT).show();
                }
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });

        saveMusicFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicFileManager.saveMusic(musicData.data, artistName, songName);
                Snackbar.make(v, "Song downloaded", Snackbar.LENGTH_LONG).show();
                musicFileManager.saveMusic(musicData.data, artistName, songName);
            }
        });
    }

    private void setupOfflineMode() {
        View fab = findViewById(R.id.save_music_fab);
        fab.setVisibility(View.GONE);
        try{
            FileDescriptor savedMusic = musicFileManager.getSavedMusic(artistName, songName);
            mediaPlayer.setDataSource(savedMusic);
            mediaPlayer.prepare();
            mediaPlayer.start();
            startStopBtn.setText("STOP");
            enableSyncronizationOfSeekbarWithSongAudio();
            isActivityReadyToPlaySong = true;
        }catch (Exception e){
            System.out.println("debug");
        }
    }

    private void setupOnlineMode() {
        LiveData<NetworkManager.DataHolder> livedatasWithInfo = networkManager.callDownloadMusic(
                artistName.replace(" ", "_"),
                songName.replace(" ", "_"));
        livedatasWithInfo.observe(this, new Observer<NetworkManager.DataHolder>() {
            @Override
            public void onChanged(final NetworkManager.DataHolder dataHolder) {
                if(dataHolder == null)return;

                try {
                    FileDescriptor fileDescriptor = musicFileManager.crateTempFileDescriptor(dataHolder.data);
                    mediaPlayer.setDataSource(fileDescriptor);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    isActivityReadyToPlaySong = true;
                    startStopBtn.setText("STOP");
                    musicData = dataHolder;
                    enableSyncronizationOfSeekbarWithSongAudio();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void initializeManagers() {
        musicFileManager = new MusicFileManager(getApplicationContext());
        networkManager = new NetworkManager(getApplicationContext());
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setLooping(true);
    }

    private void getIncomingData() {
        artistName = getIntent().getExtras().getString("artist");
        songName = getIntent().getExtras().getString("song_name");
        isOfflineMode = getIntent().getExtras().getBoolean("is_offline");
    }

    private void findAllViews() {
        startStopBtn = findViewById(R.id.start_stop_btn);
        songTextView = findViewById(R.id.song_title_textView);
        artistTextView = findViewById(R.id.artist_title_textview);
        seekBar = findViewById(R.id.seekBar);
        saveMusicFab = findViewById(R.id.save_music_fab);
    }

    private void enableSyncronizationOfSeekbarWithSongAudio(){
        seekBar.setMax(mediaPlayer.getDuration());
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
            if(mediaPlayer != null) {
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                handler.postDelayed(this, 1000);
            }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
