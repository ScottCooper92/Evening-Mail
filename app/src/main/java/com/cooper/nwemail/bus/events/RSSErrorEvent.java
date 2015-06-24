package com.cooper.nwemail.bus.events;

import retrofit.RetrofitError;

/**
 * An Object representing an RSSErrorEvent, thrown when the app failed to update.
 */
public class RSSErrorEvent {

    private RetrofitError error;

    public RSSErrorEvent(final RetrofitError error) {
        this.error = error;
    }

    public RetrofitError getError() {
        return error;
    }

    public void setError(RetrofitError error) {
        this.error = error;
    }
}
