package com.jasondelport.notes.dagger;


import com.jasondelport.notes.data.server.DataService;

import dagger.Module;
import dagger.Provides;

@Module
public class DataServiceModule {

    @Provides
    DataService providesDataService() {
        return new DataService();
    }
}

