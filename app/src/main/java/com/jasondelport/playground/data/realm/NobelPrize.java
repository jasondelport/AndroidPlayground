package com.jasondelport.playground.data.realm;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by jasondelport on 27/05/16.
 */

public class NobelPrize extends RealmObject {
    private int year;
    private String category;
    private RealmList<NobelPrizeLaureate> laureates;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public RealmList<NobelPrizeLaureate> getLaureates() {
        return laureates;
    }

    public void setLaureates(RealmList<NobelPrizeLaureate> laureates) {
        this.laureates = laureates;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
