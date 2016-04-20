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
import bubtjobs.com.icare.Model.Diet;
import bubtjobs.com.icare.R;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpComing_diet_chart extends Fragment {

    ArrayList<Diet> toDayDietList;
    @Bind(R.id.todayDietListView) ListView todayDietListView;
    Diet today_diet;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_diet_chart, container, false);
        ButterKnife.bind(this, view);

        toDayDietList=new ArrayList<>();
        today_diet=new Diet("4","Breakfast","Ruti","10:00 AM","15/15/15");
        toDayDietList.add(today_diet);
        today_diet=new Diet("5","Launch","Vat,del...","02:00 PM","15/15/15");
        toDayDietList.add(today_diet);
        today_diet=new Diet("6","Dinar","Vat,del,dim","10:00 PM","15/15/15");
        toDayDietList.add(today_diet);

        if(toDayDietList!=null)
        {
            DietAdapter adapter=new DietAdapter(view.getContext(),toDayDietList);
            todayDietListView.setAdapter(adapter);
        }
        else{
            Toast.makeText(getActivity(), "UpComming DietList Not Available", Toast.LENGTH_LONG).show();
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
