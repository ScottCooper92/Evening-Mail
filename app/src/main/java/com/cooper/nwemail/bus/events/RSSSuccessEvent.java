package com.cooper.nwemail.bus.events;

import retrofit.client.Response;

/**
 * An Object representing an RSS Success event, let's listeners know that data has been downloaded and provides them with it
 */
public class RSSSuccessEvent {

    private Response response;

    public RSSSuccessEvent(final Response response) {
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }

}
