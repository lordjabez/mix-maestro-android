// Copyright 2013 Judson D Neer

package com.singledsoftware.mixmaestro;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONObject;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;

/**
 * Background task processing of HTTP JSON requests/responses.
 *
 * @see AsyncTask
 * @author Judson D Neer
 */
class HttpClientJSONTask extends AsyncTask<String, Void, JSONObject> {

    // HTTP client connection object
    private final HttpClient httpClient = AndroidHttpClient.newInstance(null);

    // Object that will handle the response
    private HttpClientJSONResponse clientResponse;

    /**
     * @see android.os.AsyncTask#doInBackground(Object... params)
     *
     * @param urls Contains one item, the URL to be fetched
     */
    @Override
    protected JSONObject doInBackground(String... urls) {
        // Set up the GET object,
        HttpGet getMethod = new HttpGet(urls[0]);
        // Execute the request. If all goes well, get the response and parse it as JSON.
        try {
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String response = httpClient.execute(getMethod, responseHandler);
            return new JSONObject(response);
        }
        // If anything goes wrong (connection fails, non-JSON returned), indicate as much to the caller.
        catch (Throwable t) {
            return null;
        }
        // No matter what, make sure we cleanly close the connection.
        finally {
            httpClient.getConnectionManager().shutdown();
            ((AndroidHttpClient)httpClient).close();
        }
    }

    /**
     * @see android.os.AsyncTask#onPostExecute(Object response)
     *
     * @param response The JSON response object for processing
     */
    @Override
    protected void onPostExecute(JSONObject response) {
        clientResponse.processResponse(response);
    }

    /**
     * Accessor for the client response handling object.
     *
     * @param response Object that will process the HTTP response
     */
    public void setClientResponse(HttpClientJSONResponse response) {
        clientResponse = response;
    }

}
