package bubtjobs.com.icare.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import bubtjobs.com.icare.Model.Diet;
import bubtjobs.com.icare.Model.Diet_Input;
import bubtjobs.com.icare.Model.Profile;
import bubtjobs.com.icare.Model.Profile_Add;
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

    public DataBaseManager(Context context){
        helper=new DatabaseHelper(context);
        profile=new Profile();
        function=new CommonFunction();
        sessionManager=new SessionManager(context);
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
        Cursor cursor = database.query(DatabaseHelper.TABLE_USER, null, null, null, null, null, null);
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
        Cursor cursor = database.query(DatabaseHelper.TABLE_USER, null, null, null, null, null, null);
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
    public ArrayList<Diet> getTodayDiet(){
        dietList=new ArrayList<>();
        this.open();
        try {
            String query = "SELECT * FROM " + DatabaseHelper.TABLE_DIET + " where " + DatabaseHelper.COL_DATE + " == " + function.currentDate()+" and "+DatabaseHelper.COL_USER_ID+" == "+sessionManager.getCurrentPersonId();
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
}
