package com.singledsoftware.mixmaestro;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ChannelSelectActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_select);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.channel_select, menu);
        return true;
    }
    
}
