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
import android.util.Log;
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
        // Grab the channel object associated with the selected item.
        Channel channel = (Channel)parent.getItemAtPosition(position);
        // Open up a new activity to edit this channel (TODO make an editing activity)
        Intent intent = new Intent(this, ChannelSelectActivity.class);
        intent.putExtra("channel", channel);
        startActivity(intent);
    }

    /**
     * @see HttpClientJSONResponse#processResponse(JSONObject response)
     */
    @Override
    public void processResponse(JSONObject response) {
        // If no valid response was received, give an error and quit.
        if (response == null) {
            Toast.makeText(getBaseContext(), "Unable to contact server", Toast.LENGTH_LONG).show();
            return;
        }
        // Build a list of channels from the returned JSON
        // object by iterating over the keys in the object.
        ArrayList<Channel> channels = new ArrayList<Channel>();
        for(Iterator<?> iter = response.keys(); iter.hasNext();) {
            String id = (String)iter.next();
            // Construct the channel object by pulling data from JSON.
            try {
                JSONObject aux = (JSONObject)response.get(id);
                String name = aux.getString("name");
                channels.add(new Channel(id, 'A', name));
            }
            // If something goes wrong, just ignore the item.
            catch (JSONException e) {
                Log.w(this.getClass().getName(), "Invalid aux data received from server");
            }
        }
        // Sort the list of channels by identifier, and then shove it into the list widget.
        Collections.sort(channels);
        ArrayAdapter<Channel> adapter = new ArrayAdapter<Channel>(this, android.R.layout.simple_list_item_1, channels);
        channelList.setAdapter(adapter);
    }

}
