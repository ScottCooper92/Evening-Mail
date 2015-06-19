package com.cooper.nwemail.application;

import android.app.Application;

import com.cooper.nwemail.bus.AnyThreadBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final NWEApplication app;

    public ApplicationModule(final NWEApplication app) {
        this.app = app;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return app;
    }

    @Provides
    @Singleton
    AnyThreadBus provideBus() {
        return new AnyThreadBus();
    }
}
