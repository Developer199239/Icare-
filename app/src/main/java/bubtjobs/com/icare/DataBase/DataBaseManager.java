package bubtjobs.com.icare.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import bubtjobs.com.icare.Model.Profile;
import bubtjobs.com.icare.Model.Profile_Add;

/**
 * Created by Mobile App Develop on 20-4-16.
 */
public class DataBaseManager {
    private DatabaseHelper helper;
    private SQLiteDatabase database;
    Profile profile;


    public DataBaseManager(Context context){
        helper=new DatabaseHelper(context);
        profile=new Profile();
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
}
