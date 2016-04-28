package bubtjobs.com.icare.Fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import bubtjobs.com.icare.Adapter.HealthTipsAdapter;
import bubtjobs.com.icare.Model.Health_Tips;
import bubtjobs.com.icare.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GeneralInfo extends Fragment {

    Health_Tips health_tips;
    ArrayList<Health_Tips> tipslist;
    ListView generalInfListView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_general_info, container, false);
        generalInfListView=(ListView)view.findViewById(R.id.generalInfListView);
        init();

        if(tipslist!=null && tipslist.size()>0)
        {
            HealthTipsAdapter adpater=new HealthTipsAdapter(getActivity(),tipslist);
            generalInfListView.setAdapter(adpater);
        }
        else{
            Toast.makeText(getActivity(), "Health Tips not found", Toast.LENGTH_SHORT).show();
        }

        return view;
    }
    public void init(){

        tipslist=new ArrayList<>();
        health_tips=new Health_Tips("Don’t skip breakfast","Studies show that eating a proper breakfast is one of the most positive things you can do if you are trying to lose weight. Breakfast skippers tend to gain weight.");
        tipslist.add(health_tips);
        health_tips=new Health_Tips("Brush up on hygiene","Many people don't know how to brush their teeth properly. Improper brushing can cause as much damage to the teeth and gums as not brushing at all. Lots of people don’t brush for long enough, don’t floss and don’t see a dentist regularly.");
        tipslist.add(health_tips);
        health_tips=new Health_Tips("Neurobics for your mind","Get your brain fizzing with energy. American researchers coined the term ‘neurobics’ for tasks which activate the brain's own biochemical pathways and to bring new pathways online that can help to strengthen or preserve brain circuits");
        tipslist.add(health_tips);
        health_tips=new Health_Tips("Bone up daily","Get your daily calcium by popping a tab, chugging milk or eating yoghurt. It’ll keep your bones strong. Remember that your bone density declines after the age of 30. You need at least 200 milligrams daily, which you should combine with magnesium, or it simply won’t be absorbed.");
        tipslist.add(health_tips);
        health_tips=new Health_Tips("Stop fuming","Don’t smoke and if you smoke already, do everything in your power to quit.");
        tipslist.add(health_tips);
    }

}
