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
        // In login or registration case we do post request
        StringRequest strReq = new StringRequest(Request.Method.POST,URL,this,this){
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                // if its register then pass name, email and password parameter
                if(URL.equalsIgnoreCase(Constants.register)) {
                    params.put("name", name);
                    params.put("email", email);
                    params.put("password", password);
                }
                // if its login just pass email and password as post parameters
                else if(URL.equalsIgnoreCase(Constants.login)){
                    params.put("email", email);
                    params.put("password", password);
                }
                return params;
            }
        };
        // handle api call
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    // if response succeeded
    @Override
    public void onResponse(String response) {
        Log.d("HeroJongi", " Response " + response+" "+response.contains("error"));
            try {
                JSONObject jObj = new JSONObject(response);
                boolean error = jObj.getBoolean("error");
                // if error is false then its succeeded
                if (!error) {
                    // registration success
                    if (registrationSuccess != null) {
                        // if its register calk the abstract method onRegistration success
                        if (URL.equalsIgnoreCase(Constants.register)) {
                            registrationSuccess.onRegistrationSuccess();
                            // if its register calk the abstract method onLogin success and pass the name from response to save in shared preference
                        } else if (URL.equalsIgnoreCase(Constants.login)) {
                            String name=jObj.getJSONObject("user").getString("name");
                            if(name!=null && !name.equalsIgnoreCase("")) {
                                registrationSuccess.onLoginSuccess(name);
                            }
                        }
                    }
                }
                // if error is true then call on Login fail (abstract method)
                else {
           if(registrationSuccess!=null){
               registrationSuccess.onLoginFail();
           }
                }
            } catch (JSONException exception) {
                Log.d("HeroJongi", " error " + exception.getMessage());

            }
        }

    // on error response if some error happen or internet is off
    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("HeroJongi"," error "+error.getMessage());
    }
}
