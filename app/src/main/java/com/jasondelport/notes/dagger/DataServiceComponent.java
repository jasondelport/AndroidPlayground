package com.jasondelport.notes.dagger;

import com.jasondelport.notes.ui.activity.RecyclerViewActivity;

/**
 * Created by jasondelport on 28/07/15.
 */

public interface DataServiceComponent {
    void inject(RecyclerViewActivity activity);
}
