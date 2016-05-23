package com.jasondelport.playground.ui.fragment;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jasondelport.playground.R;
import com.jasondelport.playground.util.TelephonyUtils;
import com.tbruyelle.rxpermissions.RxPermissions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import timber.log.Timber;


public class TelephonyFragment extends BaseFragment {

    @BindView(R.id.output1)
    TextView output1;

    @BindView(R.id.output2)
    TextView output2;

    @BindView(R.id.output3)
    TextView output3;

    private ConnectivityManager connectivityManager;
    ConnectivityManager.NetworkCallback mNetworkCallback = new ConnectivityManager.NetworkCallback() {
        @Override
        public void onAvailable(Network network) {
            Timber.d("onAvailable -> %s", network.toString());
            NetworkInfo networkInfo = connectivityManager.getNetworkInfo(network);
            Timber.d("onAvailable -> %s", networkInfo.toString());

            getActivity().runOnUiThread(new Runnable() {
                public void run(){
                    output1.setText(networkInfo.toString());
                }
            });
        }

        @Override
        public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
            NetworkInfo networkInfo = connectivityManager.getNetworkInfo(network);
            Timber.d("onCapabilitiesChanged -> %s", network.toString());
            Timber.d("onCapabilitiesChanged -> capabilities -> %s", networkCapabilities.toString());
            //output1.setText(network.toString());
            getActivity().runOnUiThread(new Runnable() {
                public void run(){
                    output1.setText(networkInfo.toString());
                    output2.setText(networkCapabilities.toString());
                }
            });
        }

        @Override
        public void onLosing(Network network, int maxMsToLive) {
            Timber.d("onLosing -> %s", network.toString());
            //output1.setText(network.toString());
        }

        @Override
        public void onLost(Network network) {
            Timber.d("onLost -> %s", network.toString());
            //output1.setText(network.toString());
        }
    };
    private Unbinder unbinder;

    public static Fragment newInstance() {
        Fragment fragment = new TelephonyFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setRetainInstance(true);
        this.setMenuVisibility(false);
        this.setHasOptionsMenu(false);

        RxPermissions.getInstance(getActivity())
                .request(Manifest.permission.PROCESS_OUTGOING_CALLS,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.CHANGE_NETWORK_STATE)
                .subscribe(granted -> {
                    if (granted) {
                        // All requested permissions are granted
                    } else {
                        // At least one permission is denied
                    }
                });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_telephony, container, false);
        unbinder = ButterKnife.bind(this, view);

        connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkRequest.Builder builder = new NetworkRequest.Builder();

        builder.addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
        builder.addTransportType(NetworkCapabilities.TRANSPORT_WIFI);
        builder.addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR);

        NetworkRequest networkRequest = builder.build();
        connectivityManager.requestNetwork(networkRequest, mNetworkCallback);
        connectivityManager.registerNetworkCallback(networkRequest, mNetworkCallback);

        return view;
    }

    @OnClick(R.id.button_telephony_end)
    void loadEndCall() {
        TelephonyUtils.silenceCall(getActivity());
    }

    @OnClick(R.id.button_telephony_silence)
    void loadSilenceCall() {
        TelephonyUtils.endCall(getActivity());
    }

    @OnClick(R.id.button_ringer_normal)
    void loadNormalRinger() {
        boolean isSet = TelephonyUtils.normalRinger(getActivity());
        Timber.d("Ringer set to normal -> %b", isSet);
    }

    @OnClick(R.id.button_ringer_silence)
    void loadSilentRinger() {
        boolean isSet = TelephonyUtils.silentRinger(getActivity());
        Timber.d("Ringer set to silent -> %b", isSet);
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }


    @Override
    public void onResume() {
        output3.setText(TelephonyUtils.ringerStatus(getActivity()));
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
