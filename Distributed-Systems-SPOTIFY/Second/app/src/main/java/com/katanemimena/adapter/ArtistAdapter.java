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
import com.katanemimena.activity.SongActivity;
import com.katanemimena.util.Util;

import org.json.JSONObject;

import java.util.List;


public class ArtistAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<JSONObject> data;
    Context context;

    public ArtistAdapter(List<JSONObject> data, Context context) {
        this.data = data;
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_artist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        JSONObject jsonObject = data.get(position);

        ViewHolder hold = (ViewHolder) holder;
        TextView authorTextView = hold.rootView.findViewById(R.id.artist_name);

        final String artistName = Util.getString(jsonObject, "artist_name");
        authorTextView.setText(artistName);

        hold.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SongActivity.class);
                intent.putExtra("artist", artistName);
                context.startActivity(intent);
            }
        });
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