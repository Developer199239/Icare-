package bubtjobs.com.icare.Activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import bubtjobs.com.icare.R;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlarmDisplay extends AppCompatActivity {
    @Bind(R.id.alarmType) TextView alarmType;
    @Bind(R.id.personName) TextView personName;
    @Bind(R.id.date) TextView date;
    @Bind(R.id.time) TextView time;
    @Bind(R.id.details) TextView details;

    final CounterClass timer=new CounterClass(60000,1000);

    Uri uriAlarm;
    Ringtone ringTone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_display);
        ButterKnife.bind(this);



        alarmType.setText("Alarm");
        personName.setText("Name: Jaliur rahman");
        date.setText("Date: 21/04/2016");
        time.setText("Time: 02:10 PM");
        details.setText("Ditails: Breakfast");

        uriAlarm=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if(ringTone!=null){
            ringTone.stop();
        }
        ringTone = RingtoneManager.getRingtone(getApplicationContext(), uriAlarm);
        ringTone.play();
        timer.start();

    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressLint("NowApi")

    public class CounterClass extends CountDownTimer {
        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @SuppressLint("NowApi")
        @TargetApi(Build.VERSION_CODES.GINGERBREAD)
        @Override
        public void onTick(long millisUntilFinished) {


        }

        @Override
        public void onFinish() {
            if(ringTone!=null){
                ringTone.stop();
            }
        }
    }

    @OnClick(R.id.done)
    public void alarmDone(){
        Toast.makeText(AlarmDisplay.this, "done", Toast.LENGTH_SHORT).show();
       timer.cancel();
        if(ringTone!=null){
            ringTone.stop();
        }
    }
}
