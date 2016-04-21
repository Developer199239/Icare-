package bubtjobs.com.icare.Alarm_Manager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;

import bubtjobs.com.icare.Activity.MainActivity;

/**
 * Created by Murtuza on 4/21/2016.
 */
public class Alarm {
    AlarmManager alarmManager;
    Context context;
    private int alarmCode;
    public Alarm(Context context){
        this.context=context;
        alarmManager= (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
    }

    public void setAlarm(Long millis,int alarmCode){
       // Long millis=Long.parseLong("5000");
        alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+millis,createClockTickIntent(context,alarmCode));
    }

    private PendingIntent createClockTickIntent(Context context,int alarmCode) {
        Intent intent = new Intent("CUSTOM_ACTION");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarmCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }

}
