package bubtjobs.com.icare.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import bubtjobs.com.icare.Model.Diet;
import bubtjobs.com.icare.Model.Diet_Input;
import bubtjobs.com.icare.Model.Doctor;
import bubtjobs.com.icare.Model.Medical_History;
import bubtjobs.com.icare.Model.Profile;
import bubtjobs.com.icare.Model.Profile_Add;
import bubtjobs.com.icare.Model.Vaccination;
import bubtjobs.com.icare.Others.CommonFunction;
import bubtjobs.com.icare.Others.SessionManager;

/**
 * Created by Mobile App Develop on 20-4-16.
 */
public class DataBaseManager {
    private DatabaseHelper helper;
    private SQLiteDatabase database;
    Profile profile;
    CommonFunction function;
    SessionManager sessionManager;
    ArrayList<Diet>dietList;
    Diet diet;
    Vaccination vaccination;
    ArrayList<Vaccination>vaccinationList;
    Doctor doctor;
    ArrayList<Doctor> doctorList;
    Medical_History medical_history;
    ArrayList<Medical_History> historyList;

    public DataBaseManager(Context context){
        helper=new DatabaseHelper(context);
        profile=new Profile();
        function=new CommonFunction();
        sessionManager=new SessionManager(context);
        vaccination=new Vaccination();
    }

    private void open() {
        database = helper.getWritableDatabase();
    }

    private void close() {
        helper.close();
    }
    // add new profile
    public boolean add_profile(Profile_Add profile_add){
        this.open();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DatabaseHelper.COL_NAME, profile_add.getName());
        contentValues.put(DatabaseHelper.COL_RELATION, profile_add.getRelation());
        contentValues.put(DatabaseHelper.COL_AGE, profile_add.getAge());
        contentValues.put(DatabaseHelper.COL_HEIGHT, profile_add.getHeight());
        contentValues.put(DatabaseHelper.COL_WEIGHT, profile_add.getWeight());
        contentValues.put(DatabaseHelper.COL_MAJOR_DIS, profile_add.getMajor_dis());
        contentValues.put(DatabaseHelper.COL_BLOOD, profile_add.getBlood());
        contentValues.put(DatabaseHelper.COL_STATUS, "1");

        long inserted = database.insert(DatabaseHelper.TABLE_USER, null, contentValues);
        this.close();

        if(inserted>0)
        {
            return true;
        }
        else{
            return false;
        }
    }
    // get total user
    public boolean getTotalUser(){
        Boolean temp=false;
        this.open();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_USER + " where " +DatabaseHelper.COL_STATUS+" = 1";
        Log.i("query", query);
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();

        if(cursor!=null && cursor.getCount()>0)
        {
            temp=true;
        }
        else
            temp=false;
        this.close();

        return temp;
    }
    // get all user

    public ArrayList<Profile> getAllUser(){
        this.open();
        ArrayList<Profile> profileList = new ArrayList<>();
        // Cursor cursor = database.query(DatabaseHelper.TABLE_USER, null, null, null, null, null, null);
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_USER + " where " +DatabaseHelper.COL_STATUS+" = 1";
        Log.i("query", query);
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();

        if (cursor != null && cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {

                String userId= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_ID));
                String userName= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_NAME));

                profile=new Profile(userId,userName);
                profileList.add(profile);
                cursor.moveToNext();
            }

        }
        this.close();
        return profileList;
    }
    // add diet
    public boolean addDiet(Diet_Input diet_input){

        this.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_USER_ID,diet_input.getUserId());
        contentValues.put(DatabaseHelper.COL_DIET_TYPE,diet_input.getDietType());
        contentValues.put(DatabaseHelper.COL_MENU,diet_input.getMenu());
        contentValues.put(DatabaseHelper.COL_DATE,Integer.parseInt(diet_input.getDate()));
        contentValues.put(DatabaseHelper.COL_HOUR,diet_input.getHour());
        contentValues.put(DatabaseHelper.COL_MINUTE,diet_input.getMinute());
        contentValues.put(DatabaseHelper.COL_FORMATE,diet_input.getFormate());
        contentValues.put(DatabaseHelper.COL_ALARM_TYPE,diet_input.getAlarmType());
        contentValues.put(DatabaseHelper.COL_ALARM_CODE,diet_input.getAlarmCode());
        contentValues.put(DatabaseHelper.COL_STATUS, diet_input.getStatus());
        long inserted = database.insert(DatabaseHelper.TABLE_DIET, null, contentValues);
        this.close();

        if(inserted>0)
            return true;
        else
            return false;
    }
    public ArrayList<Diet> getDiet(String condition){
        dietList=new ArrayList<>();
        this.open();
        try {
            String query = "SELECT * FROM " + DatabaseHelper.TABLE_DIET + " where " + DatabaseHelper.COL_DATE + " "+condition+" " + function.currentDate()+" and "+DatabaseHelper.COL_STATUS+" = 1 and "+DatabaseHelper.COL_USER_ID+" = "+sessionManager.getCurrentPersonId();
            Log.i("query",query);
            Cursor cursor = database.rawQuery(query, null);
            cursor.moveToFirst();
            if(cursor!=null && cursor.getCount()>0)
            {
                for(int i=0;i<cursor.getCount();i++)
                {
                    String id= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_ID));
                    String userId= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_USER_ID));
                    String dietType= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_DIET_TYPE));
                    String menu= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_MENU));
                    String date= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_DATE));
                    String hour= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_HOUR));
                    String minute= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_MINUTE));
                    String formate= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_FORMATE));
                    String alarmType= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_ALARM_TYPE));
                    String alarmCode= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_ALARM_CODE));

                    String year=date.substring(0, 4);
                    String month=date.substring(4, 6);
                    String day=date.substring(6,8);

                    diet=new Diet(id,dietType,menu,hour+":"+minute+" "+formate,year+"/"+month+"/"+day);
                    dietList.add(diet);
                    cursor.moveToNext();
                }
            }
