package bubtjobs.com.icare.Fragment;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import bubtjobs.com.icare.Activity.Home;
import bubtjobs.com.icare.DataBase.DataBaseManager;
import bubtjobs.com.icare.Others.CommonFunction;
import bubtjobs.com.icare.Others.SessionManager;
import bubtjobs.com.icare.R;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Login extends Fragment {

    @Bind(R.id.loginBt) Button login_bt;
    @Bind(R.id.userNameEt) EditText userNameEt;
    @Bind(R.id.passwordEt) EditText passwordEt;
    SessionManager sessionManager;
    CommonFunction commonFunction;
    DataBaseManager manager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_sign_up, container, false);
        ButterKnife.bind(this,view);
        init(view);

        return view;
    }

    private void init(View view) {
        sessionManager=new SessionManager(view.getContext());
        commonFunction=new CommonFunction();
        manager=new DataBaseManager(getActivity());
       //Toast.makeText(getActivity(), ""+sessionManager.getInstallStatus(), Toast.LENGTH_SHORT).show();
        if(sessionManager.getInstallStatus()==true){
            login_bt.setText("Sign Up");
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Using this application first of all create a account")
                    .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
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

    @OnClick(R.id.loginBt)
    public void login(){

        if(validation())
        {
            if(login_bt.getText().toString().equals("Sign Up"))
        {
            sessionManager.setUserName(userNameEt.getText().toString(), passwordEt.getText().toString());
            sessionManager.setInstallStatus();
            Toast.makeText(getActivity(), "Registation complete and Add new Profile", Toast.LENGTH_LONG).show();
            Add_Profile add_profile=new Add_Profile();
            FragmentManager manager= getFragmentManager();
            FragmentTransaction transaction=manager.beginTransaction();
            transaction.replace(R.id.myFragment, add_profile);
            transaction.commit();
        }
        else{
                if(sessionManager.getUserName(userNameEt.getText().toString(),passwordEt.getText().toString()))
                {
                    if(manager.getTotalUser()==true)
                    {
                        Intent intent=new Intent(getActivity(),Home.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getActivity(), "Add new Profile", Toast.LENGTH_LONG).show();
                        Add_Profile add_profile = new Add_Profile();
                        FragmentManager manager = getFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.replace(R.id.myFragment, add_profile);
                        transaction.commit();
                    }
                }
                else{
                    Toast.makeText(getActivity(), "Unauthorize person", Toast.LENGTH_SHORT).show();
                }
        }
        }



    }

    public boolean validation()
    {
        boolean temp=true;


        if(!commonFunction.isEmpty(passwordEt)){
            passwordEt.setError("Please enter your Password");
            passwordEt.requestFocus();
            temp=false;
        }
        if(!commonFunction.isEmpty(userNameEt)){
            userNameEt.setError("Please enter User Name");
            userNameEt.requestFocus();
            temp=false;
        }
        if(temp)
            return  true;
        else
            return  false;
    }

}
