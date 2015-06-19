package com.cooper.nwemail.bus.events;

import retrofit.client.Response;

/**
 * TODO
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
