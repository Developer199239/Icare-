package bubtjobs.com.icare.Fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import bubtjobs.com.icare.Model.Location_Search;
import bubtjobs.com.icare.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HealthCenter extends Fragment {

    Location_Search location_search;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_health_center, container, false);

        location_search=new Location_Search(getActivity());

        int i=location_search.getLocation();
        Toast.makeText(getActivity(), ""+i, Toast.LENGTH_SHORT).show();
        Log.i("location",""+i);

        return view;
    }

}
