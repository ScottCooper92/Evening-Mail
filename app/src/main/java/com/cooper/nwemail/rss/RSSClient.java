package com.cooper.nwemail.rss;

import com.cooper.nwemail.bus.AnyThreadBus;
import com.cooper.nwemail.bus.events.RSSErrorEvent;
import com.cooper.nwemail.bus.events.RSSSuccessEvent;
import com.cooper.nwemail.data.DataManager;
import com.cooper.nwemail.models.RSSFeed;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.transform.RegistryMatcher;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.SimpleXMLConverter;

/**
 * TODO
 */
public class RSSClient {

    private DataManager mData;
    private AnyThreadBus mBus;
    private RSSAPI mAPI;

    @Inject
    public RSSClient(final DataManager dataManager, final AnyThreadBus bus) {
        mData = dataManager;
        mBus = bus;

        //We need to create a custom date transformer
        final DateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
        final RegistryMatcher m = new RegistryMatcher();
        m.bind(Date.class, new DateFormatTransformer(format));
        final Serializer ser = new Persister(m);

        mAPI = new RestAdapter.Builder()
                .setEndpoint(RSSAPI.ENDPOINT)
                .setConverter(new SimpleXMLConverter(ser))
                .build()
                .create(RSSAPI.class);
    }


    /**
     * Retrieves and saves an RSS feed that can be displayed in a list.
     */
    public void getRSSFeed(final String feedId) {

        /** Get the RSS Feed **/
        final Callback<RSSFeed> rssCallback = new Callback<RSSFeed>() {
            @Override
            public void success(RSSFeed rssFeed, Response response) {

                //Pass the data to the DataManager
                mData.parseArticles(rssFeed, feedId);

                //Broadcast success
                mBus.post(new RSSSuccessEvent(response));
            }

            @Override
            public void failure(RetrofitError error) {
                //Broadcast a failure
                mBus.post(new RSSErrorEvent(error));
            }
        };

        mAPI.getRSSFeed(feedId, rssCallback);
    }
}