//            else
//                s="0";
        }
        catch (Exception e)
        {
//            s=e.toString();
        }
        this.close();
        return dietList;
    }

    public int lastIndex(String table)
    {
        int cnt=0;
        this.open();
        Cursor cursor = database.query(table, null, null, null, null, null, null);
        cursor.moveToFirst();
        if(cursor!=null && cursor.getCount()>0)
            cnt=cursor.getCount();

        this.close();
        return cnt;
    }
    public String getPersonName(String id){
        String name="";
        this.open();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_USER + " where " + DatabaseHelper.COL_ID + " = "+id;
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        if(cursor!=null && cursor.getCount()>0)
        {
            name= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_NAME));
        }

        this.close();
        return name;
    }


    public Diet getSingleDiet(String tableId){
        this.open();
        try {
            String query = "SELECT * FROM " + DatabaseHelper.TABLE_DIET + " where " + DatabaseHelper.COL_ID + " =  "+tableId;
            Log.i("query",query);
            Cursor cursor = database.rawQuery(query, null);
            cursor.moveToFirst();
            if(cursor!=null && cursor.getCount()>0)
            {
                for(int i=0;i<cursor.getCount();i++)
                {
                    String id= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_ID));
                    String userId= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_USER_ID));
                    String dietType= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_DIET_TYPE));
                    String menu= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_MENU));
                    String date= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_DATE));
                    String hour= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_HOUR));
                    String minute= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_MINUTE));
                    String formate= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_FORMATE));
                    String alarmType= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_ALARM_TYPE));
                    String alarmCode= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_ALARM_CODE));

                    String year=date.substring(0, 4);
                    String month=date.substring(4, 6);
                    String day=date.substring(6,8);

                    diet=new Diet(id,alarmType,dietType,hour+":"+minute+" "+formate,year+"/"+month+"/"+day);
                    cursor.moveToNext();
                }
            }
            else
                diet=new Diet();
        }
        catch (Exception e)
        {
            diet=new Diet();
        }
        this.close();
        return diet;
    }

    //============================================== vaccination part=============================
    // add diet
    public boolean addVaccination(Vaccination vaccination){

        this.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_USER_ID,vaccination.getUserId());
        contentValues.put(DatabaseHelper.COL_DATE,Integer.parseInt(vaccination.getDate()));
        contentValues.put(DatabaseHelper.COL_HOUR,vaccination.getHour());
        contentValues.put(DatabaseHelper.COL_MINUTE,vaccination.getMinute());
        contentValues.put(DatabaseHelper.COL_FORMATE,vaccination.getFormate());
        contentValues.put(DatabaseHelper.COL_VACCINATION_NAME,vaccination.getVa_name());
        contentValues.put(DatabaseHelper.COL_DETAILS,vaccination.getDetails());
        contentValues.put(DatabaseHelper.COL_ALARM_TYPE,vaccination.getAlarmType());
        contentValues.put(DatabaseHelper.COL_ALARM_CODE,vaccination.getAlarmCode());
        contentValues.put(DatabaseHelper.COL_STATUS, vaccination.getStatus());
        long inserted = database.insert(DatabaseHelper.TABLE_VACCINATION, null, contentValues);
        this.close();

        if(inserted>0)
            return true;
        else
            return false;
    }



    public ArrayList<Vaccination> getVaccination(String condition){
        vaccinationList=new ArrayList<>();
        this.open();
        try {
            String query = "SELECT * FROM " + DatabaseHelper.TABLE_VACCINATION + " where " + DatabaseHelper.COL_DATE + " "+condition+" " + function.currentDate()+" and "+DatabaseHelper.COL_STATUS+" = 1 and "+DatabaseHelper.COL_USER_ID+" = "+sessionManager.getCurrentPersonId();
            Log.i("query",query);
            Cursor cursor = database.rawQuery(query, null);
            cursor.moveToFirst();
            if(cursor!=null && cursor.getCount()>0)
            {
                for(int i=0;i<cursor.getCount();i++)
                {
                    String id= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_ID));
                    String userId= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_USER_ID));
                    String va_name= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_VACCINATION_NAME));
                    String date= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_DATE));
                    String hour= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_HOUR));
                    String minute= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_MINUTE));
                    String formate= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_FORMATE));
                    String details= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_DETAILS));
                    String alarmType= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_ALARM_TYPE));
                    String alarmCode= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_ALARM_CODE));
                    String status= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_STATUS));

                    String year=date.substring(0, 4);
                    String month=date.substring(4, 6);
                    String day=date.substring(6,8);

                    vaccination=new Vaccination(id,userId,va_name,""+date,hour,minute,formate,details,alarmType,alarmCode,status);
                    vaccinationList.add(vaccination);
                    cursor.moveToNext();
                }
            }
