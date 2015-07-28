package com.jasondelport.notes.dagger;

/**
 * Created by jasondelport on 28/07/15.
 */

import dagger.Component;

@Component(modules = DataServiceModule.class)
public interface ApplicationComponent extends DataServiceComponent {
}
