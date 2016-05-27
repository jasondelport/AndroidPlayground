package com.jasondelport.playground.data.realm;

import io.realm.RealmObject;

/**
 * Created by jasondelport on 27/05/16.
 */
public class NobelPrizeLaureate extends RealmObject {
    private String firstname;
    private String surname;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