//            else
//                vaccination=new Vaccination();
        }
        catch (Exception e)
        {
            //vaccination=new Vaccination();
        }
        this.close();
        return vaccinationList;
    }




    public Vaccination getSingleVaccination(String tableId){
        this.open();
        try {
            String query = "SELECT * FROM " + DatabaseHelper.TABLE_VACCINATION + " where " + DatabaseHelper.COL_ID + " =  "+tableId;
            Log.i("query",query);
            Cursor cursor = database.rawQuery(query, null);
            cursor.moveToFirst();
            if(cursor!=null && cursor.getCount()>0)
            {
                for(int i=0;i<cursor.getCount();i++)
                {
                    String id= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_ID));
                    String userId= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_USER_ID));
                    String va_name= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_VACCINATION_NAME));
                    String date= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_DATE));
                    String hour= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_HOUR));
                    String minute= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_MINUTE));
                    String formate= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_FORMATE));
                    String details= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_DETAILS));
                    String alarmType= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_ALARM_TYPE));
                    String alarmCode= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_ALARM_CODE));

                    String year=date.substring(0, 4);
                    String month=date.substring(4, 6);
                    String day=date.substring(6,8);

                    // diet=new Diet(id,alarmType,dietType,hour+":"+minute+" "+formate,year+"/"+month+"/"+day);
                    vaccination=new Vaccination(id,alarmType,"Vacination, "+details,day+"/"+month+"/"+year,hour+":"+minute+" "+formate);
                    cursor.moveToNext();
                }
            }
            else
                vaccination=new Vaccination();
        }
        catch (Exception e)
        {
            vaccination=new Vaccination();
        }
        this.close();
        return vaccination;
    }

    // add doctor
    public boolean add_Doctor(Doctor doctor){
        this.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_USER_ID,doctor.getUserId());
        contentValues.put(DatabaseHelper.COL_NAME, doctor.getName());
        contentValues.put(DatabaseHelper.COL_DETAILS, doctor.getDetails());
        contentValues.put(DatabaseHelper.COL_PHONE, doctor.getPhone());
        contentValues.put(DatabaseHelper.COL_EMAIL, doctor.getEmail());
        contentValues.put(DatabaseHelper.COL_STATUS, doctor.getStatus());
        long inserted = database.insert(DatabaseHelper.TABLE_DOCTORS, null, contentValues);
        this.close();

        if(inserted>0)
            return true;
        else
            return false;
    }

    public ArrayList<Doctor> getAllDoctor(){
        doctorList=new ArrayList<>();
        this.open();
        try {
            String query = "SELECT * FROM " + DatabaseHelper.TABLE_DOCTORS + " where " +DatabaseHelper.COL_STATUS+" = 1 and "+DatabaseHelper.COL_USER_ID+" = "+sessionManager.getCurrentPersonId();
            Log.i("query",query);
            Cursor cursor = database.rawQuery(query, null);
            cursor.moveToFirst();
            if(cursor!=null && cursor.getCount()>0)
            {
                for(int i=0;i<cursor.getCount();i++)
                {
                    String id= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_ID));
                    String userId= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_USER_ID));
                    String name= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_NAME));
                    String details= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_DETAILS));
                    String phone= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_PHONE));
                    String email= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_EMAIL));
                    String status= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_STATUS));

                    doctor=new Doctor(id,userId,name,details,phone,email,status);
                    doctorList.add(doctor);
                    cursor.moveToNext();
                }
            }
