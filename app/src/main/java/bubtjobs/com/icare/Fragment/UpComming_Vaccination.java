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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import bubtjobs.com.icare.Adapter.DietAdapter;
import bubtjobs.com.icare.Adapter.VaccinationAdapter;
import bubtjobs.com.icare.Alarm_Manager.Alarm;
import bubtjobs.com.icare.DataBase.DataBaseManager;
import bubtjobs.com.icare.Model.Diet;
import bubtjobs.com.icare.Model.Diet_Input;
import bubtjobs.com.icare.Model.Vaccination;
import bubtjobs.com.icare.Others.CommonFunction;
import bubtjobs.com.icare.Others.SessionManager;
import bubtjobs.com.icare.R;
import butterknife.Bind;
import butterknife.ButterKnife;

public class UpComming_Vaccination extends Fragment {

    @Bind(R.id.vaccinationListView) ListView vaccinationListView;
    DataBaseManager manager;
    ArrayList<Vaccination> vaccinationList;
    Vaccination vaccination;

    Alarm alarm;
    CommonFunction function;
    SessionManager sessionManager;


    int H=0,M=0;
    private String YY,MM,DD,TimeFormat;
    Button date_bt;
    Button time_bt;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment__vaccination_chart, container, false);
        ButterKnife.bind(this, view);

        manager=new DataBaseManager(getActivity());
        alarm=new Alarm(getActivity());
        function=new CommonFunction();
        sessionManager=new SessionManager(getActivity());

        vaccinationList=new ArrayList<>();
        vaccinationList=manager.getVaccination(">=");

        if(vaccinationList!=null && vaccinationList.size()>0)
        {
            VaccinationAdapter adapter=new VaccinationAdapter(view.getContext(),vaccinationList);
            vaccinationListView.setAdapter(adapter);
        }
        else{
            Toast.makeText(getActivity(), "Vaccination List Not Available", Toast.LENGTH_LONG).show();
        }

        vaccinationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String vaccinationId = vaccinationList.get(position).getTableId();
               // Toast.makeText(getActivity(), vaccinationId, Toast.LENGTH_SHORT).show();
                vaccinationUpdateOperation(vaccinationId);
            }
        });

        return view;
    }

    private void vaccinationUpdateOperation(final String vaccinationId) {
        Vaccination vaccination=new Vaccination();
        vaccination=manager.getSingleRowVaccination(vaccinationId);

        final String date=vaccination.getDate();
        final String hour=vaccination.getHour();
        final String minute=vaccination.getMinute();
        final String formate=vaccination.getFormate();
        final String vaccination_name=vaccination.getVa_name();
        final String details=vaccination.getDetails();
        final String alarmType=vaccination.getAlarmType();
        final String alarmCode=vaccination.getAlarmCode();






        Log.i("query data1",date+" "+vaccination_name);
        // ================= 1st AlertDialog start===================
        LayoutInflater li = LayoutInflater.from(getActivity());
        View promptsView = li.inflate(R.layout.alert_dialog_update, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptsView);
        final Button updae_bt = (Button) promptsView.findViewById(R.id.updae_bt);
        final Button remove_bt = (Button) promptsView.findViewById(R.id.remove_bt);
        final AlertDialog alertDialog = alertDialogBuilder.create();

        // ======================== 1st AlertDialog update button start
        updae_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

                LayoutInflater li = LayoutInflater.from(getActivity());
                View promptsView = li.inflate(R.layout.fragment_add__vaccination, null);
                AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(getActivity());
                alertDialogBuilder1.setView(promptsView);
                final AlertDialog alertDialog1 = alertDialogBuilder1.create();

                final Spinner vaccination_Com = (Spinner) promptsView.findViewById(R.id.vaccination_Com);
                final EditText va_detatils_Et = (EditText) promptsView.findViewById(R.id.va_detatils_Et);
                final CheckBox va_reminder_Cb = (CheckBox) promptsView.findViewById(R.id.va_reminder_Cb);
                date_bt = (Button) promptsView.findViewById(R.id.date_bt);
                time_bt = (Button) promptsView.findViewById(R.id.time_bt);
                Button add_va_bt = (Button) promptsView.findViewById(R.id.add_va_bt);
                Button cancel_bt = (Button) promptsView.findViewById(R.id.cancel_bt);

                add_va_bt.setText("Update");


                //start date retrive and print
                // spinner value  set
                ArrayAdapter<String> array_spinner = (ArrayAdapter<String>) vaccination_Com.getAdapter();
                vaccination_Com.setSelection(array_spinner.getPosition(vaccination_name));
                va_detatils_Et.setText(details);
                //date set
                String year = date.substring(0, 4);
                String month = date.substring(4, 6);
                String day = date.substring(6, 8);
                date_bt.setText(day + "/" + month + "/" + year);
                // time set
                time_bt.setText(hour + ":" + minute + " " + formate);

                va_reminder_Cb.setChecked(true);

                H = Integer.parseInt(hour);
                M = Integer.parseInt(minute);
                YY = year;
                MM = month;
                DD = day;
                TimeFormat = formate;


                //end date retrive and print

                // date choose
                date_bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar cal = Calendar.getInstance(TimeZone.getDefault()); // Get current date
                        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), datePickerListener,
                                Integer.parseInt(YY),
                                (Integer.parseInt(MM) - 1),
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

                        TimePickerDialog timePicker = new TimePickerDialog(getActivity(), timePickerListener, H, M,
                                DateFormat.is24HourFormat(getActivity()));
                        timePicker.setCancelable(false);
                        timePicker.setTitle("Select the time");
                        timePicker.show();
                    }
                });

                // ======================== 2st AlertDialog update button start
                add_va_bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (function.isEmpty(va_detatils_Et) && function.checkButtonValue(date_bt, "Pick Date") && function.checkButtonValue(time_bt, "Pick Time") && va_reminder_Cb.isChecked()) {
                            String va_name = vaccination_Com.getSelectedItem().toString();
                            String va_details = va_detatils_Et.getText().toString();
                            String date = YY + MM + DD;

                            Long value = function.validAlarm(YY, MM, DD, TimeFormat, H, M);
                            if (value == -1) {
                                Toast.makeText(getActivity(), "Invalid date and time selection", Toast.LENGTH_SHORT).show();
                            } else {

                                String userId = sessionManager.getCurrentPersonId();
                                String hour = String.valueOf(H);
                                String minute = String.valueOf(M);
                                //Vaccination vaccination=new Vaccination(userId,va_name,date,hour,minute,TimeFormat,va_details,"Reminder",""+alarmcode,"1");
                                Vaccination vaccination = new Vaccination(vaccinationId, userId, va_name, date, hour, minute, formate, va_details, "Reminder", alarmCode, "1");

                                Boolean insert = manager.vaccinationUpdate(vaccination);
                                if (insert) {
                                    String currentPersonId = sessionManager.getCurrentPersonId();
                                    String table = "vaccination";

                                    alarm.setAlarm(value, Integer.parseInt(alarmCode), table, vaccinationId, currentPersonId);
                                    Toast.makeText(getActivity(), "Update Vaccination successfully ", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity(), "Update Vaccination Fail, Try Again", Toast.LENGTH_SHORT).show();
                                }
                                alertDialog1.dismiss();
                                Fragment currentFragment;
                                FragmentManager fragmanager;
                                fragmanager = getFragmentManager();
                                FragmentTransaction transaction;

                                currentFragment = new UpComming_Vaccination();
                                transaction = fragmanager.beginTransaction();
                                transaction.replace(R.id.homeFragment, currentFragment);
                                transaction.addToBackStack(null);
                                transaction.commit();
                            }
                        }

                    }
                });
                // ======================== 2st AlertDialog update button end


                // ======================== 2st AlertDialog cancel button start
                cancel_bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog1.dismiss();
                    }
                });


                alertDialog1.show();

            }
        });
        // ================= 1st AlertDialog update button end

        // ======================== 1st AlertDialog remove button start
        remove_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

                // current day calculation
                Calendar cal = Calendar.getInstance(TimeZone.getDefault());
                int currentHour = cal.get(Calendar.HOUR_OF_DAY);
                int currentMinute = cal.get(Calendar.MINUTE);

                if (currentHour > Integer.parseInt(hour) || (Integer.parseInt(hour) == currentHour && currentMinute > Integer.parseInt(minute)) || (Integer.parseInt(hour) == currentHour && currentMinute == Integer.parseInt(minute))) {
                    Toast.makeText(getActivity(), "Its not remove", Toast.LENGTH_SHORT).show();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Do you want to remove?")
                            .setPositiveButton("remove", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    boolean isRemove = manager.removeRow("vaccination", vaccinationId);
                                    if (isRemove) {
                                        alarm.cancelAlarm(Integer.parseInt(alarmCode));
                                        Toast.makeText(getActivity(), "Remove Successfully", Toast.LENGTH_LONG).show();
                                        Fragment currentFragment;
                                        FragmentManager fragmanager;
                                        fragmanager = getFragmentManager();
                                        FragmentTransaction transaction;

                                        currentFragment = new UpComming_Vaccination();
                                        transaction = fragmanager.beginTransaction();
                                        transaction.replace(R.id.homeFragment, currentFragment);
                                        transaction.addToBackStack(null);
                                        transaction.commit();

                                    } else {

                                        Toast.makeText(getActivity(), "Remove Fail, Try Again", Toast.LENGTH_LONG).show();
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
            }
        });
        // ======================== 1st AlertDialog remove button end


        alertDialog.show();

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
//            if(hour < 12 && hour >= 0)
//            {
//                formate="AM";
//            }
//            else {
//                hour -= 12;
//                if(hour == 0)
//                {
//                    hour = 12;
//                }
//                formate="PM";
//            }

            //SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");



            int Hour = Integer.parseInt(String.valueOf(hourOfDay));
            int min = Integer.parseInt(String.valueOf(minute));
            H=Hour;
            M=min;

            Log.i("time",Hour%12 + ":" + min + " " + ((Hour>=12) ? "PM" : "AM"));

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

        }
    };


}
