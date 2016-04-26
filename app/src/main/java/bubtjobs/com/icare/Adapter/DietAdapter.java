package bubtjobs.com.icare.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import bubtjobs.com.icare.Model.Diet;
import bubtjobs.com.icare.Others.CommonFunction;
import bubtjobs.com.icare.R;

/**
 * Created by Murtuza on 4/19/2016.
 */
public class DietAdapter extends ArrayAdapter<Diet>{

    private ArrayList<Diet> todayDietList;
    private Context context;
    CommonFunction function;

    public DietAdapter(Context context, ArrayList<Diet> todayDietList) {
        super(context, R.layout.custom_diet_chart,todayDietList);
        this.context = context;
        this.todayDietList = todayDietList;
        function=new CommonFunction();
    }
    static class ViewHolder {
        TextView dietId_tv;
        TextView diet_type_tv;
        TextView menu_tv;
        TextView time_tv;
        TextView date_tv;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.custom_diet_chart, null);

            viewHolder = new ViewHolder();
            viewHolder.dietId_tv = (TextView) convertView.findViewById(R.id.dietId_tv);
            viewHolder.diet_type_tv = (TextView) convertView.findViewById(R.id.diet_type_tv);
            viewHolder.menu_tv = (TextView) convertView.findViewById(R.id.menu_tv);
            viewHolder.time_tv = (TextView) convertView.findViewById(R.id.time_tv);
            viewHolder.date_tv = (TextView) convertView.findViewById(R.id.date_tv);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.dietId_tv.setText(todayDietList.get(position).getDietId());
        viewHolder.diet_type_tv.setText(todayDietList.get(position).getDietType());
        viewHolder.menu_tv.setText("Menu: "+todayDietList.get(position).getMenu());
        viewHolder.time_tv.setText("Time: "+todayDietList.get(position).getDietTime());


        //
        String currentDate=function.currentDate();
        String year=currentDate.substring(0, 4);
        String month=currentDate.substring(4, 6);
        String day=currentDate.substring(6, 8);
        //

        if(todayDietList.get(position).getIsdaily().equals("1")  && todayDietList.get(position).getDietDate().equals(day+"/"+month+"/"+year) ){
            viewHolder.date_tv.setText("Dialy Alarm");
        }
       else{

            viewHolder.date_tv.setText("Date: "+todayDietList.get(position).getDietDate());
        }


        return convertView;
    }
}