//            else
//                vaccination=new Vaccination();
        }
        catch (Exception e)
        {
            //vaccination=new Vaccination();
        }
        this.close();
        return doctorList;
    }

    // medical history
    public boolean add_medical_history(Medical_History medical_history){

        this.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_USER_ID,medical_history.getUserId());
        contentValues.put(DatabaseHelper.COL_IMG,medical_history.getImage());
        contentValues.put(DatabaseHelper.COL_DOCTOR_NAME,medical_history.getDoctorName());
        contentValues.put(DatabaseHelper.COL_DETAILS,medical_history.getDetails());
        contentValues.put(DatabaseHelper.COL_DATE,Integer.parseInt(medical_history.getDate()));
        contentValues.put(DatabaseHelper.COL_STATUS, "1");

        long inserted = database.insert(DatabaseHelper.TABLE_MEDICAL_HISTORY, null, contentValues);
        this.close();
        if(inserted>0)
            return true;
        else
            return false;
    }


    public ArrayList<Medical_History> getAllMedicalHistory(){
        historyList=new ArrayList<>();
        this.open();
        try {
            String query = "SELECT * FROM " + DatabaseHelper.TABLE_MEDICAL_HISTORY + " where " +DatabaseHelper.COL_STATUS+" = 1 and "+DatabaseHelper.COL_USER_ID+" = "+sessionManager.getCurrentPersonId();

            Log.i("query",query);
            Cursor cursor = database.rawQuery(query, null);
            Log.i("query=", "" + cursor.getCount());
            cursor.moveToFirst();
            if(cursor!=null && cursor.getCount()>0)
            {
                for(int i=0;i<cursor.getCount();i++)
                {
                    Log.i("query",""+i);
                    String id=  cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_ID));
                    String userId=  cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_USER_ID));
                    String image=  cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_IMG));
                    String doctor_name=  cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_DOCTOR_NAME));
                    String details=  cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_DETAILS));
                    String date=  cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_DATE));
                    String status=  cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_STATUS));
                    Log.i("query data",id+" = "+userId+" image= "+image+" doctor name= "+doctor_name+" deatils "+details+" date= "+date+" status "+status);

                    medical_history=new Medical_History(id,userId,image,doctor_name,details,date,status);
                    historyList.add(medical_history);
                    cursor.moveToNext();
                }
            }
