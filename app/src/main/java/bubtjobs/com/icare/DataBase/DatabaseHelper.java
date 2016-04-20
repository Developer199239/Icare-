package bubtjobs.com.icare.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by BITM Trainer 401 on 3/12/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    static  final int DATABASE_VERSION=1;
    static  final String DATABASE_NAME="iCare";

    // table user
    static  final String TABLE_USER="user";
    static final String COL_ID="id";
    static final String COL_NAME="name";
    static final String COL_RELATION="relation";
    static final String COL_AGE="age";
    static final String COL_HEIGHT="height";
    static final String COL_WEIGHT="weight";
    static final String COL_MAJOR_DIS="major_dis";
    static final String COL_BLOOD="blood";
    static final String COL_STATUS="status";

    // table diet
    static  final String TABLE_DIET="diet";
    static final String COL_USER_ID="user_id";
    static final String COL_DIET_TYPE="deit_type";
    static final String COL_MENU="menu";
    static final String COL_DATE="date";
    static final String COL_TIME="time";
    static final String COL_ALARM_TYPE="alarm_type";
    static final String COL_ALARM_CODE="alarm_code";

    // table vaccination
    static  final String TABLE_VACCINATION="vaccination";
    static  final String COL_DETAILS="details";
    // table doctors
    static  final String TABLE_DOCTORS="doctors";
    static  final String COL_APPOINMENT="appoinment";
    static  final String COL_PHONE="phone";
    static  final String COL_EMAIL="email";

    // table medical hostory
    static  final String TABLE_MEDICAL_HISTORY="medical_history";
    static  final String COL_IMG="appoinment";
    static  final String COL_DOCTOR_NAME="doctor_name";
    //crate table user
    String CREATE_TABLE_USER=" CREATE TABLE " + TABLE_USER + " ( " + COL_ID +" INTEGER PRIMARY KEY," + COL_NAME +" TEXT," +COL_RELATION +" TEXT,"+COL_AGE +" TEXT,"+COL_HEIGHT +" TEXT,"+COL_WEIGHT +" TEXT,"+COL_MAJOR_DIS +" TEXT,"+COL_BLOOD +" TEXT,"+COL_STATUS +" TEXT )";
    //create table diet
    String CREATE_TABLE_DIET=" CREATE TABLE " + TABLE_DIET + " ( " + COL_ID +" INTEGER PRIMARY KEY," + COL_USER_ID +" TEXT,"+ COL_DIET_TYPE +" TEXT,"+ COL_MENU +" TEXT,"+ COL_DATE +" TEXT,"+ COL_TIME +" TEXT,"+ COL_ALARM_TYPE +" TEXT,"+ COL_ALARM_CODE +" TEXT," +COL_STATUS +" TEXT )";

    // create table vaccination
    String CREATE_TABLE_VACCINATION=" CREATE TABLE " + TABLE_VACCINATION + " ( " + COL_ID +" INTEGER PRIMARY KEY," + COL_USER_ID +" TEXT,"+ COL_DATE +" TEXT,"+ COL_TIME +" TEXT,"+COL_DETAILS +" TEXT,"+ COL_ALARM_TYPE +" TEXT,"+ COL_ALARM_CODE +" TEXT," +COL_STATUS +" TEXT )";

    // create table doctors
    String CREATE_TABLE_DOCTORS=" CREATE TABLE " + TABLE_DOCTORS + " ( " + COL_ID +" INTEGER PRIMARY KEY," + COL_USER_ID +" TEXT," +COL_NAME +" TEXT," +COL_DETAILS +" TEXT,"+COL_APPOINMENT +" TEXT,"+COL_PHONE +" TEXT,"+COL_EMAIL +" TEXT,"+COL_STATUS +" TEXT )";

    // create table medical history
    String CREATE_TABLE_MEDICA_HISTORY=" CREATE TABLE " + TABLE_MEDICAL_HISTORY + " ( " + COL_ID +" INTEGER PRIMARY KEY," + COL_USER_ID +" TEXT,"+ COL_IMG+" TEXT," +COL_DOCTOR_NAME +" TEXT," +COL_DETAILS +" TEXT,"+COL_DATE +" TEXT,"+COL_STATUS +" TEXT )";

//    String CREATE_TABLE_CONTACT=" CREATE TABLE " + TABLE_CONTACT +
//            " ( " + COL_ID +" INTEGER PRIMARY KEY," + COL_NAME +" TEXT," +
//            COL_PHONENO +" TEXT )";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_DIET);
        db.execSQL(CREATE_TABLE_VACCINATION);
        db.execSQL(CREATE_TABLE_DOCTORS);
        db.execSQL(CREATE_TABLE_MEDICA_HISTORY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
