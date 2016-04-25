package bubtjobs.com.icare.Fragment;


import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.TimeZone;

import bubtjobs.com.icare.Alarm_Manager.Alarm;
import bubtjobs.com.icare.DataBase.DataBaseManager;
import bubtjobs.com.icare.Model.Vaccination;
import bubtjobs.com.icare.Others.CommonFunction;
import bubtjobs.com.icare.Others.SessionManager;
import bubtjobs.com.icare.R;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Add_Vaccination extends Fragment {

    @Bind(R.id.vaccination_Com) Spinner vaccination_Com;
    @Bind(R.id.va_detatils_Et) EditText va_detatils_Et;
    @Bind(R.id.date_bt) Button date_bt;
    @Bind(R.id.time_bt) Button time_bt;
    @Bind(R.id.va_reminder_Cb) CheckBox va_reminder_Cb;

    int H=0,M=0;
    String YY,MM,DD,TimeFormat;

    CommonFunction function;
    SessionManager sessionManager;
    Vaccination va_input;
    DataBaseManager manager;
    Alarm alarm;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_add__vaccination, container, false);
        ButterKnife.bind(this,view);
        inti();
        return view;
    }

    private void inti() {
        function=new CommonFunction();
        sessionManager=new SessionManager(getActivity());
        manager=new DataBaseManager(getActivity());
        alarm=new Alarm(getActivity());
    }

    @OnClick(R.id.add_va_bt)
    public void add_vaccination(){
        if(function.isEmpty(va_detatils_Et) && function.checkButtonValue(date_bt,"Pick Date") && function.checkButtonValue(time_bt,"Pick Time") && va_reminder_Cb.isChecked())
        {
            String va_name=vaccination_Com.getSelectedItem().toString();
            String va_details=va_detatils_Et.getText().toString();
            String date=YY+MM+DD;

            Long value=function.validAlarm(YY, MM, DD, TimeFormat, H, M);
            if(value==-1)
            {
                Toast.makeText(getActivity(), "Invalid date and time selection", Toast.LENGTH_SHORT).show();
            }
            else{

                String userId=sessionManager.getCurrentPersonId();
                String hour=String.valueOf(H);
                String minute=String.valueOf(M);
                int alarmcode=function.alarmCodeGenerate();

                va_input=new Vaccination(userId,va_name,date,hour,minute,TimeFormat,va_details,"Reminder",""+alarmcode,"1");
                boolean isinsert=manager.addVaccination(va_input);
                if(isinsert)
                {

                    String currentPersonId=sessionManager.getCurrentPersonId();
                    String table="vaccination";
                    String dietId=""+manager.lastIndex(table);

                    alarm.setAlarm(value,alarmcode,table,dietId,currentPersonId);
                    Toast.makeText(getActivity(), "Data Insert Successfully", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getActivity(), "Error...", Toast.LENGTH_SHORT).show();
                }


            }
        }
        else{
            Toast.makeText(getActivity(), "Please Insert All Fields", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.cancel_bt)
    public void cancel(){
        GeneralInfo currentFragment=new GeneralInfo();
        FragmentManager manager=getFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.replace(R.id.homeFragment,currentFragment);
        transaction.commit();
    }

    @OnClick(R.id.date_bt)
    public void setDate(){
        Calendar cal = Calendar.getInstance(TimeZone.getDefault()); // Get current date
        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), datePickerListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH));
        datePicker.setCancelable(false);
        datePicker.setTitle("Select the date");
        datePicker.show();
    }
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,int selectedMonth, int selectedDay) {

            String year1 = String.valueOf(selectedYear);
            String  month1 = String.valueOf(selectedMonth + 1);
            String day1 = String.valueOf(selectedDay);

            if(month1.length()==1)
                month1="0"+month1;
            if(day1.length()==1)
                day1="0"+day1;

            YY=year1;
            MM=month1;
            DD=day1;
            date_bt.setText(DD+"/"+MM+"/"+YY);
        }
    };
    @OnClick(R.id.time_bt)
    public void setTime(){
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePicker=new TimePickerDialog(getActivity(), timePickerListener, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
        timePicker.setCancelable(false);
        timePicker.setTitle("Select the time");
        timePicker.show();
    }

    private TimePickerDialog.OnTimeSetListener timePickerListener= new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {



            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            String formate="";
            if(hour < 12 && hour >= 0)
            {
                formate="AM";
            }
            else {
                hour -= 12;
                if(hour == 0)
                {
                    hour = 12;
                }
                formate="PM";
            }


            int Hour = Integer.parseInt(String.valueOf(hourOfDay));
            int min = Integer.parseInt(String.valueOf(minute));
            H=Hour;
            M=min;
            TimeFormat=formate;
            time_bt.setText(H + ":" + M);
        }
    };



}
