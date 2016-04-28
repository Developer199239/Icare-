package bubtjobs.com.icare.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import bubtjobs.com.icare.Activity.MainActivity;
import bubtjobs.com.icare.Activity.Map;
import bubtjobs.com.icare.Model.Health_Tips;
import bubtjobs.com.icare.Model.Lat_Lon;
import bubtjobs.com.icare.R;

/**
 * Created by Murtuza on 4/19/2016.
 */
public class HospitalAdapter extends ArrayAdapter<Lat_Lon> {

    private ArrayList<Lat_Lon> hospitalList;
    private Context context;

    public HospitalAdapter(Context context, ArrayList<Lat_Lon> hospitalList) {
        super(context, R.layout.custom_healthcenter_row, hospitalList);
        this.context = context;
        this.hospitalList = hospitalList;
    }

    static class ViewHolder {
        TextView hospitalName;
        ImageView map_Iv;


    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.custom_healthcenter_row, null);

            viewHolder = new ViewHolder();
            viewHolder.hospitalName = (TextView) convertView.findViewById(R.id.hospitalName);
            viewHolder.map_Iv = (ImageView) convertView.findViewById(R.id.map_Iv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.hospitalName.setText(hospitalList.get(position).getHospitalName());

        viewHolder.map_Iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,hospitalList.get(position).getLat()+" "+hospitalList.get(position).getLon(),Toast.LENGTH_LONG).show();
                Intent intent=new Intent(context, Map.class);
                intent.putExtra("lat1","23.7527022");
                intent.putExtra("lon1","90.3927751");

                intent.putExtra("lat2",""+hospitalList.get(position).getLat());
                intent.putExtra("lon2",""+hospitalList.get(position).getLon());

                context.startActivity(intent);
            }
        });
       // viewHolder.des_tv.setText(tipsList.get(position).getDes());
        return convertView;
    }
}
