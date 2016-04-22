package bubtjobs.com.icare.Adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import bubtjobs.com.icare.Model.Diet;
import bubtjobs.com.icare.Model.Doctor;
import bubtjobs.com.icare.R;

/**
 * Created by Murtuza on 4/19/2016.
 */
public class DoctorAdapter extends ArrayAdapter<Doctor> {

    private ArrayList<Doctor> doctorList;
    private Context context;

    public DoctorAdapter(Context context, ArrayList<Doctor> doctorList) {
        super(context, R.layout.custom_doctor_row, doctorList);
        this.context = context;
        this.doctorList = doctorList;
    }

    static class ViewHolder {
        TextView doctorId_tv;
        TextView name_tv;
        ImageButton call_bt;
        ImageButton sms_bt;


    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.custom_doctor_row, null);

            viewHolder = new ViewHolder();
            viewHolder.doctorId_tv = (TextView) convertView.findViewById(R.id.doctorId_tv);
            viewHolder.name_tv = (TextView) convertView.findViewById(R.id.name_tv);
            viewHolder.call_bt = (ImageButton) convertView.findViewById(R.id.call_bt);
            viewHolder.sms_bt = (ImageButton) convertView.findViewById(R.id.sms_bt);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.doctorId_tv.setText(doctorList.get(position).getId());
        viewHolder.name_tv.setText(doctorList.get(position).getName());

        viewHolder.call_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(context, "call", Toast.LENGTH_SHORT).show();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + doctorList.get(position).getPhone()));
                context.startActivity(callIntent);
            }
        });

        viewHolder.sms_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "sms", Toast.LENGTH_SHORT).show();
                Uri uri = Uri.parse("smsto:" +doctorList.get(position).getPhone());
                Intent smsIntent = new Intent(Intent.ACTION_SENDTO, uri);
                smsIntent.putExtra("sms_body","Hello "+doctorList.get(position).getName());
                context.startActivity(smsIntent);
            }
        });

        return convertView;
    }
}
