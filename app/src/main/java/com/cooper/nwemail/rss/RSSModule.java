package com.cooper.nwemail.rss;

import com.cooper.nwemail.bus.AnyThreadBus;
import com.cooper.nwemail.data.DataManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * TODO
 */
@Module
public final class RSSModule {

    @Provides
    @Singleton
    RSSClient provideApiClient(final DataManager dataManager, final AnyThreadBus bus) {
        return new RSSClient(dataManager, bus);
    }
}
