package com.cooper.nwemail.application;

import com.cooper.nwemail.bus.AnyThreadBus;
import com.cooper.nwemail.data.DataManager;
import com.cooper.nwemail.data.DataModule;
import com.cooper.nwemail.rss.RSSClient;
import com.cooper.nwemail.rss.RSSModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                ApplicationModule.class, RSSModule.class, DataModule.class
        }
)
public interface ApplicationComponent {
    void inject(final NWEApplication nweApplication);

    AnyThreadBus bus();

    RSSClient rssClient();

    DataManager dataManager();

    final class Initialiser {
        private Initialiser() {
        }

        static ApplicationComponent init(final NWEApplication nweApplication) {
            return DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(nweApplication))
                    .rSSModule(new RSSModule())
                    .dataModule(new DataModule(nweApplication))
                    .build();
        }
    }
}
