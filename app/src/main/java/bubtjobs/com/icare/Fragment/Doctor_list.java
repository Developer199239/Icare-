package bubtjobs.com.icare.Fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import bubtjobs.com.icare.Adapter.DietAdapter;
import bubtjobs.com.icare.Adapter.DoctorAdapter;
import bubtjobs.com.icare.DataBase.DataBaseManager;
import bubtjobs.com.icare.Model.Diet;
import bubtjobs.com.icare.Model.Doctor;
import bubtjobs.com.icare.R;
import butterknife.Bind;
import butterknife.ButterKnife;

public class Doctor_list extends Fragment {

    ArrayList<Doctor> doctorList;
    @Bind(R.id.todayDietListView) ListView doctorListView;
    Diet today_diet;
    DataBaseManager manager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_diet_chart, container, false);
        ButterKnife.bind(this, view);

        manager=new DataBaseManager(getActivity());
        doctorList=new ArrayList<>();
        doctorList=manager.getAllDoctor();
        if(doctorList!=null && doctorList.size()>0)
        {
            DoctorAdapter adapter=new DoctorAdapter(view.getContext(),doctorList);
            doctorListView.setAdapter(adapter);
        }
        else{
            Toast.makeText(getActivity(), "DoctorList Not Available", Toast.LENGTH_LONG).show();
        }

        // set on click linear

//        doctorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                String doctorId = doctorList.get(position).getId();
//                Toast.makeText(getActivity(), doctorId, Toast.LENGTH_SHORT).show();
//            }
//        });

        return  view;
    }

}
