// Copyright 2013 Judson D Neer

package com.singledsoftware.mixmaestro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Provides channel selection for the Mix Maestro client application.
 *
 * @see Activity
 * @see OnItemClickListener
 * @see HttpClientJSONResponse
 * @author Judson D Neer
 */
public class ChannelSelectActivity extends Activity implements OnItemClickListener, HttpClientJSONResponse {

    // URL for fetching data (TODO move base URL to a configuration item somewhere)
    private final String auxesUrl = "http://hobbiton:8080/auxes";

    // Helper object for handling background HTTP requests.
    private final HttpClientJSONTask httpClientTask = new HttpClientJSONTask();

    // Stores a reference to the channel list widget.
    private ListView channelList;

    /**
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_select);
        // Grab a reference to the channel list and set up its event handler.
        channelList = (ListView)this.findViewById(R.id.channel_list);
        channelList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        channelList.setOnItemClickListener(this);
        // Set the HTTP response callback, and then execute the call to populate the channel list.
        httpClientTask.setClientResponse(this);
        httpClientTask.execute(auxesUrl);
    }

    /**
     * @see android.app.Activity#onCreateOptionsMenu(Menu menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.channel_select, menu);
        return true;
    }

    /**
     * @see android.widget.AdapterView.OnItemClickListener#onItemClick(AdapterView<?>, View, int, long)
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Channel channel = (Channel)parent.getItemAtPosition(position);
        Intent intent = new Intent(this, ChannelSelectActivity.class);  // TODO change to the proper new activity for channel selection
        intent.putExtra("channel", channel);
        startActivity(intent);
    }

    /**
     * @see HttpClientJSONResponse#processResponse(JSONObject response)
     */
    @Override
    public void processResponse(JSONObject response) {
        if (response == null) {
            Toast.makeText(getBaseContext(), "Unable to contact Mix Maestro server", Toast.LENGTH_LONG).show();
            return;
        }
        ArrayList<Channel> channels = new ArrayList<Channel>();
        for(Iterator<?> iter = response.keys(); iter.hasNext();) {
            String id = (String)iter.next();
            try {
                JSONObject aux = (JSONObject)response.get(id);
                char type = 'A';
                String name = aux.getString("name");
                channels.add(new Channel(id, type, name));
            } catch (JSONException e) {}
            Collections.sort(channels);
        }
        ArrayAdapter<Channel> adapter = new ArrayAdapter<Channel>(this, android.R.layout.simple_list_item_1, channels);
        channelList.setAdapter(adapter);
    }

}
