// Copyright 2013 Judson D Neer

package com.singledsoftware.mixmaestro;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;

/**
 * Provides channel selection for the Mix Maestro client application.
 *
 * @see ScoresheetActivity
 * @author Judson D Neer
 */
public class ChannelSelectActivity extends Activity {

    // Stores a reference to the channel list widget.
    private ListView channelList;

    /**
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_select);
        // Grab a reference to a view widget.
        channelList = (ListView)this.findViewById(R.id.channel_list);
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

}
