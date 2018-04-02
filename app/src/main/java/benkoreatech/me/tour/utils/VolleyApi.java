package benkoreatech.me.tour.utils;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import benkoreatech.me.tour.interfaces.TourSettings;
import benkoreatech.me.tour.objects.Constants;
import benkoreatech.me.tour.objects.Item;
import benkoreatech.me.tour.objects.SearchStay;
import benkoreatech.me.tour.objects.StayItem;
import benkoreatech.me.tour.objects.areaCode;
import benkoreatech.me.tour.objects.categoryCode;
import benkoreatech.me.tour.objects.categoryItem;

public class VolleyApi implements Response.Listener<JSONObject>,Response.ErrorListener{

    Context context;
    TourSettings tourSettings;
    RequestQueue requestQueue;
    String Keyword;
    Item item;
    String URL;


    public VolleyApi(Context context, TourSettings tourSettings) {
        this.context = context;
        this.tourSettings = tourSettings;
        requestQueue = Volley.newRequestQueue(context);
    }

    public void fetchData(String URL,String keyword,Item item){
        this.Keyword=keyword;
        this.item=item;
        this.URL=URL;
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, URL, null,this,this);
        requestQueue.add(request);
    }

    @Override
    public void onResponse(JSONObject response) {
        Log.d("HeroJongi"," Response volley "+response);
        if(response!=null) {
            Gson gson = new Gson();
               // api call to fetch the area codes
            if (Keyword.equalsIgnoreCase(Constants.areaCode)) {
                try {
                    // area code
                    areaCode areaCode = gson.fromJson(String.valueOf(response), areaCode.class);
                    // list of items for area code
                    List<Item> itemList = areaCode.getResponse().getBody().getItems().getItem();
                    if (tourSettings != null) {
                        // send this list by interface tour settings using method fill city to maps activity
                        tourSettings.FillCity(itemList);
                    }
                } catch (Exception exception) {
                }
            }
        }
        }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("HeroJongi"," fail volley api "+error.getMessage());
    }
}
