package com.katanemimena.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.katanemimena.R;
import com.katanemimena.activity.MusicPlayerActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class OfflineSongAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    String artist;
    List<JSONObject> data;
    Context context;

    public OfflineSongAdapter(List<JSONObject> data, Context context, String artistName) {
        this.data = data;
        this.context = context;
        this.artist = artistName;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_song, parent, false);
        return new OfflineSongAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        JSONObject jsonObject = data.get(position);
        OfflineSongAdapter.ViewHolder customHolder = (OfflineSongAdapter.ViewHolder) holder;

        TextView songNameTextView = customHolder.rootView.findViewById(R.id.song_name);
        TextView songDescriptionTextView = customHolder.rootView.findViewById(R.id.song_extra_info);
        TextView durationTextView = customHolder.rootView.findViewById(R.id.duration);

        try {
            final String songName = jsonObject.getString("song_name").replace("_", " ");
            final String artistName = jsonObject.getString("artist").replace("_", " ");

            songNameTextView.setText(songName);
            songDescriptionTextView.setText(artistName);

            customHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MusicPlayerActivity.class);
                    intent.putExtra("artist", artistName);
                    intent.putExtra("song_name", songName);
                    intent.putExtra("is_offline", artist == null);
                    context.startActivity(intent);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        View rootView;

        public ViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
        }
    }
}
