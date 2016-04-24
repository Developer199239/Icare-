package bubtjobs.com.icare.Adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import bubtjobs.com.icare.Model.Medical_History;
import bubtjobs.com.icare.R;

/**
 * Created by Murtuza on 4/19/2016.
 */
public class Medial_History_Adapter extends ArrayAdapter<Medical_History> {

    private ArrayList<Medical_History> historyList;
    private Context context;

    public Medial_History_Adapter(Context context, ArrayList<Medical_History> historyList) {
        super(context, R.layout.custom_history_row, historyList);
        this.context = context;
        this.historyList = historyList;
    }

    static class ViewHolder {
        TextView historyId_tv;
        TextView date_tv;
        TextView name_tv;
        TextView detatils_tv;
        ImageButton pre_ib;


    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.custom_history_row, null);
            viewHolder = new ViewHolder();
            viewHolder.historyId_tv = (TextView) convertView.findViewById(R.id.historyId_tv);
            viewHolder.date_tv = (TextView) convertView.findViewById(R.id.date_tv);
            viewHolder.name_tv = (TextView) convertView.findViewById(R.id.name_tv);
            viewHolder.detatils_tv = (TextView) convertView.findViewById(R.id.detatils_tv);
            viewHolder.pre_ib = (ImageButton) convertView.findViewById(R.id.pre_ib);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String date=historyList.get(position).getDate();
        String year=date.substring(0, 4);
        String month=date.substring(4, 6);
        String day=date.substring(6,8);


        viewHolder.historyId_tv.setText(historyList.get(position).getTableId());
        viewHolder.date_tv.setText("Date: "+day+"/"+month+"/"+year);
        viewHolder.name_tv.setText("Doctor Name: "+historyList.get(position).getDoctorName());
        viewHolder.detatils_tv.setText("Details: "+historyList.get(position).getDetails());


        File path = new File(Environment.getExternalStorageDirectory() + "/Medical History/"+historyList.get(position).getImage());
        if(!path.exists())
        {
            Log.i("query", "Image not found");
            viewHolder.pre_ib.setImageResource(R.drawable.image_not_found);
        }
        else{
            try {
                Bitmap bmp = BitmapFactory.decodeFile(String.valueOf(path));
               //.setImageBitmap(bmp);
                viewHolder.pre_ib.setImageBitmap(bmp);
                Log.d("query", "image found");
            }
            catch (Exception e)
            {
                Log.d("query", "error image");
                viewHolder.pre_ib.setImageResource(R.drawable.image_not_found);
            }
        }


//        //
//        viewHolder.pre_ib.buildDrawingCache();
//        final Bitmap bitmap=viewHolder.pre_ib.getDrawingCache();

        viewHolder.pre_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.custome_prescription_image_view, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setView(promptsView);
                final ImageView imageView = (ImageView) promptsView
                        .findViewById(R.id.pre_view);
//                final Button userInput = (Button) promptsView
//                        .findViewById(R.id.done);


//                userInput.setHint("ok");

                File path = new File(Environment.getExternalStorageDirectory() + "/Medical History/"+historyList.get(position).getImage());
                if(!path.exists())
                {
                    Log.i("query", "Image not found");
                    imageView.setImageResource(R.drawable.image_not_found);
                }
                else{
                    try {
                        Bitmap bmp = BitmapFactory.decodeFile(String.valueOf(path));
                        //.setImageBitmap(bmp);
                        imageView.setImageBitmap(bmp);
                        Log.d("query", "image found");
                    }
                    catch (Exception e)
                    {
                        Log.d("query", "error image");
                        imageView.setImageResource(R.drawable.image_not_found);
                    }
                }


                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setNegativeButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });


                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();

            }
        });



//        File paht1 = new File(Environment.getExternalStorageDirectory() + "/mydir/myiamge.png");
//
//
//
//        viewHolder.pre_ib.setImageResource(R.drawable.app_logo_2);


//        viewHolder.sms_bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Toast.makeText(context, "sms", Toast.LENGTH_SHORT).show();
//                Uri uri = Uri.parse("smsto:" +doctorList.get(position).getPhone());
//                Intent smsIntent = new Intent(Intent.ACTION_SENDTO, uri);
//                smsIntent.putExtra("sms_body","Hello "+doctorList.get(position).getName());
//                context.startActivity(smsIntent);
//            }
//        });

        return convertView;
    }

}
