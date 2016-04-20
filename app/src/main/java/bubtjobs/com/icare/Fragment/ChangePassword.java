package bubtjobs.com.icare.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.content.IntentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import bubtjobs.com.icare.Activity.Home;
import bubtjobs.com.icare.Others.CommonFunction;
import bubtjobs.com.icare.Others.SessionManager;
import bubtjobs.com.icare.R;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePassword extends Fragment {


    @Bind(R.id.newPasswod_Et)
    EditText newPasswod_Et;
    @Bind(R.id.oldPassword_Et)
    EditText oldPassword_Et;

    CommonFunction function;
    SessionManager sessionManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_change_password, container, false);
        ButterKnife.bind(this, view);
        sessionManager=new SessionManager(getActivity());
        return view;
    }

    @OnClick(R.id.changPassword_bt)
    public void changePassword(){
        function=new CommonFunction();
        if(function.isEmpty(newPasswod_Et) && function.isEmpty(oldPassword_Et)){
            if(sessionManager.currentPasswordMatch(oldPassword_Et.getText().toString()))
            {
              sessionManager.passwordChange(newPasswod_Et.getText().toString());
                Toast.makeText(getActivity(), "Current Password Successfully", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getActivity(), "Current Password not match", Toast.LENGTH_SHORT).show();
            }

        }
        else{
            Toast.makeText(getActivity(), "Please Insert All Field", Toast.LENGTH_SHORT).show();
        }
    }
    @OnClick(R.id.cancel_bt)
    public void cancel(){
        Intent intent=new Intent(getActivity(),Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
