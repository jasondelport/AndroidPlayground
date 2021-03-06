package com.jasondelport.playground.ui.fragment;

import android.app.Fragment;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.jasondelport.playground.R;
import com.jasondelport.playground.data.realm.NobelPrize;
import com.jasondelport.playground.data.realm.NobelPrizeLaureate;
import com.jasondelport.playground.data.realm.NobelPrizes;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import timber.log.Timber;


public class RealmFragment extends BaseFragment {

    @BindView(R.id.output1)
    TextView output1;
    private Realm realm;
    private Unbinder unbinder;
    private RealmConfiguration realmConfiguration;

    public static Fragment newInstance() {
        Fragment fragment = new RealmFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setRetainInstance(true);
        this.setMenuVisibility(false);
        this.setHasOptionsMenu(false);

        realmConfiguration = new RealmConfiguration.Builder(getActivity()).build();
        //Realm.deleteRealm(realmConfiguration);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_realm, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.button_load)
    void load() {
        loadRealmDatabase();
    }

    @OnClick(R.id.button_search)
    void search() {
        searchRealmDatabase();
    }

    @OnClick(R.id.button_delete)
    void delete() {
        deleteRealmDatabase();
    }

    @OnClick(R.id.button_backup)
    void backup() {
        backupRealmDatabase();
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    private void loadRealmDatabase() {
        try {
            AssetManager assetManager = getResources().getAssets();
            /*
            String[] files = assetManager.list("");
            for (int i = 0; i < files.length; i++) {
                Timber.d("file -> %s", files[i]);
            }
            */
            String output = "Loading data:\n";
            InputStream stream = assetManager.open("prize.json");
            Gson gson = new GsonBuilder().create();
            JsonElement json = new JsonParser().parse(new InputStreamReader(stream));
            // For when GSON rejects the RealmObject as a wrong TypeToken
            //List<City> cities = gson.fromJson(json, new TypeToken<List<City>>() {}.getType());
            NobelPrizes prizes = gson.fromJson(json, NobelPrizes.class);
            Timber.d("Prizes -> %d", prizes.getNobelPrizes().size());
            output += "Size from JSON = " + prizes.getNobelPrizes().size() + "\n";
            realm.beginTransaction();
            Collection<NobelPrize> realmNobelPrizes = realm.copyToRealm(prizes.getNobelPrizes());
            realm.commitTransaction();
            output += "Total size from Realm = " + realm.where(NobelPrize.class).count() + "\n";
            /*
            List<NobelPrize> nobelPrizes = new ArrayList<>(realmNobelPrizes);
            for (NobelPrize prize : nobelPrizes) {
                Timber.d("Year: %d Category: %s", prize.getYear(), prize.getCategory());
            }
            */
            output1.setText(output);
        } catch (Exception ex) {
            Timber.e(ex, "Error -> %s", ex.getMessage());
        }
    }

    private void searchRealmDatabase() {
        RealmResults<NobelPrize> nps = realm.where(NobelPrize.class).equalTo("year", 2000).findAll();
        Timber.d("Realm Results: %d", nps.size());
        String output = "Data: \n";
        for (NobelPrize prize : nps) {
            output += prize.getYear() + " | " + prize.getCategory() + " | " + prize.getLaureates().size() + " laureate(s)" + "\n";
            for (NobelPrizeLaureate laureate : prize.getLaureates()) {
                output += laureate.getFirstname() + " " + laureate.getSurname();
                output += " -> " + laureate.getMotivation().substring(1, laureate.getMotivation().length() - 1) + "\n";
            }
            output += "\n";
        }
        output1.setText(output);
    }

    private void deleteRealmDatabase() {
        try {
            realm.beginTransaction();
            realm.deleteAll();
            realm.commitTransaction();
            output1.setText("Data deleted");
        } catch (Exception ex) {
            Timber.e(ex, "Error -> %s", ex.getMessage());
        }
    }

    public void backupRealmDatabase() {
        //https://github.com/realm/realm-java/issues/1305
        // adb pull /storage/emulated/0/Android/data/com.jasondelport.playground/files/ .
        try {
            File path = getActivity().getExternalFilesDir(null);
            String name = "default.realm";
            File file = new File(path, name);
            Timber.i("Backup path -> %s/%s", path, name);
            // if backup file already exists, delete it
            if (file.exists()) file.delete();
            output1.setText("Copied to SD card");
            realm.writeCopyTo(file);
        } catch (Exception ex) {
            Timber.e(ex, "Error -> %s", ex.getMessage());
        }

    }


    @Override
    public void onResume() {
        super.onResume();
        realm = Realm.getInstance(realmConfiguration);
        Timber.i("Realm path -> %s", realm.getPath());
    }

    @Override
    public void onPause() {
        realm.close();
        super.onPause();
    }
}
