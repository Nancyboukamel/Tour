package benkoreatech.me.tour.utils;


import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import benkoreatech.me.tour.interfaces.RegistrationSuccess;
import benkoreatech.me.tour.objects.Constants;

public class Registration implements Response.Listener<String>,Response.ErrorListener{

    RegistrationSuccess registrationSuccess;
    String URL;

    // constructor with name of class
    public Registration(RegistrationSuccess registrationSuccess) {
        // interface declaration
        this.registrationSuccess=registrationSuccess;
    }

    public void register(final String name, final String email, final String password, final String URL){
        String tag_string_req = "req_register";
        this.URL=URL;
        StringRequest strReq = new StringRequest(Request.Method.POST,URL,this,this){
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                if(URL.equalsIgnoreCase(Constants.register)) {
                    params.put("name", name);
                    params.put("email", email);
                    params.put("password", password);
                }
                else if(URL.equalsIgnoreCase(Constants.login)){
                    params.put("email", email);
                    params.put("password", password);
                }
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    @Override
    public void onResponse(String response) {
        Log.d("HeroJongi"," Response "+response);
       try{
           JSONObject jObj = new JSONObject(response);
           boolean error = jObj.getBoolean("error");
           if (!error) {
               // registration success
               if(registrationSuccess!=null){
                   if(URL.equalsIgnoreCase(Constants.register)) {
                       registrationSuccess.onRegistrationSuccess();
                   }
                   else if(URL.equalsIgnoreCase(Constants.login)){
                       registrationSuccess.onLoginSuccess();
                   }
               }
           }
           else{
               // registration failed
           }
       }
       catch (JSONException exception){
         Log.d("HeroJongi"," error "+exception.getMessage());

       }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("HeroJongi"," error "+error.getMessage());
    }
}
