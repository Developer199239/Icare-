package bubtjobs.com.icare.Fragment;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import bubtjobs.com.icare.Adapter.DietAdapter;
import bubtjobs.com.icare.Alarm_Manager.Alarm;
import bubtjobs.com.icare.DataBase.DataBaseManager;
import bubtjobs.com.icare.Model.Diet;
import bubtjobs.com.icare.Model.Diet_Input;
import bubtjobs.com.icare.Others.CommonFunction;
import bubtjobs.com.icare.Others.SessionManager;
import bubtjobs.com.icare.R;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class Today_diet_chart extends Fragment {

    ArrayList<Diet> toDayDietList;
    @Bind(R.id.todayDietListView) ListView todayDietListView;
    Diet today_diet;
    DataBaseManager manager;
    Alarm alarm;
    CommonFunction function;
    SessionManager sessionManager;


    int H=0,M=0;
    String YY,MM,DD,TimeFormat;
     Button date_bt;
    Button time_bt;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_diet_chart, container, false);
        ButterKnife.bind(this, view);

        manager=new DataBaseManager(getActivity());
        toDayDietList=new ArrayList<>();
        alarm=new Alarm(getActivity());
        function=new CommonFunction();
        sessionManager=new SessionManager(getActivity());
       // Toast.makeText(getActivity(), manager.getTodayDiet(), Toast.LENGTH_LONG).show();

        toDayDietList=manager.getDiet("==");

        if(toDayDietList!=null && toDayDietList.size()>0)
        {
            DietAdapter adapter=new DietAdapter(view.getContext(),toDayDietList);
            todayDietListView.setAdapter(adapter);
        }
        else {
            Toast.makeText(getActivity(), "Todays DietList Not Available", Toast.LENGTH_LONG).show();
        }

        // set on click linear

        todayDietListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String dietId = toDayDietList.get(position).getDietId();
                //Toast.makeText(getActivity(), dietId, Toast.LENGTH_SHORT).show();
                todayOperation(dietId);
            }
        });

        return  view;
    }

    // AlertDialog start
    private void todayOperation(final String dietId) {
       Diet_Input dietInput=new Diet_Input();
        dietInput=manager.getSingleRowDiet(dietId);

        final String dietType=dietInput.getDietType();
        final  String menu=dietInput.getMenu();
        final String date=dietInput.getDate();
        final  String formate=dietInput.getFormate();
        final String alarmType=dietInput.getAlarmType();
        final String alarmCode=dietInput.getAlarmCode();
        final int hour=Integer.parseInt(dietInput.getHour());
         final int  minute=Integer.parseInt(dietInput.getMinute());


        // current day calculation
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        int currentHour=cal.get(Calendar.HOUR_OF_DAY);
        int currentMinute=cal.get(Calendar.MINUTE);

        //================================== check list item  isPossible to  update or not================
        if(currentHour>hour || (hour==currentHour && currentMinute>minute) ||(hour==currentHour && currentMinute==minute))
        {
            Toast.makeText(getActivity(), "You can not modify ", Toast.LENGTH_SHORT).show();
        }
        else{
            LayoutInflater li = LayoutInflater.from(getActivity());
            View promptsView = li.inflate(R.layout.alert_dialog_update, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setView(promptsView);
            final Button updae_bt = (Button) promptsView.findViewById(R.id.updae_bt);
            final Button remove_bt = (Button) promptsView.findViewById(R.id.remove_bt);
            final AlertDialog alertDialog = alertDialogBuilder.create();

            /////////////////////////first AlertDialog update button operation start//////////////////////
            updae_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    //============================= 2nd alertDialog view Start============================
                    LayoutInflater li = LayoutInflater.from(getActivity());
                    View promptsView = li.inflate(R.layout.fragment_add_diet, null);
                    AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(getActivity());
                    alertDialogBuilder1.setView(promptsView);

                    final AlertDialog alertDialog1 = alertDialogBuilder1.create();

                    final Spinner diet_Com = (Spinner) promptsView.findViewById(R.id.diet_Com);
                    final EditText menu_Et = (EditText) promptsView.findViewById(R.id.menu_Et);
                    date_bt = (Button) promptsView.findViewById(R.id.date_bt);
                    time_bt = (Button) promptsView.findViewById(R.id.time_bt);
                    final RadioGroup radioGroup = (RadioGroup) promptsView.findViewById(R.id.radioGroup);
                   Button add_diet_bt = (Button) promptsView.findViewById(R.id.add_diet_bt);
                   Button cancel_bt = (Button) promptsView.findViewById(R.id.cancel_bt);

                    //start date retrive and print
                    // spinner value  set
                    ArrayAdapter<String> array_spinner=(ArrayAdapter<String>)diet_Com.getAdapter();
                    diet_Com.setSelection(array_spinner.getPosition(dietType));
                    // menu value set
                    menu_Et.setText(menu);
                    //date set
                    String year=date.substring(0, 4);
                    String month=date.substring(4, 6);
                    String day=date.substring(6,8);
                    date_bt.setText(day+"/"+month+"/"+year);
                    // time set
                    time_bt.setText(hour+":"+minute+" "+formate);
                    // alarm type
                    if(alarmType.equals("Alarm"))
                    radioGroup.check(((RadioButton)radioGroup.getChildAt(0)).getId());
                    else{
                        radioGroup.check(((RadioButton)radioGroup.getChildAt(1)).getId());
                    }

                    H=hour;
                    M=minute;
                    YY=year;
                    MM=month;
                    DD=day;
                    //end date retrive and print

                    // date choose
                    date_bt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Calendar cal = Calendar.getInstance(TimeZone.getDefault()); // Get current date
                            DatePickerDialog datePicker = new DatePickerDialog(getActivity(), datePickerListener,
                                    Integer.parseInt(YY),
                                    (Integer.parseInt(MM)-1),
                                    Integer.parseInt(DD));
                            datePicker.setCancelable(false);
                            datePicker.setTitle("Select the date");
                            datePicker.show();

                           // date_bt.setText(DD+"/"+MM+"/"+YY);
                        }
                    });
                    // time choose
                    time_bt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            TimePickerDialog timePicker=new TimePickerDialog(getActivity(), timePickerListener, H, M,
                                    DateFormat.is24HourFormat(getActivity()));
                            timePicker.setCancelable(false);
                            timePicker.setTitle("Select the time");
                            timePicker.show();
                        }
                    });

                    // ========================2nd alertDialog update button operation start====================
                    add_diet_bt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int selectedId=radioGroup.getCheckedRadioButtonId();// if not select neg value return
                            if(function.isEmpty(menu_Et) && selectedId>0 && function.checkButtonValue(date_bt,"Pick Date") && function.checkButtonValue(time_bt,"Pick Time"))
                            {
                                String alarmType= ((RadioButton) radioGroup.findViewById(selectedId)).getText().toString();
                                String diet_type=diet_Com.getSelectedItem().toString();
                                String menu=menu_Et.getText().toString();
                                String date=YY+MM+DD;

                                Long value=function.validAlarm(YY, MM, DD, TimeFormat, H, M);
                                if(value==-1)
                                {
                                    Toast.makeText(getActivity(), "Invalid date and time selection", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    String hour=String.valueOf(H);
                                    String minute=String.valueOf(M);
                                    //int alarmcode=function.alarmCodeGenerate();

                                   Diet_Input input=new Diet_Input(dietId,diet_type,menu,date,hour,minute,TimeFormat,alarmType);
                                    Boolean insert=manager.dietUpdate(input);
                                    if(insert)
                                    {
                                        String currentPersonId=sessionManager.getCurrentPersonId();
                                        String table="diet";
                                        if(alarmType.equals("Alarm")){
                                            alarm.setAlarm(value,Integer.parseInt(alarmCode),table,dietId,currentPersonId);
                                        }
                                        else{
                                            alarm.setDailyAlarm(value,Integer.parseInt(alarmCode),table,dietId,currentPersonId);
                                        }
                                        Toast.makeText(getActivity(), "Update diet successfully ", Toast.LENGTH_SHORT).show();



                                    }
                                    else{
                                        Toast.makeText(getActivity(), "Update deit Fail, Try Again", Toast.LENGTH_SHORT).show();
                                    }
                                    alertDialog1.dismiss();
                                    Fragment currentFragment;
                                    FragmentManager fragmanager;
                                    fragmanager=getFragmentManager();
                                    FragmentTransaction transaction;

                                    currentFragment=new Today_diet_chart();
                                    transaction = fragmanager.beginTransaction();
                                    transaction.replace(R.id.homeFragment, currentFragment);
                                    transaction.addToBackStack(null);
                                    transaction.commit();

                                }
                            }
                            else{
                                Toast.makeText(getActivity(), "Please Insert All Field", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    // ========================2nd alertDialog update button operation start====================

                    // ========================  2nd alertDialog cancel button operation start====================
                    cancel_bt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            //Toast.makeText(getActivity(), "cancle", Toast.LENGTH_SHORT).show();
                            alertDialog1.dismiss();
                            Fragment currentFragment;
                            FragmentManager fragmanager;
                            fragmanager=getFragmentManager();
                            FragmentTransaction transaction;

                            currentFragment=new Today_diet_chart();
                            transaction = fragmanager.beginTransaction();
                            transaction.replace(R.id.homeFragment, currentFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }
                    });
                    // ======================== cancel end====================



                    alertDialog1.show();
                }
            });

            /////////////////////////first AlertDialog update button operation end//////////////////////

            /////////////////////////first AlertDialog remove button operation start//////////////////////
            remove_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    // Use the Builder class for convenient dialog construction
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Do you want to remove?")
                            .setPositiveButton("remove", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    boolean isRemove = manager.removeRow("diet", dietId);
                                    if(isRemove)
                                    {
                                        alarm.cancelAlarm(Integer.parseInt(alarmCode));
                                        Toast.makeText(getActivity(),"Remove Successfully",Toast.LENGTH_LONG).show();
                                        Fragment currentFragment;
                                        FragmentManager fragmanager;
                                        fragmanager=getFragmentManager();
                                        FragmentTransaction transaction;

                                        currentFragment=new Today_diet_chart();
                                        transaction = fragmanager.beginTransaction();
                                        transaction.replace(R.id.homeFragment, currentFragment);
                                        transaction.addToBackStack(null);
                                        transaction.commit();
                                    }
                                    else{

                                        Toast.makeText(getActivity(),"Remove Fail, Try Again",Toast.LENGTH_LONG).show();
                                    }
                                }
                            })
                            .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    builder.create();
                    builder.show();
                }
            });

            alertDialog.show();

        }








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
