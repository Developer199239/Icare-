package bubtjobs.com.icare.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import bubtjobs.com.icare.Model.Diet;
import bubtjobs.com.icare.Model.Vaccination;
import bubtjobs.com.icare.R;

/**
 * Created by Murtuza on 4/19/2016.
 */
public class VaccinationAdapter extends ArrayAdapter<Vaccination>{

    private ArrayList<Vaccination> vaccinationList;
    private Context context;

    public VaccinationAdapter(Context context, ArrayList<Vaccination> vaccinationList) {
        super(context, R.layout.custom_vaccination_chart,vaccinationList);
        this.context = context;
        this.vaccinationList = vaccinationList;
    }
    static class ViewHolder {
        TextView vaccinationId_tv;
        TextView va_name_tv;
        TextView va_details_tv;
        TextView time_tv;
        TextView date_tv;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.custom_vaccination_chart, null);

            viewHolder = new ViewHolder();
            viewHolder.vaccinationId_tv = (TextView) convertView.findViewById(R.id.vaccinationId_tv);
            viewHolder.va_name_tv = (TextView) convertView.findViewById(R.id.va_name_tv);
            viewHolder.va_details_tv = (TextView) convertView.findViewById(R.id.va_details_tv);
            viewHolder.time_tv = (TextView) convertView.findViewById(R.id.time_tv);
            viewHolder.date_tv = (TextView) convertView.findViewById(R.id.date_tv);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//
//        viewHolder.dietId_tv.setText(todayDietList.get(position).getDietId());
//        viewHolder.diet_type_tv.setText(todayDietList.get(position).getDietType());
//        viewHolder.menu_tv.setText("Menu: "+todayDietList.get(position).getMenu());
//        viewHolder.time_tv.setText("Time: "+todayDietList.get(position).getDietTime());
//        viewHolder.date_tv.setText("Date: "+todayDietList.get(position).getDietDate());

        String date=vaccinationList.get(position).getDate();
        String year=date.substring(0, 4);
        String month=date.substring(4, 6);
        String day=date.substring(6,8);

        String time=vaccinationList.get(position).getHour()+":"+vaccinationList.get(position).getMinute()+" "+vaccinationList.get(position).getFormate();


        viewHolder.vaccinationId_tv.setText(vaccinationList.get(position).getTableId());
        viewHolder.va_name_tv.setText(vaccinationList.get(position).getVa_name());
        viewHolder.va_details_tv.setText("Details: "+vaccinationList.get(position).getDetails());
        viewHolder.time_tv.setText("Time: "+time);
        viewHolder.date_tv.setText("Date: "+day+"/"+month+"/"+year);

        return convertView;
    }
}
