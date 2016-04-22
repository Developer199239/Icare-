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

import bubtjobs.com.icare.Adapter.VaccinationAdapter;
import bubtjobs.com.icare.DataBase.DataBaseManager;
import bubtjobs.com.icare.Model.Vaccination;
import bubtjobs.com.icare.R;
import butterknife.Bind;
import butterknife.ButterKnife;

public class Previous_Vaccination_chart extends Fragment {

    @Bind(R.id.vaccinationListView) ListView vaccinationListView;
    DataBaseManager manager;
    ArrayList<Vaccination> vaccinationList;
    Vaccination vaccination;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment__vaccination_chart, container, false);
        ButterKnife.bind(this, view);

        manager=new DataBaseManager(getActivity());
        vaccinationList=new ArrayList<>();
        vaccinationList=manager.getVaccination("<");

        if(vaccinationList!=null && vaccinationList.size()>0)
        {
            VaccinationAdapter adapter=new VaccinationAdapter(view.getContext(),vaccinationList);
            vaccinationListView.setAdapter(adapter);
        }
        else{
            Toast.makeText(getActivity(), "Previous Vaccination List Not Available", Toast.LENGTH_LONG).show();
        }

        return view;
    }

}
