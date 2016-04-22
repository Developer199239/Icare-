package bubtjobs.com.icare.Fragment;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import bubtjobs.com.icare.DataBase.DataBaseManager;
import bubtjobs.com.icare.Model.Doctor;
import bubtjobs.com.icare.Others.CommonFunction;
import bubtjobs.com.icare.Others.SessionManager;
import bubtjobs.com.icare.R;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Add_Doctor extends Fragment {

    @Bind(R.id.name_Et)EditText name_Et;
    @Bind(R.id.details_Et)EditText details_Et;
    @Bind(R.id.phone_Et)EditText phone_Et;
    @Bind(R.id.email_Et)EditText email_Et;

    CommonFunction function;
    DataBaseManager manager;
    Doctor doctor;
    SessionManager sessionManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_add__doctor, container, false);
        ButterKnife.bind(this,view);
        function=new CommonFunction();
        manager=new DataBaseManager(getActivity());
        sessionManager=new SessionManager(getActivity());

        return view;
    }

    @OnClick(R.id.add_doctor_bt)
    public void add_doctor(){
        if(function.isEmpty(name_Et) && function.isEmpty(details_Et)&& function.isEmpty(phone_Et)&& function.isEmpty(email_Et))
        {
            String userId=sessionManager.getCurrentPersonId();
            String name=name_Et.getText().toString();
            String details=details_Et.getText().toString();
            String phone=phone_Et.getText().toString();
            String email=email_Et.getText().toString();
            doctor=new Doctor(userId,name,details,phone,email,"1");
           boolean isinsert=manager.add_Doctor(doctor);

            if(isinsert)
            {
                Toast.makeText(getActivity(), "Insert Successfully", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(getActivity(), "Please Insert All Field", Toast.LENGTH_SHORT).show();
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

}
