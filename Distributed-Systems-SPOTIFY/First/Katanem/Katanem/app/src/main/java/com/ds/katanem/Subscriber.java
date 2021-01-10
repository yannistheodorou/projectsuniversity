package com.ds.katanem;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Subscriber extends AsyncTask<Topic, Integer, ArrayList<Value>> {
    // message bytes
    final byte BROKER_LIST = 1;
    final byte VALUE_LIST = 2;
    // ip enos broker pou tha mas dwsei tous allous
    final String firstBroker = "192.168.1.4";
    // port pou xrisimopoioun oi brokers
    final int brokerPort = 1234;
    // xartis me tous broker
    Map<String, ArrayList<Topic>> brokermap;
    ArrayList<Value> values;
    ArrayList<Topic> topics;

    Socket socket;
    ObjectInputStream socketIn;
    ObjectOutputStream socketOut;

    GoogleMap map;

    public Subscriber(GoogleMap m) {map = m;}

    public void requestTopic(Topic topic) throws IOException {
        socketOut.writeObject(topic);
        socketOut.flush();
    }

    public boolean readValues() throws IOException, ClassNotFoundException {
        byte message = socketIn.readByte();
        if (message == BROKER_LIST) {
            brokermap = (HashMap<String, ArrayList<Topic>>) socketIn.readObject();
            return false;
        } else if (message == VALUE_LIST) {
            values = (ArrayList<Value>) socketIn.readObject();
            return true;
        }
        return false;
    }

    public ArrayList<Value> doInBackground(Topic...params) {
        try {

            if (brokermap == null) {
                //readTopicsFromFile();
                socket = new Socket(firstBroker, brokerPort);
                socketIn = new ObjectInputStream(socket.getInputStream());
                socketOut = new ObjectOutputStream(socket.getOutputStream());

                // steile lathos topic gia na pareis thn lista me tous brokers
                Topic t = new Topic();
                t.lineCode = "";
                t.lineId = "";
                t.description = "";
                requestTopic(t);
                readValues();
                socket.close();
            }

            Topic selection = params[0];
            for (String key : brokermap.keySet()) {
                ArrayList<Topic> list = brokermap.get(key);
                if (list.contains(selection)) {
                    socket = new Socket(key, brokerPort);
                    socketIn = new ObjectInputStream(socket.getInputStream());
                    socketOut = new ObjectOutputStream(socket.getOutputStream());

                    requestTopic(selection);
                    if (!readValues()) {
                        Log.d("tag", "Requested data from the wrong broker");
                        socket.close();
                        return null;
                    } else {
                        socket.close();
                        return values;
                    }
                }
            }



        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            Log.e("Error:", "Unknown host! " + e.getMessage());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.e("Error:", "IO exception! " + e.getMessage());
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            Log.e("Error:", "Class not found! " + e.getMessage());
        }

        return null;
    }

    public void onPostExecute(ArrayList<Value> result) {
        //
        map.clear();
        if (result != null) {
            PolylineOptions options = new PolylineOptions();
            options.width(5);
            options.color(Color.RED);
            for (int i = 0; i < result.size(); i++) {
                Value v = result.get(i);
                LatLng pos = new LatLng(v.latitude, v.longtitude);
                if (i == result.size() - 1) {
                    map.addMarker(new MarkerOptions().position(pos).title(v.bus.lineName));
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 13));
                }
                options.add(pos);
            }
            map.addPolyline(options);
        } else
            Log.d("tag:", "no data");
    }

}
