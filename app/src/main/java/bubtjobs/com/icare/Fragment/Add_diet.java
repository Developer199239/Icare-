package bubtjobs.com.icare.Fragment;


import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.Format;
import java.util.Calendar;
import java.util.TimeZone;

import bubtjobs.com.icare.Alarm_Manager.Alarm;
import bubtjobs.com.icare.DataBase.DataBaseManager;
import bubtjobs.com.icare.DataBase.DatabaseHelper;
import bubtjobs.com.icare.Model.Diet_Input;
import bubtjobs.com.icare.Others.CommonFunction;
import bubtjobs.com.icare.Others.SessionManager;
import bubtjobs.com.icare.R;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Add_diet extends Fragment {
    @Bind(R.id.diet_Com) Spinner diet_Com;
    @Bind(R.id.menu_Et) EditText menu_Et;
    @Bind(R.id.date_bt) Button date_bt;
    @Bind(R.id.time_bt) Button time_bt;
    @Bind(R.id.radioGroup) RadioGroup radioGroup;
    int H=0,M=0;
    String YY,MM,DD,TimeFormat;


    CommonFunction function;
    DataBaseManager manager;
    SessionManager sessionManager;
    Diet_Input input;
    Alarm alarm;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_add_diet, container, false);
        ButterKnife.bind(this, view);
        inti();
        return view;
    }
    public void inti(){
        function=new CommonFunction();
        manager=new DataBaseManager(getActivity());
        sessionManager=new SessionManager(getActivity());
        alarm=new Alarm(getActivity());
    }
    //====================================== add diet======================
    @OnClick(R.id.add_diet_bt)
    public void add_diet(){
        int selectedId=radioGroup.getCheckedRadioButtonId();// if not select neg value return
        if(function.isEmpty(menu_Et) && selectedId>0 && function.checkButtonValue(date_bt,"Pick Date") && function.checkButtonValue(time_bt,"Pick Time"))
        {
            String alarmType= ((RadioButton) radioGroup.findViewById(selectedId)).getText().toString();
            String diet_type=diet_Com.getSelectedItem().toString();
            String menu=menu_Et.getText().toString();
            String date=YY+MM+DD;
            // hour
            // min


            Long value=function.validAlarm(YY, MM, DD, TimeFormat, H, M);
           // Toast.makeText(getActivity(), ""+value, Toast.LENGTH_SHORT).show();
            if(value==-1)
            {
                Toast.makeText(getActivity(), "Invalid date and time selection", Toast.LENGTH_SHORT).show();
            }
            else{
                String userId=sessionManager.getCurrentPersonId();
                String hour=String.valueOf(H);
                String minute=String.valueOf(M);
                int alarmcode=function.alarmCodeGenerate();
                if(alarmType.equals("Alarm")) {
                    input = new Diet_Input(userId, diet_type, menu, date, hour, minute, TimeFormat, alarmType, "" + alarmcode,"0", "1");
                }
                else{
                    input = new Diet_Input(userId, diet_type, menu, date, hour, minute, TimeFormat, alarmType, "" + alarmcode,"1", "1");
                }
                Boolean insert=manager.addDiet(input);
                if(insert) {
                    String currentPersonId=sessionManager.getCurrentPersonId();
                    String table="diet";
                    String dietId=""+manager.lastIndex(table);
                    if(alarmType.equals("Alarm")){
                        alarm.setAlarm(value,alarmcode,table,dietId,currentPersonId);
                    }
                    else{
                        alarm.setDailyAlarm(value,alarmcode,table,dietId,currentPersonId);
                    }

                    diet_Com.setSelection(0);
                    menu_Et.getText().clear();
                    date_bt.setText("Pick Date");
                    time_bt.setText("Pick Time");
                    radioGroup.clearCheck();

                    Toast.makeText(getActivity(), "Add diet successfully ", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getActivity(), "Add deit Fail ", Toast.LENGTH_SHORT).show();
                }
            }

        }
        else
        {
            Toast.makeText(getActivity(), "Please Insert All Field", Toast.LENGTH_SHORT).show();
        }

    }

    //===================================== cancel=================
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
            //Toast.makeText(getActivity(), day1+"/"+month1+"/"+year1, Toast.LENGTH_SHORT).show();

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

            Log.i("time", Hour % 12 + ":" + min + " " + ((Hour >= 12) ? "PM" : "AM"));

            if(Hour>=12)
                TimeFormat="PM";
            else
                TimeFormat="AM";
            //time_bt.setText(H + ":" + M);
            // Toast.makeText(getActivity(), Hour+":"+min+" "+formate, Toast.LENGTH_SHORT).show();

            if(String.valueOf(M).length()==1)
            {
                time_bt.setText(H%12 + ":" + "0"+M+" "+TimeFormat);
            }
            else{
                time_bt.setText(H%12 + ":" + M+" "+TimeFormat);
            }
           // Toast.makeText(getActivity(), Hour+":"+min+" "+formate, Toast.LENGTH_SHORT).show();

        }
    };


}
