package com.katanemimena.util;

import org.json.JSONObject;

public class RequestBuilder {
    public static final String CUSTOMER_IDENTITY = "c";
    public static final String DOWNLOAD_MUSIC = "download_music";
    public static final String SEARCH_ARTIST_SONGS = "get_artist_songs";

    public static String createSearchRequest(String artist){
        try{
            JSONObject request =  new JSONObject();
            request.put("sender", CUSTOMER_IDENTITY);
            request.put("action", SEARCH_ARTIST_SONGS);
            request.put("artist_name", artist);
            return request.toString();
        }catch (Exception e){
            return "";
        }
    }

    public static String createGetMusicRequest(String artist, String song){
        try{
            JSONObject request = new JSONObject();
            request.put("sender", CUSTOMER_IDENTITY);
            request.put("action", DOWNLOAD_MUSIC);
            request.put("artist_name", artist);
            request.put("song_name", song);
            return request.toString();
        }catch (Exception e){
            return "";
        }
    }

    public static String createRegisterInDistributedSystemRequest(){
        try{
            JSONObject request = new JSONObject();
            request.put("sender", CUSTOMER_IDENTITY);
            return request.toString();
        }catch (Exception e){
            return "";
        }
    }
}
