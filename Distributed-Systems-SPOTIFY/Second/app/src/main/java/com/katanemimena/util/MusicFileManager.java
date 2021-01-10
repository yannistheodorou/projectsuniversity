package com.katanemimena.util;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for interacting with local music files
 */
public class MusicFileManager {
    Context context;
    LocalStorage localStorage;

    public MusicFileManager(Context context) {
        this.context = context;
        localStorage = new LocalStorage(context);
    }

    public FileDescriptor crateTempFileDescriptor(byte[] data){
        try {
            FileOutputStream fos = context.openFileOutput("cache_song_online" + ".mp3", Context.MODE_PRIVATE);
            fos.write(data);
            fos.flush();
            fos.close();

            FileInputStream fileInputStream = context.openFileInput("cache_song_online" + ".mp3");
            FileDescriptor fd = fileInputStream.getFD();
            return fd;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public FileDescriptor getSavedMusic(String artist, String song){
        FileInputStream fileInputStream = null;
        artist = artist.replace(" ", "_");
        song = song.replace(" ", "_");
        try {
            fileInputStream = context.openFileInput("saved_song_" + artist + "_" + song + ".mp3");
            FileDescriptor fd = fileInputStream.getFD();
            return fd;
        } catch (Exception e) {
            return null;
        }
    }

    public void saveMusic(byte[] data, String artist, String song){
        FileOutputStream fos = null;
        artist = artist.replace(" ", "_");
        song = song.replace(" ", "_");
        try {
            fos = context.openFileOutput("saved_song_" + artist + "_" + song + ".mp3", Context.MODE_PRIVATE);
            fos.write(data);
            fos.flush();
            fos.close();

            ifFirstTimeInitializeArray();
            saveMusicNameInLocalMemory(artist, song);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void saveMusicNameInLocalMemory(String artist, String song) throws Exception {
        String string = localStorage.getString(LocalStorage.CACHE_SAVED_MUSIC);
        JSONArray array = new JSONArray(string);

        for (int i = 0; i < array.length(); i++) {
            if(array.getJSONObject(i).getString("artist").equalsIgnoreCase(artist)
                    && array.getJSONObject(i).getString("song_name").equalsIgnoreCase(song)){
                return;
            }
        }

        JSONObject newItemToSave = new JSONObject();
        newItemToSave.put("artist", artist);
        newItemToSave.put("song_name", song);
        array.put(newItemToSave);

        localStorage.setString(LocalStorage.CACHE_SAVED_MUSIC, array.toString()); //Update array in local storage with new song
    }

    private void ifFirstTimeInitializeArray() {
        String string = localStorage.getString(LocalStorage.CACHE_SAVED_MUSIC);
        if(string.isEmpty()){
            localStorage.setString(LocalStorage.CACHE_SAVED_MUSIC, "[]");
        }
    }


    public List<JSONObject> getAllSavedSongs(){
        String str = localStorage.getString(LocalStorage.CACHE_SAVED_MUSIC);
        try {
            JSONArray array = new JSONArray(str);
            List<JSONObject> result = new ArrayList<>();
            for (int i = 0; i < array.length() ; i++) {
                result.add(array.getJSONObject(i));
            }
            return result;
        } catch (JSONException e) {}
        return new ArrayList<>();

    }
}
