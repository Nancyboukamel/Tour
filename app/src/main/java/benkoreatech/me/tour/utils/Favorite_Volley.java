package benkoreatech.me.tour.utils;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import benkoreatech.me.tour.interfaces.placeInfoInterface;

public class Favorite_Volley implements Response.Listener<JSONArray>,Response.ErrorListener{
    Context context;
    placeInfoInterface placeInfoInterface;
    RequestQueue requestQueue;
    int status;

    public Favorite_Volley(Context context, placeInfoInterface placeInfoInterface) {
        this.context = context;
        this.placeInfoInterface=placeInfoInterface;
        requestQueue = Volley.newRequestQueue(context);
    }

    public void fetchData(final String URL, final int status){
        this.status=status;
            JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET, URL, null,this,this);
            requestQueue.add(request);

    }


    @Override
    public void onErrorResponse(VolleyError error) {
     Log.d("HeroJongi"," error addition to favorite "+error);
    }



    @Override
    public void onResponse(JSONArray response) {
        Log.d("HeroJongi"," json array "+response);
        try {
            String message=response.getJSONObject(0).get("message").toString();
            if(message.equalsIgnoreCase("your favorite is added successfully")){
                if(status==0) {
                    placeInfoInterface.addToFavorite();
                }
            }
            else if(message.equalsIgnoreCase("Remove item from favorite done successfully")){
                if(status==1){
                    placeInfoInterface.RemoveFromFavorite();
                }
            }
            else if(message.equalsIgnoreCase("exists")){
                placeInfoInterface.addToFavorite();
            }
            else if(message.equalsIgnoreCase("not exists")){
                placeInfoInterface.RemoveFromFavorite();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
