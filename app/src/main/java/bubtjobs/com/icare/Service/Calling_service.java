package bubtjobs.com.icare.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.IBinder;
import android.widget.Toast;

import bubtjobs.com.icare.Others.SessionManager;

public class Calling_service extends Service {
    SensorManager manager;
    float accelValue;
    float currentValue;
    float lastValue;
    SessionManager sessionManager;
    @Override
    public void onCreate() {
        super.onCreate();
        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelValue=0.00f;
        currentValue=SensorManager.GRAVITY_EARTH;
        lastValue=SensorManager.GRAVITY_EARTH;
        sessionManager=new SessionManager(Calling_service.this);
       // Toast.makeText(Calling_service.this, "start", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDestroy() {
        manager.unregisterListener(listener);
        super.onDestroy();
        //Toast.makeText(Calling_service.this, "stop", Toast.LENGTH_SHORT).show();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sessionManager=new SessionManager(Calling_service.this);
        manager.registerListener(listener,manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
        return START_STICKY;
    }




    private SensorEventListener listener=new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {

            float x=event.values[0];
            float y=event.values[1];
            float z=event.values[2];

            lastValue=currentValue;

            currentValue= (float) Math.sqrt((double)(x*x+y*y+z*z));

            float delta=currentValue-lastValue;
            accelValue=accelValue*.9f+delta;

            // Toast.makeText(MainActivity.this, String.valueOf(accelValue), Toast.LENGTH_SHORT).show();

            if (accelValue>5){
                //Toast.makeText(MainActivity.this, String.valueOf(accelValue), Toast.LENGTH_SHORT).show();
                Intent callIntent=new Intent(Intent.ACTION_CALL, Uri.parse("tel:" +sessionManager.getEmergencyNumber()));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(callIntent);
            }



        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}