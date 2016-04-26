package bubtjobs.com.icare.Adapter;

import android.Manifest;
import android.app.AlertDialog;
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
        Button dr_details;


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
            viewHolder.dr_details = (Button) convertView.findViewById(R.id.dr_details);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final String _name=doctorList.get(position).getName();
        final  String _appoinment=doctorList.get(position).getAppoinment();
        final String _details=doctorList.get(position).getDetails();
        final String _phone=doctorList.get(position).getPhone();
        final String _email=doctorList.get(position).getEmail();



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
                smsIntent.putExtra("sms_body", "Hello " + doctorList.get(position).getName());
                context.startActivity(smsIntent);
            }
        });

        viewHolder.dr_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.ad_doctor_details, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setView(promptsView);

                final TextView name = (TextView) promptsView.findViewById(R.id.name);
                final TextView appoinment = (TextView) promptsView.findViewById(R.id.appoinment);
                final TextView details = (TextView) promptsView.findViewById(R.id.details);
                final TextView phone = (TextView) promptsView.findViewById(R.id.phone);
                final TextView email = (TextView) promptsView.findViewById(R.id.email);

                name.setText("Doctor Name: "+_name);
                appoinment.setText("Appoinment: "+_appoinment);
                details.setText("Details: " + _details);
                phone.setText("Phone: " + _phone);
                email.setText("Email: " + _email);

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });

        return convertView;
    }
}
