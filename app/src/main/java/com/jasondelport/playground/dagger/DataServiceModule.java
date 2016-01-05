package com.jasondelport.playground.dagger;


import com.jasondelport.playground.data.server.DataService;

import dagger.Module;
import dagger.Provides;

@Module
public class DataServiceModule {

    @Provides
    DataService providesDataService() {
        return new DataService();
    }
}

