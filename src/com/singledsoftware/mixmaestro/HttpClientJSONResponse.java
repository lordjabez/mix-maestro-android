// Copyright 2013 Judson D Neer

package com.singledsoftware.mixmaestro;

import org.json.JSONObject;

/**
 * Simple interface for handling HTTP JSON responses.
 *
 * @author Judson D Neer
 */
public interface HttpClientJSONResponse {

    void processResponse(JSONObject response);

}
