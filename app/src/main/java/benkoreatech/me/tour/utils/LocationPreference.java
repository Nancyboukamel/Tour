package benkoreatech.me.tour.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class LocationPreference {
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "My_Location";

    public LocationPreference(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void saveLocation(String parentName,String childName,String areaCode,String sigungcode){
        editor.putString("areaCode",areaCode);
        editor.putString("sigungCode",sigungcode);
        editor.putString("parentName",parentName);
        editor.putString("childName",childName);
        editor.apply();
    }

    public String getAreaCode(){
        return pref.getString("areaCode","");
    }

    public String getSigungCode(){
        return pref.getString("sigungCode","");
    }

    public String getParentName(){return  pref.getString("parentName","");}

    public String getchildName(){return  pref.getString("childName","");}


}
