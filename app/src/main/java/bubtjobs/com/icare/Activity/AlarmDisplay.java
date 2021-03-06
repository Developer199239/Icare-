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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import bubtjobs.com.icare.DataBase.DataBaseManager;
import bubtjobs.com.icare.Model.Diet;
import bubtjobs.com.icare.Model.Vaccination;
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
    DataBaseManager manager;
    Diet diet;
    Vaccination vaccination;
    boolean isring=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_display);
        ButterKnife.bind(this);
        manager=new DataBaseManager(this);
        diet=new Diet();
        vaccination=new Vaccination();
        Intent intent=getIntent();

        String table=intent.getStringExtra("table");
        String tableId=intent.getStringExtra("tableId");
        String userId=intent.getStringExtra("userId");

        Log.i("tableId", table + tableId + userId);

        String name=manager.getPersonName(userId);

        String alarm_Type="";
        String alarm_Date="";
        String alarm_time="";
        String alarm_details="";
        if(table.equals("diet")) {
            diet = manager.getSingleDiet(tableId);
            if (diet != null) {
                alarm_Type = diet.getDietType();
                alarm_Date = diet.getDietDate();
                alarm_time = diet.getDietTime();
                alarm_details = diet.getMenu();
            }
        }
        else if(table.equals("vaccination"))
        {
           vaccination=manager.getSingleVaccination(tableId);
            if (vaccination != null) {
                alarm_Type = vaccination.getAlarmType();
                alarm_Date = vaccination.getDate();
                alarm_time = vaccination.getTime();
                alarm_details = vaccination.getDetails();
            }
        }

        alarmType.setText(alarm_Type);
        personName.setText("Name: "+name);
        date.setText("Date: "+alarm_Date);
        time.setText("Time: "+alarm_time);
        details.setText("Details: "+alarm_details);

        uriAlarm=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if(ringTone!=null){
            ringTone.stop();
        }
            ringTone = RingtoneManager.getRingtone(getApplicationContext(), uriAlarm);
            ringTone.play();
            timer.start();
            isring=false;


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
        //Toast.makeText(AlarmDisplay.this, "done", Toast.LENGTH_SHORT).show();
        finish();
       timer.cancel();
        if(ringTone!=null){
            ringTone.stop();
        }
    }

    @Override
    protected void onPause() {
        if(ringTone!=null){
            ringTone.stop();
        }
        super.onPause();
    }
}
