package com.katanemimena.util;

import android.content.Context;
import android.provider.SyncStateContract;
import android.util.Pair;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileDescriptor;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for network calls logic
 */
public class NetworkManager {
    public static final String SYSTEM_CONTROLLER_IP = "192.168.1.14";
    public static final int SYSTEM_CONTROLLER_PORT = 666;
    LocalStorage localStorage;

    public NetworkManager(Context context) {
        this.localStorage = new LocalStorage(context);
    }

    public LiveData<List<JSONObject>> callSystemControllerGetInformation(){
        final MutableLiveData<List<JSONObject>> result = new MutableLiveData<>();
        new Thread( new Runnable() {
            @Override
            public void run() {
                // IN BACKGROUND THREAD to avoid blocking the UI
                try{
                    Socket socket = new Socket(SYSTEM_CONTROLLER_IP, SYSTEM_CONTROLLER_PORT);
                    PrintWriter pr = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    pr.println(RequestBuilder.createRegisterInDistributedSystemRequest());
                    String response = br.readLine();
                    JSONObject json = new JSONObject(response);
                    JSONArray array = json.getJSONArray("hashing_data_result");
                    List<JSONObject> jsonObjects = Util.convertJSONArrayIntoListOfJSONObjects(array);

                    //Cache response
                    localStorage.setString(LocalStorage.CACHE_SYSTEM_CONTROLLER_RESPONSE, array.toString());

                    result.postValue(jsonObjects);
                }catch (Exception e){
                    result.postValue(null);
                }
            }} ).start();
        return result;
    }

    public LiveData<List<JSONObject>> callSearchForMusic(final String artist){
        final MutableLiveData<List<JSONObject>> result = new MutableLiveData<>();
        new Thread( new Runnable() {
            @Override
            public void run() {
                // IN BACKGROUND THREAD to avoid blocking the UI
                try{
                    Pair<String, Integer> broker = getBrokerDetailsForArtist(artist);
                    Socket socket = new Socket(broker.first, broker.second);
                    PrintWriter pr = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    pr.println(RequestBuilder.createSearchRequest(artist));
                    String response = br.readLine();
                    JSONObject json = new JSONObject(response);

                    JSONArray jsonArraySongs = json.getJSONArray("songs");
                    List<JSONObject> songs = new ArrayList<>();
                    for (int i = 0; i < jsonArraySongs.length(); i++) {
                        JSONObject currentSongJSONObject = new JSONObject();
                        currentSongJSONObject.put("song_name", jsonArraySongs.getString(i));
                        songs.add(currentSongJSONObject);
                    }
                    result.postValue(songs);
                }catch (Exception e){
                    result.postValue(null);
                }
            }} ).start();
        return result;
    }

    public LiveData<DataHolder> callDownloadMusic(final String artist, final String song){
        final MutableLiveData<DataHolder> result = new MutableLiveData<>();



        new Thread( new Runnable() {
            @Override
            public void run() {
                // IN BACKGROUND THREAD to avoid blocking the UI
                try{
                    Pair<String, Integer> brokerDetailsForArtist = getBrokerDetailsForArtist(artist);

                    Socket socket = new Socket(brokerDetailsForArtist.first, brokerDetailsForArtist.second);
                    PrintWriter pr = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    pr.println(RequestBuilder.createGetMusicRequest(artist, song));
                    String response = br.readLine();
                    JSONObject resp = new JSONObject(response);

                    //READING BYTES
                    int size_in_bytes = resp.getInt("size_in_bytes");
                    DataInputStream dis = new DataInputStream(socket.getInputStream());
                    byte[] songData = new byte[size_in_bytes];
                    dis.readFully(songData);

                    DataHolder dataHolder = new DataHolder();
                    dataHolder.data = songData;
                    result.postValue(dataHolder);
                }catch (Exception e){
                    byte[] emptyArr = new byte[0];
                    DataHolder dataHolder = new DataHolder();
                    dataHolder.data = emptyArr;
                    result.postValue(dataHolder); // Implies an error
                }
            }} ).start();
        return result;
    }

    private Pair<String, Integer> getBrokerDetailsForArtist(String artist){
        String cachedStringFromSystemController = localStorage.getString(LocalStorage.CACHE_SYSTEM_CONTROLLER_RESPONSE);
        try{
            JSONArray arrayWithArtistsAndConnectionInfo = new JSONArray(cachedStringFromSystemController);
            for (int i = 0; i < arrayWithArtistsAndConnectionInfo.length() ; i++) {
                JSONObject currentJSONObject = arrayWithArtistsAndConnectionInfo.getJSONObject(i);
                if(artist.equalsIgnoreCase(currentJSONObject.getString("artist_name"))){
                    //Return responsible broker details for artist
                    String[] ipAndPort = currentJSONObject.getString("broker_ip_and_port").split(" ");
                    return new Pair<>(ipAndPort[0], Integer.parseInt(ipAndPort[1]));
                }
            }
        }catch (Exception e){}

        // If no broker found for this artist
        return null;
    }


    public static class DataHolder {
        public byte[] data;
    }

}
