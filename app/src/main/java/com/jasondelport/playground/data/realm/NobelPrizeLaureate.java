package com.jasondelport.playground.data.realm;

import io.realm.RealmObject;

/**
 * Created by jasondelport on 27/05/16.
 */
public class NobelPrizeLaureate extends RealmObject {
    private int id;
    private String firstname;
    private String surname;
    private String motivation;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMotivation() {
        return motivation;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }
}
