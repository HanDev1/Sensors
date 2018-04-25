package com.example.a2hanj43.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity implements SensorEventListener {


    Sensor accel, magField;

    float[] accelValues, magFieldValues, orientationMatrix, orientations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //obtain sensor manager using a given context (activity)
        SensorManager sMgr = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        //obtain a sensor (accelerometer)
        accel = sMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        magField = sMgr.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        //sMgr.registerListener(sensorEventListener,accel, SensorManager.SENSOR_DELAY_UI);


        sMgr.registerListener(this, accel, SensorManager.SENSOR_DELAY_UI);
        sMgr.registerListener(this, magField,SensorManager.SENSOR_DELAY_UI);

        accelValues = new float[3];
        magFieldValues = new float[3];
        orientationMatrix = new float[16];// combination of rotation and translation need 4 by 4 matrix
        orientations = new float[3];

    }


    public void onSensorChanged(SensorEvent ev){
        DecimalFormat df = new DecimalFormat("#.##");
        TextView xacc = (TextView)findViewById(R.id.xacc);
        TextView yacc = (TextView)findViewById(R.id.yacc);
        TextView zacc = (TextView)findViewById(R.id.zacc);
        TextView match = (TextView)findViewById(R.id.match);

        //values is an array of the acceleration values in the axes
//        xacc.setText(df.format(ev.values[0]));
//        yacc.setText(df.format(ev.values[1]));
//        zacc.setText(df.format(ev.values[2]));
        if(ev.sensor == accel)
        {
            for (int i = 0; i<3; i++){
                accelValues[i] = ev.values [i];//ev.values.clone();//.clone() makes a copy of array but is memory inefficient
            }

        }
        else if (ev.sensor == magField)
        {
            for (int i = 0; i<3; i++){
                magFieldValues[i] = ev.values [i];//ev.values.clone();//.clone() makes a copy of array but is memory inefficient
            }



        }

        if (ev.values[0] > 100 && ev.values[0] < 150) {
            Log.d("sensors", "there is a match");
            System.out.println("match");
            match.setText("match");

        }

        SensorManager.getRotationMatrix(orientationMatrix, null, accelValues, magFieldValues);
        SensorManager.getOrientation(orientationMatrix, orientations);

        xacc.setText(df.format(orientations[0] * 180/Math.PI));
        yacc.setText(df.format(orientations[1] * 180/Math.PI));
        zacc.setText(df.format(orientations[2] * 180/Math.PI));



    }

    public void onAccuracyChanged(Sensor sensor, int acc){

    }
}
