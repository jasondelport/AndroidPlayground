package com.jasondelport.playground.data.model;

import com.jasondelport.playground.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jasondelport on 13/04/15.
 */
public class LocationManager {

    private static LocationManager sInstance;
    private List<CustomLocation> locations = new ArrayList<>();

    public static LocationManager getInstance() {
        if (sInstance == null) {
            sInstance = getSynchronizedInstance();
        }
        return sInstance;
    }

    private static synchronized LocationManager getSynchronizedInstance() {
        if (sInstance == null) {
            sInstance = new LocationManager();
        }
        return sInstance;
    }

    public static boolean isNull() {
        if (sInstance == null) {
            return true;
        }
        return false;
    }

    public void createLocations() {
        CustomLocation sainsburys = new CustomLocation("Sainsburys");
        sainsburys.setLatitudeLongitude(51.540161, -0.141069);
        sainsburys.setAudio(R.raw.bikehorn);

        CustomLocation mixer = new CustomLocation("Mixer");
        mixer.setLatitudeLongitude(51.539389, -0.144506);
        mixer.setAudio(R.raw.farts);

        CustomLocation zensai = new CustomLocation("Zen Sai");
        zensai.setLatitudeLongitude(51.539550, -0.144033);
        zensai.setAudio(R.raw.bikehorn);

        CustomLocation diner = new CustomLocation("Diner");
        diner.setLatitudeLongitude(51.540623, -0.144609);
        diner.setAudio(R.raw.yourmom);

        CustomLocation myjol = new CustomLocation("My Jol");
        myjol.setLatitudeLongitude(51.540541, -0.138873);
        myjol.setAudio(R.raw.bikehorn);

        CustomLocation wagamama = new CustomLocation("Wagamama");
        wagamama.setLatitudeLongitude(51.540285, -0.145504);
        wagamama.setAudio(R.raw.farts);

        CustomLocation camdentown = new CustomLocation("Camden Town Tube");
        camdentown.setLatitudeLongitude(51.539136, -0.142625);
        camdentown.setAudio(R.raw.yourmom);

        CustomLocation inverness = new CustomLocation("Top of Inverness Street");
        inverness.setLatitudeLongitude(51.539726, -0.143530);
        inverness.setAudio(R.raw.bikehorn);

        locations.add(sainsburys);
        locations.add(mixer);
        locations.add(zensai);
        locations.add(wagamama);
        locations.add(myjol);
        locations.add(diner);
        locations.add(camdentown);
        locations.add(inverness);
    }

    public List<CustomLocation> getLocations() {
        if (locations.size() == 0) {
            createLocations();
        }
        return locations;
    }
}
