package com.cooper.nwemail.rss;

import com.cooper.nwemail.models.RSSFeed;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * An interface listing the endpoints used within this application
 */
public interface RSSAPI {

    String ENDPOINT = "http://www.nwemail.co.uk";

    @GET("/se/{feedId}")
    void getRSSFeed(@Path("feedId") final String id, final Callback<RSSFeed> callback);

}