//            else
//                vaccination=new Vaccination();
        }
        catch (Exception e)
        {
            //vaccination=new Vaccination();
        }
        this.close();
        //Log.i("query size:", "" + historyList.size());
        return historyList;
    }

    // user single row return
    public Profile_Add getUserProfile(String id){
        Profile_Add profile_add=new Profile_Add();
        this.open();
        try {
            String query = "SELECT * FROM " + DatabaseHelper.TABLE_USER + " where " + DatabaseHelper.COL_ID + " =  "+id;
            Log.i("query",query);
            Cursor cursor = database.rawQuery(query, null);
            cursor.moveToFirst();
            if(cursor!=null && cursor.getCount()>0)
            {
                for(int i=0;i<cursor.getCount();i++)
                {
                    String name= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_NAME));
                    String relation= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_RELATION));
                    String age= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_AGE));
                    String height= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_HEIGHT));
                    String weight= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_WEIGHT));
                    String major_dis= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_MAJOR_DIS));
                    String blood= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_BLOOD));
                    profile_add=new Profile_Add(name,relation,age,height,weight,major_dis,blood);

                }
            }
            else
                profile_add=new Profile_Add();
        }
        catch (Exception e)
        {
            profile_add=new Profile_Add();
        }
        this.close();
        return profile_add;


    }
    //user remove

    public boolean removeRow(String table,String id){
        this.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_STATUS,"0");
        int updated = database.update(table, contentValues, DatabaseHelper.COL_ID + " = " + id, null);
        this.close();
        if (updated > 0) {
            return true;
        } else return false;
    }



    public Diet_Input getSingleRowDiet(String tableId){
        Diet_Input dietInput=new Diet_Input();
        this.open();
        try {
            String query = "SELECT * FROM " + DatabaseHelper.TABLE_DIET + " where " + DatabaseHelper.COL_ID + " =  "+tableId;
            Log.i("query",query);
            Cursor cursor = database.rawQuery(query, null);
            cursor.moveToFirst();
            if(cursor!=null && cursor.getCount()>0)
            {
                for(int i=0;i<cursor.getCount();i++)
                {
                    String id= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_ID));
                    String userId= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_USER_ID));
                    String dietType= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_DIET_TYPE));
                    String menu= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_MENU));
                    String date= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_DATE));
                    String hour= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_HOUR));
                    String minute= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_MINUTE));
                    String formate= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_FORMATE));
                    String alarmType= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_ALARM_TYPE));
                    String alarmCode= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_ALARM_CODE));

                    String year=date.substring(0, 4);
                    String month=date.substring(4, 6);
                    String day=date.substring(6,8);

                    //diet=new Diet(id,alarmType,dietType,hour+":"+minute+" "+formate,year+"/"+month+"/"+day);
                    //cursor.moveToNext();
                    dietInput=new Diet_Input(userId,dietType,menu,date,hour,minute,formate,alarmType,alarmCode,"1");
                }
            }
            else
                dietInput=new Diet_Input();
        }
        catch (Exception e)
        {
            dietInput=new Diet_Input();
        }
        this.close();
        return dietInput;
    }

    public boolean dietUpdate(Diet_Input input){
        this.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_DIET_TYPE,input.getDietType());
        contentValues.put(DatabaseHelper.COL_MENU,input.getMenu());
        contentValues.put(DatabaseHelper.COL_DATE,Integer.parseInt(input.getDate()));
        contentValues.put(DatabaseHelper.COL_HOUR,input.getHour());
        contentValues.put(DatabaseHelper.COL_MINUTE,input.getMinute());
        contentValues.put(DatabaseHelper.COL_FORMATE,input.getFormate());
        contentValues.put(DatabaseHelper.COL_ALARM_TYPE,input.getAlarmType());
        contentValues.put(DatabaseHelper.COL_FORMATE,input.getFormate());
        int updated = database.update(DatabaseHelper.TABLE_DIET, contentValues, DatabaseHelper.COL_ID + " = " + input.getId(), null);
        this.close();
        if (updated > 0) {
            return true;
        } else return false;
    }

    // vaccination

    public Vaccination getSingleRowVaccination(String tableId){
        Vaccination vaccination=new Vaccination();
        this.open();
        try {
            String query = "SELECT * FROM " + DatabaseHelper.TABLE_VACCINATION + " where " + DatabaseHelper.COL_ID + " =  "+tableId;
            Log.i("query",query);
            Cursor cursor = database.rawQuery(query, null);
            cursor.moveToFirst();
            if(cursor!=null && cursor.getCount()>0)
            {
                for(int i=0;i<cursor.getCount();i++)
                {
                    String id= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_ID));
                    String userId= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_USER_ID));
                    String date= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_DATE));
                    String hour= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_HOUR));
                    String minute= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_MINUTE));
                    String formate= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_FORMATE));
                    String vaccination_name= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_VACCINATION_NAME));
                    String details= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_DETAILS));
                    String alarmType= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_ALARM_TYPE));
                    String alarmCode= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_ALARM_CODE));
                    String status= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_STATUS));
//
//                    String year=date.substring(0, 4);
//                    String month=date.substring(4, 6);
//                    String day=date.substring(6,8);

                    Log.i("query data",id+" "+vaccination_name+" "+date);
                    vaccination=new Vaccination(id,userId,vaccination_name,date,hour,minute,formate,details,alarmType,alarmCode,status);


                }
            }
            else
                vaccination=new Vaccination();
        }
        catch (Exception e)
        {
            vaccination=new Vaccination();
        }
        this.close();
        return vaccination;
    }

    public boolean vaccinationUpdate(Vaccination vaccination){
        this.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_VACCINATION_NAME,vaccination.getVa_name());
        contentValues.put(DatabaseHelper.COL_DETAILS,vaccination.getDetails());
        contentValues.put(DatabaseHelper.COL_DATE,Integer.parseInt(vaccination.getDate()));
        contentValues.put(DatabaseHelper.COL_MINUTE,vaccination.getMinute());
        contentValues.put(DatabaseHelper.COL_HOUR,vaccination.getHour());
        contentValues.put(DatabaseHelper.COL_FORMATE,vaccination.getFormate());

        int updated = database.update(DatabaseHelper.TABLE_VACCINATION, contentValues, DatabaseHelper.COL_ID + " = " + vaccination.getTableId(), null);
        this.close();
        if (updated > 0) {
            return true;
        } else return false;
    }

}
