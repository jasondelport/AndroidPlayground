package com.jasondelport.playground.ui.activity;

import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import com.jasondelport.playground.ui.fragment.CameraFragment;
import com.jasondelport.playground.util.NavUtils;
import com.tbruyelle.rxpermissions.RxPermissions;

import timber.log.Timber;


public class CameraActivity extends BaseActivity {

    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RxPermissions.getInstance(this)
                .request(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {
                        Timber.d("Permission granted");
                    } else {
                        Timber.d("Permission denied");
                        // At least one permission is denied
                    }
                });

        if (savedInstanceState == null) {
            fragment = CameraFragment.newInstance();
        } else {
            fragment = getFragmentManager().getFragment(savedInstanceState, "fragment");
        }

        NavUtils.addFragment(getFragmentManager(), fragment, android.R.id.content, "CameraFragment");

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
       super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getFragmentManager().putFragment(outState, "fragment", fragment);
    }

}
