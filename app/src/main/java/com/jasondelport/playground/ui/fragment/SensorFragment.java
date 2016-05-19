package com.jasondelport.playground.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jasondelport.playground.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;


public class SensorFragment extends BaseFragment implements SensorEventListener {

    @BindView(R.id.output1)
    TextView output1;

    @BindView(R.id.output2)
    TextView output2;

    private Unbinder unbinder;
    private SensorManager mSensorManager;
    private Sensor mSensor;

    public static Fragment newInstance() {
        Fragment fragment = new SensorFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setRetainInstance(true);
        this.setMenuVisibility(false);
        this.setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sensor, container, false);
        unbinder = ButterKnife.bind(this, view);

        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        String sensors = "";
        for (int i = 0; i < sensorList.size(); i++) {
            Sensor sensor =  sensorList.get(i);
            sensors += sensor.getName() + "\n";
        }

        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        output1.setText(sensors);



        return view;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        String data = "";
        if (sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            data += "ACCELERATION\n";
            float[] values = event.values;
            for (int i = 0; i < values.length; i++) {
                data += values[i] + "\n";
            }
        }
        output2.setText(data);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Timber.d("Sensor -> %s", sensor.getName());
        Timber.d("Sensor -> %s", sensor.toString());
        Timber.d("accuracy -> %d", accuracy);
    }

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
}
