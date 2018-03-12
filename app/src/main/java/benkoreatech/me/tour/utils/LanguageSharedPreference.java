package benkoreatech.me.tour.utils;


import android.content.Context;
import android.content.SharedPreferences;

import benkoreatech.me.tour.objects.Constants;

public class LanguageSharedPreference {
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "My_Location";

    public LanguageSharedPreference(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void save_language(String lang){
        String _lang=null;
        switch (lang){
            case "en":
            _lang=Constants.english;
            break;
            case "ja":
            _lang=Constants.japanese;
            break;
            case "es":
                _lang=Constants.spanish;
                break;
            case "de":
                _lang=Constants.deutsch;
                break;
            case "zh":
                _lang=Constants.chinese;
                break;
            case "fr":
                _lang=Constants.french;
                break;
            case "ru":
                _lang=Constants.russian;
                break;
        }
        if(_lang==null){
           _lang= Constants.english;
        }
        editor.putString("language",_lang);
        editor.apply();
    }

    public String getLanguage(){
        return pref.getString("language","");
    }
}
