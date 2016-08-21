package ittraining.revision.com.kinbech.utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.SharedPreferences;

public class GlobalState extends Application {

    SharedPreferences isLoggedIn;
    SharedPreferences.Editor isLoggedInEditor;

    SharedPreferences validUserInfo;
    SharedPreferences.Editor validUserInfoEditor;

    public static GlobalState singleton;

    public static final String PREFS_IS_LOGGED_IN = "is logged in"; // to check if user is logged in
    public static final String PREFS_VALID_USER_INFO = "valid user info"; // to store the user information like name and phone number

    @SuppressLint("CommitPrefEdits")
    @Override
    public void onCreate() {
        super.onCreate();

        isLoggedIn = this.getSharedPreferences(PREFS_IS_LOGGED_IN, 0);
        isLoggedInEditor = isLoggedIn.edit();

        validUserInfo = this.getSharedPreferences(PREFS_VALID_USER_INFO, 0);
        validUserInfoEditor = validUserInfo.edit();

        singleton = this;
    }

    /**
     * @return MySIngleton instance
     */
    public GlobalState getInstance() {
        return singleton;
    }

    public String getPrefsIsLoggedIn(){
        return isLoggedIn.getString(PREFS_IS_LOGGED_IN,"");
    }

    public void setPrefsIsLoggedIn(String loggedStatus,int resultCode){
        if (resultCode == 1) {
            isLoggedInEditor.putString(PREFS_IS_LOGGED_IN, loggedStatus).commit();
        } else {
            isLoggedInEditor.remove(PREFS_IS_LOGGED_IN).commit();
        }
    }

    public String getPREFS_VALID_USER_INFO(){
        return validUserInfo.getString(PREFS_VALID_USER_INFO,"");
    }

    public void setPrefsValidUserInfo(String validUser,int resultCode){
        if (resultCode == 1) {
            validUserInfoEditor.putString(PREFS_VALID_USER_INFO, validUser).commit();
        } else {
            validUserInfoEditor.remove(PREFS_VALID_USER_INFO).commit();
        }
    }
}
