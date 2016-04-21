package bubtjobs.com.icare.Broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import bubtjobs.com.icare.Activity.AlarmDisplay;
import bubtjobs.com.icare.Alarm_Manager.WakeLocker;

public class Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String state = intent.getExtras().getString("table_name");
        WakeLocker.acquire(context);
        Intent intent1=new Intent(context,AlarmDisplay.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent1.putExtra("table_name", state);
        context.startActivity(intent1);
        WakeLocker.release();
    }
}
