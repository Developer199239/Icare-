package bubtjobs.com.icare.Fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import bubtjobs.com.icare.Adapter.HospitalAdapter;
import bubtjobs.com.icare.Model.Lat_Lon;
import bubtjobs.com.icare.Model.Location_Search;
import bubtjobs.com.icare.R;
import bubtjobs.com.icare.Volly.AppController;

/**
 * A simple {@link Fragment} subclass.
 */
public class HealthCenter extends Fragment {

    double lat1=23.7508671,lng1=90.3913638;// bitm
   // double lat1=23.8049244,lng1=90.3550357;// mirpur
    String url2="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=23.7508671,90.3913638&radius=10000&key=AIzaSyBV0WTD9v-KoB-J-SgmsBEJ4CEH-1O_TEQ&type=hospital&sensor=true";
    ListView hospitalListView;
    ProgressDialog pd;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_health_center, container, false);

        hospitalListView=(ListView)view.findViewById(R.id.hospitalListView);


        ProgressBar progressBar;

        pd=ProgressDialog.show(getActivity(), "","Fetching data...",false,true);

        getHospitalNames();

        return view;
    }


    private void  getHospitalNames() {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url2, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                Log.i("query",response.toString());

                ArrayList<Lat_Lon> lat_lonlist=new ArrayList<>();

                try{
                    JSONArray results = response.optJSONArray("results");
                    int len=results.length();
                    for(int i=0;i<results.length();i++) {

                        JSONObject data = results.getJSONObject(i);
                        JSONObject geometry = data.optJSONObject("geometry");
                        JSONObject location = geometry.optJSONObject("location");

                        double lat2 = location.getDouble("lat");
                        double lng2 = location.getDouble("lng");
                        // calculation

                        double m_dis=Math.sqrt((lat2 - lat1)*(lat2-lat1)+(lng2-lng1)*(lng2-lng1));

                        // name retrive
                        String name = data.getString("name");

                        Log.i("error", lat2 + " " + lng2 + " " + name+" == min value== "+m_dis);

                        Lat_Lon lat_lon=new Lat_Lon(String.valueOf(m_dis),String.valueOf(lat2),String.valueOf(lng2),name);
                        lat_lonlist.add(lat_lon);

                    }

                    Collections.sort(lat_lonlist, Lat_Lon.StuNameComparator);

                    HospitalAdapter adapter=new HospitalAdapter(getActivity(),lat_lonlist);
                    hospitalListView.setAdapter(adapter);
                    pd.dismiss();


                }
                catch (Exception e)
                {
                    pd.dismiss();
                    Toast.makeText(getActivity(), ""+e.toString(), Toast.LENGTH_SHORT).show();
                    Log.i("error", e.toString());

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                if (error instanceof NoConnectionError){
                    Toast.makeText(getActivity(), "NO Internet Connection", Toast.LENGTH_SHORT).show();
                }

                Log.i("Error", error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(request);

    }

}
