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

import org.json.JSONObject;

import benkoreatech.me.tour.interfaces.TourSettings;
import benkoreatech.me.tour.interfaces.placeInfoInterface;
import benkoreatech.me.tour.objects.Item;
import benkoreatech.me.tour.objects.detailCommonItem;
import benkoreatech.me.tour.objects.detailIntroItem;

public class detailIntroVolley implements Response.Listener<JSONObject>,Response.ErrorListener{

    Context context;
    placeInfoInterface placeInfoInterface;
    RequestQueue requestQueue;
    Item item;

    public detailIntroVolley(Context context, placeInfoInterface placeInfoInterface) {
        this.context = context;
        this.placeInfoInterface=placeInfoInterface;
        requestQueue = Volley.newRequestQueue(context);
    }

    public void fetchData(String URL){
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, URL, null,this,this);
        requestQueue.add(request);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("HeroJongi"," failure "+error.getMessage());
    }

    @Override
    public void onResponse(JSONObject response) {
        Log.d("HeroJongi"," detailIntro "+response);
        Gson gson=new Gson();
        try {
            JSONObject items=response.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONObject("item");
            detailIntroItem _item=gson.fromJson(String.valueOf(items),detailIntroItem.class);
            Log.d("HeroJongi","Text is "+_item.toString());
            if(placeInfoInterface!=null) {
                placeInfoInterface.detailsIntro(_item);
            }
        }
        catch (Exception exception) {
            Log.d("HeroJongi","deatilIntro fail"+exception.getMessage());
        }
    }
}
