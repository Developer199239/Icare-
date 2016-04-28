package bubtjobs.com.icare.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import bubtjobs.com.icare.Model.Doctor;
import bubtjobs.com.icare.Model.Health_Tips;
import bubtjobs.com.icare.R;

/**
 * Created by Murtuza on 4/19/2016.
 */
public class HealthTipsAdapter extends ArrayAdapter<Health_Tips> {

    private ArrayList<Health_Tips> tipsList;
    private Context context;

    public HealthTipsAdapter(Context context, ArrayList<Health_Tips> tipsList) {
        super(context, R.layout.custom_healthtips_row, tipsList);
        this.context = context;
        this.tipsList = tipsList;
    }

    static class ViewHolder {
        TextView title_tv;
        TextView des_tv;


    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.custom_healthtips_row, null);

            viewHolder = new ViewHolder();
            viewHolder.title_tv = (TextView) convertView.findViewById(R.id.title_tv);
            viewHolder.des_tv = (TextView) convertView.findViewById(R.id.des_tv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.title_tv.setText(tipsList.get(position).getTitle());
        viewHolder.des_tv.setText(tipsList.get(position).getDes());
        return convertView;
    }
}
