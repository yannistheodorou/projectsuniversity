package com.ds.katanem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends Activity {
    ArrayList<Topic> topics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        topics = new ArrayList<Topic>();
        topics.add(new Topic("1151", "021", "PLATEIA KANIGKOS - GKIZI"));
        topics.add(new Topic("750", "024", "AG. ANARGYROI - ST. KATO PATHSIA"));
        topics.add(new Topic("821", "022", "PLATEIA KANIGKOS - GKIZI"));
        topics.add(new Topic("817", "025", "IPPOKRATOUS - PROFITI DANIIL"));
        topics.add(new Topic("818", "026", "IPPOKRATOUS - VOTANIKOS"));
        topics.add(new Topic("974", "027", "IPPOKRATOUS - ORFEOS"));
        topics.add(new Topic("1113", "032", "GOUDI-MARASLIOS"));
        topics.add(new Topic("816", "035", "ANO KYPSELI - PETRALONA - TAVROS"));
        topics.add(new Topic("804", "036", "KATECHAKI - KYPSELI"));
        topics.add(new Topic("1219", "036", "KATECHAKI - KYPSELI"));
        topics.add(new Topic("1220", "036", "KATECHAKI - KYPSELI"));
        topics.add(new Topic("938", "040", "PEIRAIAS - SINTAGMA"));
        topics.add(new Topic("831", "046", "MOUSEIO - ELLINOROSON"));
        topics.add(new Topic("819", "049", "PEIRAIAS - OMONOIA"));
        topics.add(new Topic("1180", "051", "OMONOIA - ST. YPERAST. LEOF. KIFISOU"));
        topics.add(new Topic("868", "054", "PERISSOS - AKADIMIA METAMORFOSI"));
        topics.add(new Topic("824", "057", "OMONOIA - LOFOS SKOUZE"));
        topics.add(new Topic("825", "060", "MOUSEIO - AKADIMIA - LYKAVITTOS"));
        topics.add(new Topic("1069", "1", "PLATEIA ATTIKIS - TZITZIFIES - MOSCHATO"));
        topics.add(new Topic("1077", "10", "TZITZIFIES - XALANDRI"));

        ArrayAdapter<Topic> adapter = new ArrayAdapter<Topic>(this, R.layout.listview_layout, topics.toArray(new Topic[topics.size()]));
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                //Publisher pub = new Publisher();
                //pub.execute(topics.get(position));
                Log.e("CLICK", position + "");

                Intent intent = new Intent(parent.getContext(), MapsActivity.class);

                intent.putExtra("topic", topics.get(position));
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
