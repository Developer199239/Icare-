package bubtjobs.com.icare.Others;

import java.util.HashMap;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager {
	SharedPreferences perf;
	Editor editor;
	Context _context;
	int PRIVATE_MODE=0;
	private static final String PREF_NAME="jobs";
	private static final String IS_LOGIN="IsLoggedIn";
	public static final String KEY_EMAIL="email";
	public static final String KEY_ID="id";

	// first install
	private static final String IS_FIRST_INSTALL="isFirstInstall";
	// user and password
	private static final String USER_NAME="user_Name";
	private static final String USER_Passwrd="user_password";

	//constructor
	public SessionManager(Context _context) {
		this._context = _context;
		perf=_context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor=perf.edit();
	}


	// install status

	public void setInstallStatus(){
		editor.putString(IS_FIRST_INSTALL,"False");
		editor.commit();
	}
	public boolean getInstallStatus(){
		if(perf.getString(IS_FIRST_INSTALL,"").equals("False"))
			return false;
		else
			return true;
	}

	//set and retrive user and password

	public void setUserName(String userName,String password){
		editor.putString(USER_NAME,userName);
		editor.putString(USER_Passwrd,password);
		editor.commit();
	}

	public boolean getUserName(String name,String password)
	{
		if(perf.getString(USER_NAME,"").equals(name) && perf.getString(USER_Passwrd,"").equals(password))
		return true;
		else
			return false;
	}

	public boolean currentPasswordMatch(String password){
		if(password.equals(perf.getString(USER_Passwrd,"")))
		{
			return true;
		}
		else
			return false;
	}
	public void passwordChange(String pass){
		editor.putString(USER_Passwrd,pass);
		editor.commit();
	}

	public void createLoginSession(String id){

		//store login value is true
		editor.putBoolean(IS_LOGIN,true);
		editor.putString(KEY_ID,id);
		editor.commit();
		
	}
	
	//get store session date
	
	public String getUserId(){
		return perf.getString(KEY_ID,null);
	}
	
	
	//check login
	public void CheckLogin(){
		 // Check login status
        if(!this.isLoggedIn()){
//            // user is not logged in redirect him to Login Activity
//            Intent i = new Intent(_context, Fragment_login.class);
//            // Closing all the Activities
//            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//            // Add new Flag to start new Activity
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//            // Staring Login Activity
//            _context.startActivity(i);
        }
	}
	/**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return perf.getBoolean(IS_LOGIN, false);
    }
    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
//        editor.clear();
//        editor.commit();
//
//        // After logout redirect user to Loing Activity
//        Intent i = new Intent(_context, Fragment_login.class);
//        // Closing all the Activities
//        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        // Add new Flag to start new Activity
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//        // Staring Login Activity
//        _context.startActivity(i);
    }
    
	

}
