package com.jasondelport.notes.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jasondelport on 13/04/15.
 */
public class Locations {

    private static List<CustomLocation> locations = new ArrayList<>();

    public static void createLocations() {
        CustomLocation sainsburys = new CustomLocation("Sainsburys");
        sainsburys.setLatitudeLongitude(51.540161, -0.141069);

        CustomLocation mixer = new CustomLocation("Mixer");
        mixer.setLatitudeLongitude(51.539354, -0.144537);

        CustomLocation zensai = new CustomLocation("Zen Sai");
        zensai.setLatitudeLongitude(51.539610, -0.144025);

        CustomLocation diner = new CustomLocation("Diner");
        diner.setLatitudeLongitude(51.539610, -0.144025);

        CustomLocation myjol = new CustomLocation("My Jol");
        myjol.setLatitudeLongitude(51.540541, -0.138873);

        CustomLocation wagamama = new CustomLocation("Wagamama");
        wagamama.setLatitudeLongitude(51.540285, -0.145504);

        CustomLocation camdentown = new CustomLocation("Camden Town Tube");
        camdentown.setLatitudeLongitude(51.539120, -0.142617);

        CustomLocation inverness = new CustomLocation ("Top of Inverness Street");
        inverness.setLatitudeLongitude(51.539740, -0.143348);

        locations.add(sainsburys);
        locations.add(mixer);
        locations.add(zensai);
        locations.add(wagamama);
        locations.add(myjol);
        locations.add(diner);
        locations.add(camdentown);
        locations.add(inverness);
    }

    public Locations() {

    }

    public static List<CustomLocation> getLocations() {
        createLocations();
        return locations;
    }
}
