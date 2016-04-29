package bubtjobs.com.icare.Fragment;


import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.TimeZone;

import bubtjobs.com.icare.DataBase.DataBaseManager;
import bubtjobs.com.icare.Model.Medical_History;
import bubtjobs.com.icare.Others.CommonFunction;
import bubtjobs.com.icare.Others.SessionManager;
import bubtjobs.com.icare.R;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class Add_Medical_History extends Fragment {
    @Bind(R.id.pre_image)ImageView pre_image;
    @Bind(R.id.take_prescription_bt) Button take_prescription_bt;
    @Bind(R.id.date_bt) Button date_bt;
    @Bind(R.id.name_et) EditText name_et;
    @Bind(R.id.details_et) EditText details_et;

    private static final int RESULT_LOAD_IMAGE=1;
    int H=0,M=0;
    String YY,MM,DD,TimeFormat;

    CommonFunction function;
    boolean isPicTaken=false;
    DataBaseManager manager;
    Medical_History medical_history;
    SessionManager sessionManager;

    File mydir = new File(Environment.getExternalStorageDirectory() + "/Medical History");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_add__medical__history, container, false);
        ButterKnife.bind(this, view);
        isPicTaken=false;
        function=new CommonFunction();
        manager=new DataBaseManager(getActivity());
        sessionManager=new SessionManager(getActivity());
        return  view;
    }

    @OnClick(R.id.take_prescription_bt)
    public void take_prescription(){
        pre_image.setImageResource(0);
        Intent galleryIntent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RESULT_LOAD_IMAGE  && resultCode==getActivity().RESULT_OK && data!=null){
            try {
                Uri selectedImage=data.getData();
                pre_image.setImageURI(selectedImage);
                isPicTaken=true;
               // Toast.makeText(getActivity(), "ok OnActivityResult "+selectedImage, Toast.LENGTH_LONG).show();

            }
            catch (Exception e)
            {
                isPicTaken=false;
               // Toast.makeText(getActivity(), "Error ONActivity Result "+e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }
    @OnClick(R.id.date_bt)
    public void pickdate(){
        Calendar cal = Calendar.getInstance(TimeZone.getDefault()); // Get current date
        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), datePickerListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH));
        datePicker.setCancelable(false);
        datePicker.setTitle("Select the date");
        datePicker.show();
    }


    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,int selectedMonth, int selectedDay) {

            String year1 = String.valueOf(selectedYear);
            String  month1 = String.valueOf(selectedMonth + 1);
            String day1 = String.valueOf(selectedDay);

            if(month1.length()==1)
                month1="0"+month1;
            if(day1.length()==1)
                day1="0"+day1;

            YY=year1;
            MM=month1;
            DD=day1;



            if(Integer.parseInt(YY+MM+DD)>Integer.parseInt(function.currentDate()))
            {
                Toast.makeText(getActivity(), "Please Select Valid Date", Toast.LENGTH_SHORT).show();
            }
            else{
                date_bt.setText(DD+"/"+MM+"/"+YY);
            }

            //Toast.makeText(getActivity(), day1+"/"+month1+"/"+year1, Toast.LENGTH_SHORT).show();

        }
    };


    @OnClick(R.id.add_me_his_bt)
    public void add_medical_history(){
        if(!mydir.exists())
        {
            mydir.mkdirs();
            Log.d("error", "folder create");
        }

        if(function.isEmpty(name_et) &&function.isEmpty(details_et) && function.checkButtonValue(date_bt,"Pick Date") && isPicTaken)
        {

            String doctorName=name_et.getText().toString();
            String details=details_et.getText().toString();
            String date=YY+MM+DD;
            String userId=sessionManager.getCurrentPersonId();
            // image part
            String pic_name=function.alarmCodeGenerate()+".png";
            File file=new File(mydir,pic_name);
            pre_image.buildDrawingCache();
            Bitmap bitmap=pre_image.getDrawingCache();
            OutputStream output;

            try{
                output=new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG,100,output);
                output.flush();
                output.close();
                Log.d("error", "insert");

                medical_history=new Medical_History(userId,pic_name,doctorName,details,date,"1");
                Boolean isinsert=manager.add_medical_history(medical_history);
                if(isinsert){
                    pre_image.setImageResource(0);
                    details_et.getText().clear();
                    name_et.getText().clear();
                    date_bt.setText("Pick Date");
                    isPicTaken=false;
                    Toast.makeText(getActivity(), "Add Medical History  Successfully", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }

            }
            catch (Exception e)
            {
                Log.d("error", "fial="+e.toString());
                Toast.makeText(getActivity(), "Error "+e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(getActivity(), "Please Insert All Fields", Toast.LENGTH_SHORT).show();
        }

    }
    @OnClick(R.id.cancel_bt)
    public void cancel(){
        GeneralInfo currentFragment=new GeneralInfo();
        FragmentManager manager=getFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.replace(R.id.homeFragment,currentFragment);
        transaction.commit();
    }
}
