package bubtjobs.com.icare.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import bubtjobs.com.icare.Model.Profile;
import bubtjobs.com.icare.R;

/**
 * Created by Murtuza on 4/19/2016.
 */
public class ProfileViewAdapter extends ArrayAdapter<Profile>{

    private ArrayList<Profile> profileArrayList;
    private Context context;

    public ProfileViewAdapter(Context context, ArrayList<Profile> profileArrayList) {
        super(context, R.layout.custom_row_profile,profileArrayList);
        this.context = context;
        this.profileArrayList = profileArrayList;
    }
    static class ViewHolder {
        TextView userId_Tv;
        TextView name_Tv;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.custom_row_profile, null);

            viewHolder = new ViewHolder();
            viewHolder.userId_Tv = (TextView) convertView.findViewById(R.id.userId_Tv);
            viewHolder.name_Tv = (TextView) convertView.findViewById(R.id.name_Tv);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.userId_Tv.setText(profileArrayList.get(position).getUserId());
        viewHolder.name_Tv.setText(profileArrayList.get(position).getUserName());

        return convertView;
    }
}
