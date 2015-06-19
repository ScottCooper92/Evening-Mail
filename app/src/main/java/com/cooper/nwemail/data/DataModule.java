package com.cooper.nwemail.data;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {

    private final Application application;

    public DataModule(final Application application) {
        this.application = application;
    }

    /*@Provides
    @Singleton
    Realm provideRealm() {
        return Realm.getInstance(application);
    }*/

    @Provides
    @Singleton
    DataManager provideDataManager() {
        return new DataManager(application);
    }
}
