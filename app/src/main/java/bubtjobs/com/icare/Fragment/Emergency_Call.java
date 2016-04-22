package bubtjobs.com.icare.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import bubtjobs.com.icare.Activity.MainActivity;
import bubtjobs.com.icare.Others.CommonFunction;
import bubtjobs.com.icare.Others.SessionManager;
import bubtjobs.com.icare.R;
import bubtjobs.com.icare.Service.Calling_service;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class Emergency_Call extends Fragment {

    @Bind(R.id.phone_et)
    EditText phone_et;
    @Bind(R.id.set_bt)
    Button set_bt;
    @Bind(R.id.cancel_bt) Button cancel_bt;
    SessionManager sessionManager;
    CommonFunction function;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_emergency__call, container, false);
        ButterKnife.bind(this,view);

        sessionManager=new SessionManager(getActivity());
        function=new CommonFunction();

        if(sessionManager.getEmergencyNumber().equals("#"))
        {
            cancel_bt.setEnabled(false);
        }
        else
        {
            set_bt.setEnabled(false);
            phone_et.setEnabled(false);
        }
        return view;
    }
    @OnClick(R.id.set_bt)
    public void set(){
        if(function.isEmpty(phone_et))
        {

            sessionManager.setEmergencyNumber(phone_et.getText().toString());
            set_bt.setEnabled(false);
            phone_et.setEnabled(false);
            cancel_bt.setEnabled(true);
            phone_et.getText().clear();
            getActivity().startService(new Intent(getActivity(), Calling_service.class));
        }
        else{
            Toast.makeText(getActivity(), "Please Insert Phone Number", Toast.LENGTH_SHORT).show();
        }
    }
    @OnClick(R.id.cancel_bt)
    public void cancel()
    {
        sessionManager.setEmergencyNumber("#");
        cancel_bt.setEnabled(false);
        set_bt.setEnabled(true);
        phone_et.setEnabled(true);
        phone_et.setHint("Put Your Emergency phone Number");
        getActivity().stopService(new Intent(getActivity(), Calling_service.class));
    }


}
