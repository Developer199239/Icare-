package bubtjobs.com.icare.Fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import bubtjobs.com.icare.Adapter.DietAdapter;
import bubtjobs.com.icare.DataBase.DataBaseManager;
import bubtjobs.com.icare.Model.Diet;
import bubtjobs.com.icare.R;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class Today_diet_chart extends Fragment {

    ArrayList<Diet> toDayDietList;
    @Bind(R.id.todayDietListView) ListView todayDietListView;
    Diet today_diet;
    DataBaseManager manager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_diet_chart, container, false);
        ButterKnife.bind(this, view);

        manager=new DataBaseManager(getActivity());

//        String temp=manager.getTodayDiet();
//        Toast.makeText(getActivity(), temp, Toast.LENGTH_LONG).show();


        toDayDietList=new ArrayList<>();
       // Toast.makeText(getActivity(), manager.getTodayDiet(), Toast.LENGTH_LONG).show();

        toDayDietList=manager.getTodayDiet();

        if(toDayDietList!=null && toDayDietList.size()>0)
        {
            DietAdapter adapter=new DietAdapter(view.getContext(),toDayDietList);
            todayDietListView.setAdapter(adapter);
        }
        else {
            Toast.makeText(getActivity(), "Todays DietList Not Available", Toast.LENGTH_LONG).show();
        }

        // set on click linear

        todayDietListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String dietId = toDayDietList.get(position).getDietId();
                Toast.makeText(getActivity(), dietId, Toast.LENGTH_SHORT).show();
            }
        });

        return  view;
    }



}
