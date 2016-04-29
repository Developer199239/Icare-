package bubtjobs.com.icare.Fragment;


import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.content.IntentCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import bubtjobs.com.icare.Activity.Home;
import bubtjobs.com.icare.Activity.MainActivity;
import bubtjobs.com.icare.Activity.PersonHome;
import bubtjobs.com.icare.Adapter.ProfileViewAdapter;
import bubtjobs.com.icare.Alarm_Manager.Alarm;
import bubtjobs.com.icare.DataBase.DataBaseManager;
import bubtjobs.com.icare.Model.Profile;
import bubtjobs.com.icare.Model.Profile_Add;
import bubtjobs.com.icare.Others.SessionManager;
import bubtjobs.com.icare.R;
import butterknife.Bind;
import butterknife.ButterKnife;

public class View_All_Profile extends Fragment {

    ArrayList<Profile>profileArrayList;
    @Bind(R.id.profileListView) ListView profileListView;
    Profile profile;
    SessionManager sessionManager;
    DataBaseManager manager;
    Alarm alarm;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_view__all__profile, container, false);
        ButterKnife.bind(this,view);

        // add data in viewprofile class
        profileArrayList=new ArrayList<>();
        alarm=new Alarm(getActivity());
        manager=new DataBaseManager(getActivity());
        profileArrayList=manager.getAllUser();


        if(profileArrayList!=null) {
            ProfileViewAdapter adapter = new ProfileViewAdapter(view.getContext(), profileArrayList);
            profileListView.setAdapter(adapter);
        }

        profileListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String userId=profileArrayList.get(position).getUserId();
                String username=profileArrayList.get(position).getUserName();

                sessionManager=new SessionManager(getActivity());
                sessionManager.setCurrentPersonId(userId);

                //Toast.makeText(getActivity(), userId+""+username, Toast.LENGTH_SHORT).show();

                Intent intent=new Intent(getActivity(),PersonHome.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });

        // =========================== on item long click==============================
        profileListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {



            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                String userId=profileArrayList.get(position).getUserId();
                String username=profileArrayList.get(position).getUserName();

                sessionManager=new SessionManager(getActivity());
                sessionManager.setCurrentPersonId(userId);

                LayoutInflater li = LayoutInflater.from(getActivity());
                View promptsView = li.inflate(R.layout.alert_dialog_view_profile, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setView(promptsView);
                final Button profileDetails_bt = (Button) promptsView.findViewById(R.id.profileDetails_bt);
                final Button remove_bt = (Button) promptsView.findViewById(R.id.remove_bt);
                final AlertDialog alertDialog = alertDialogBuilder.create();


                profileDetails_bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();

                        // inner alertDialog
                        // profile details
                        LayoutInflater li = LayoutInflater.from(getActivity());
                        View promptsView = li.inflate(R.layout.ad_profile_details, null);
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                        alertDialogBuilder.setView(promptsView);

                        final TextView name = (TextView) promptsView.findViewById(R.id.name);
                        final TextView relation = (TextView) promptsView.findViewById(R.id.relation);
                        final TextView age = (TextView) promptsView.findViewById(R.id.age);
                        final TextView height = (TextView) promptsView.findViewById(R.id.height);
                        final TextView weight = (TextView) promptsView.findViewById(R.id.weight);
                        final TextView major_dis = (TextView) promptsView.findViewById(R.id.major_dis);
                        final TextView blood_group = (TextView) promptsView.findViewById(R.id.blood_group);

                        String userId = profileArrayList.get(position).getUserId();
                        String username = profileArrayList.get(position).getUserName();
                        Profile_Add profile_add = new Profile_Add();
                        profile_add = manager.getUserProfile(userId);

                        if (profile_add != null) {
                            name.setText("Name: " + profile_add.getName());
                            relation.setText("Relation: " + profile_add.getRelation());
                            age.setText("Age: " + profile_add.getAge()+" year");
                            height.setText("Height: " + profile_add.getHeight()+" feet");
                            weight.setText("Weight: " + profile_add.getWeight()+" kg");
                            major_dis.setText("Major Disease: " + profile_add.getMajor_dis());
                            blood_group.setText("Blood Group: " + profile_add.getBlood());
                        } else {

                        }

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                });

                // remove button

                remove_bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();

                        // Use the Builder class for convenient dialog construction
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("Do you want to remove the user?")
                                .setPositiveButton("remove", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {


                                        String userId=profileArrayList.get(position).getUserId();
                                        ArrayList<String> diet_alarmCode=new ArrayList<String>();
                                        ArrayList<String> vaccination_alarmCode=new ArrayList<String>();
                                        diet_alarmCode=manager.getAlarmCode("diet",userId);
                                        vaccination_alarmCode=manager.getAlarmCode("vaccination",userId);


                                        boolean isRemove = manager.removeRow("user",userId);
                                        if (isRemove) {

                                            //alarmCancel
                                           if(diet_alarmCode!=null)
                                           {
                                               for(String obj:diet_alarmCode)
                                               {
                                                   Log.i("obj", obj);
                                                   alarm.cancelAlarm(Integer.parseInt(obj));
                                               }
                                           }

                                            if(vaccination_alarmCode!=null)
                                            {
                                                for(String obj:vaccination_alarmCode)
                                                {
                                                    Log.i("obj", obj);
                                                    alarm.cancelAlarm(Integer.parseInt(obj));
                                                }
                                            }


                                            Toast.makeText(getActivity(),"Remove Successfully",Toast.LENGTH_LONG).show();


                                            if(manager.getTotalUser()==false)
                                            {
//                                                Intent intent=new Intent(getActivity(), MainActivity.class);
//                                                startActivity(intent);
                                                Fragment currentFragment;
                                                FragmentManager fragmanager;
                                                fragmanager = getFragmentManager();
                                                FragmentTransaction transaction;

                                                currentFragment = new Add_Profile();
                                                transaction = fragmanager.beginTransaction();
                                                transaction.replace(R.id.homeFragment, currentFragment);
                                                transaction.addToBackStack(null);
                                                transaction.commit();
                                            }
                                            else {
//                                                Fragment currentFragment;
//                                                FragmentManager fragmanager;
//                                                fragmanager = getFragmentManager();
//                                                FragmentTransaction transaction;
//
//                                                currentFragment = new View_All_Profile();
//                                                transaction = fragmanager.beginTransaction();
//                                                transaction.replace(R.id.homeFragment, currentFragment);
//                                                transaction.addToBackStack(null);
//                                                transaction.commit();
                                                Intent intent=new Intent(getActivity(),Home.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                            }

                                        } else {
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
                return true;
            }
        });

        return view;
    }
}
