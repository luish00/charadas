package com.cafeinelabs.charadas.utils;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class Accelerometer extends Activity implements SensorEventListener{

	private float curX = 0;
	private float curY = 0;
	private float curZ = 0;
	
	SensorManager manager;
	Sensor accelerometer;
	Activity activity;
	
	public Accelerometer(Activity activity){
		this.activity = activity;
        manager = (SensorManager) this.activity.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = manager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
        manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		synchronized (this) {
			curX = event.values[0];
			curY = event.values[1];
			curZ = event.values[2];
		}	
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {		
	}

	public float getCurZ() {
		return curZ;
	}
}
