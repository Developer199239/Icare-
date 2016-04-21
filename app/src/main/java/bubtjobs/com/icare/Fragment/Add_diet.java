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

import bubtjobs.com.icare.Others.CommonFunction;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_add_diet, container, false);
        ButterKnife.bind(this, view);
        inti();
        return view;
    }
    public void inti(){
        function=new CommonFunction();
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
            String date=DD+MM+YY;
            // hour
            // min


            Long value=validAlarm(YY, MM, DD, TimeFormat, H, M);
            if(value==-1)
            {
                Toast.makeText(getActivity(), "Invalid date and time selection", Toast.LENGTH_SHORT).show();
            }
            else{
                //Toast.makeText(getActivity(), "Correct", Toast.LENGTH_SHORT).show();
            }

           // Toast.makeText(getActivity(), diet_type+" "+menu+" "+date+" "+H+":"+M+" "+TimeFormat+" "+alarmType, Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(getActivity(), "Please Insert All Field", Toast.LENGTH_SHORT).show();
        }

    }

    private Long validAlarm(String YY,String MM,String DD,String formate,int requestHour,int requstMinute) {
       int year=Integer.parseInt(String.valueOf(YY));
       int month=Integer.parseInt(String.valueOf(MM));
       int day=Integer.parseInt(String.valueOf(DD));

        String requestDateString=YY+MM+DD;
        int requestDateInt=Integer.parseInt(requestDateString);



        // current day calculation
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        String monthTemp=String.valueOf(cal.get(Calendar.MONTH) + 1);
        String dayTemp=String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        int currentHour=cal.get(Calendar.HOUR);
        int currentMinute=cal.get(Calendar.MINUTE);

        if(monthTemp.length()==1)
            monthTemp="0"+monthTemp;
        if(dayTemp.length()==1)
            dayTemp="0"+dayTemp;

        int currentYear=cal.get(Calendar.YEAR);
        int currentMonth=Integer.parseInt(monthTemp);
        int currentDay=Integer.parseInt(dayTemp);


        String currentdateString=currentYear+""+monthTemp+dayTemp;
        int currentdateInt=Integer.parseInt(currentdateString);

        boolean isCorrect=false;

        if(requestDateInt>=currentdateInt)
        {
            //Toast.makeText(getActivity(), "Correct", Toast.LENGTH_SHORT).show();
            if(requestDateInt==currentdateInt)
            {
              if(requestHour==currentHour && requstMinute>currentMinute)
              {
                 isCorrect=true;
              }
                else if(requestHour>currentHour)
              {
                  isCorrect=true;
              }
                else{
                  isCorrect=false;
              }

            }
            else{
                isCorrect=true;
            }
        }
        else{
            isCorrect=false;
        }

        // calculation part
        if(isCorrect==false)
        {
            return Long.parseLong("-1");
        }
        else
        {

            int yearInterval=year-currentYear;
            int monthInterval=Math.abs(month-currentMonth);
            int dayInterval=Math.abs(day-currentDay);

            int hourInterval=Math.abs(requestHour-currentHour);
            int minuteInterval=Math.abs(requstMinute-currentMinute);

            Toast.makeText(getActivity(), yearInterval+" m="+monthInterval+" d "+dayInterval+" h "+hourInterval+" m "+minuteInterval, Toast.LENGTH_SHORT).show();

            return Long.parseLong("1");
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
            TimeFormat=formate;
            time_bt.setText(H + ":" + M);
           // Toast.makeText(getActivity(), Hour+":"+min+" "+formate, Toast.LENGTH_SHORT).show();

        }
    };


}
