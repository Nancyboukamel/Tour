package benkoreatech.me.tour.utils;


import android.content.Context;
import android.content.SharedPreferences;

public class SigninPreference {
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "My_Location";

    public SigninPreference(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void isSignin(boolean isLogin,String email,String name){
        editor.putBoolean("login",isLogin);
        editor.putString("email",email);
        editor.putString("username",name);
        editor.apply();
    }

    public boolean getLogin(){
        return pref.getBoolean("login",false);
    }

    public String getUserEmail(){
        return pref.getString("email","");
    }

    public String getUsername(){ return  pref.getString("username","");}

}
