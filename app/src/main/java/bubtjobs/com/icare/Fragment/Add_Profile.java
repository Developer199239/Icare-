package bubtjobs.com.icare.Fragment;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;
import java.util.TimeZone;

import bubtjobs.com.icare.Activity.Home;
import bubtjobs.com.icare.DataBase.DataBaseManager;
import bubtjobs.com.icare.Model.Profile_Add;
import bubtjobs.com.icare.Others.CommonFunction;
import bubtjobs.com.icare.R;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Add_Profile extends Fragment {
    @Bind(R.id.name_Et) EditText name_Et;
    @Bind(R.id.height_Et) EditText height_Et;
    @Bind(R.id.weight_Et) EditText weight_Et;
    @Bind(R.id.majorDis_Et) EditText majorDis_Et;
    @Bind(R.id.relation_Com) Spinner relation_Com;
    @Bind(R.id.bloodGroup_Com) Spinner bloodGroup_Com;
    @Bind(R.id.dob_bt) Button dob_bt;

    CommonFunction function;
    DataBaseManager manager;
    // model class
    Profile_Add profile_add;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_add__profile, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @OnClick(R.id.add_bt)
    public void add(){
        function=new CommonFunction();
        dob_bt.getText().toString();
        if(function.isEmpty(name_Et) &&function.isEmpty(height_Et) &&function.isEmpty(weight_Et) &&function.isEmpty(majorDis_Et) && ageSelect())
        {
            String name=name_Et.getText().toString();
            String relation=relation_Com.getSelectedItem().toString();
            String age=dob_bt.getText().toString();
            String weight=weight_Et.getText().toString();
            String height=height_Et.getText().toString();
            String major_dis=majorDis_Et.getText().toString();
            String blood=bloodGroup_Com.getSelectedItem().toString();



            profile_add=new Profile_Add(name,relation,age,height,weight,major_dis,blood);
            manager=new DataBaseManager(getActivity());
            Boolean isInsert=manager.add_profile(profile_add);
            if(isInsert)
            {
                Toast.makeText(getActivity(), "Insert Successfully", Toast.LENGTH_SHORT).show();
                name_Et.getText().clear();
                relation_Com.setSelection(0);
                dob_bt.setText("Pick Birth Date");
                height_Et.getText().clear();
                weight_Et.getText().clear();
                majorDis_Et.getText().clear();
                bloodGroup_Com.setSelection(0);
            }
            else
            {
                Toast.makeText(getActivity(), "Insert Fail", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(getActivity(), "Please Insert All Field", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.cancel_bt)
    public void cancel(){
       // startActivity(new Intent(getActivity(), Home.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

        manager=new DataBaseManager(getActivity());
        if(manager.getTotalUser()==true)
        {
        Intent intent=new Intent(getActivity(),Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        }
        else{

            Login login=new Login();
            FragmentManager manager= getFragmentManager();
            FragmentTransaction transaction=manager.beginTransaction();
            transaction.replace(R.id.myFragment, login);
            transaction.commit();
        }
    }

    @OnClick(R.id.dob_bt)
    public void dateOfBirth(){
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
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            int year1 = Integer.parseInt(String.valueOf(selectedYear));
            int  month1 = Integer.parseInt(String.valueOf(selectedMonth + 1));
            int day1 = Integer.parseInt(String.valueOf(selectedDay));
            if(year1+month1>1)
            {
                int month=0;
                final Calendar cal= Calendar.getInstance();
                if((cal.get(Calendar.MONTH)+1)<month1)
                  month=month1-cal.get(Calendar.MONTH);
                else
                month=(cal.get(Calendar.MONTH)+1)-month1;


              String age=(cal.get(Calendar.YEAR)-year1)+"."+month;
                dob_bt.setText(age);
            }
            else{
                dob_bt.setText("0.1");
            }

        }
    };

    public  boolean ageSelect(){
        if(dob_bt.getText().toString().equals("Pick Birth Date"))
            return false;
        else
            return true;
    }


}
