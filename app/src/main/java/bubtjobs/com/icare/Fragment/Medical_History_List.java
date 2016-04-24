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
import bubtjobs.com.icare.Adapter.Medial_History_Adapter;
import bubtjobs.com.icare.DataBase.DataBaseManager;
import bubtjobs.com.icare.Model.Diet;
import bubtjobs.com.icare.Model.Medical_History;
import bubtjobs.com.icare.R;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class Medical_History_List extends Fragment {

    ArrayList<Medical_History> medicalList;
    @Bind(R.id.todayDietListView) ListView medicalListView;
    Medical_History medical_history;
    DataBaseManager manager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_diet_chart, container, false);
        ButterKnife.bind(this, view);

        manager=new DataBaseManager(getActivity());
        medicalList=new ArrayList<>();
        medicalList=manager.getAllMedicalHistory();

        if(medicalList!=null && medicalList.size()>0)
        {
            Medial_History_Adapter adapter=new Medial_History_Adapter(view.getContext(),medicalList);
            medicalListView.setAdapter(adapter);
        }
        else{
            Toast.makeText(getActivity(), "Medical List Not Available", Toast.LENGTH_LONG).show();
        }

        // set on click linear

        medicalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String dietId = medicalList.get(position).getTableId();
                Toast.makeText(getActivity(), dietId, Toast.LENGTH_SHORT).show();
            }
        });

        return  view;
    }

}
