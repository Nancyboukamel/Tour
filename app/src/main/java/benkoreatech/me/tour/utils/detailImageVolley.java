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
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import benkoreatech.me.tour.interfaces.categoryInterface;
import benkoreatech.me.tour.interfaces.placeInfoInterface;
import benkoreatech.me.tour.objects.Item;
import benkoreatech.me.tour.objects.areaBasedItem;
import benkoreatech.me.tour.objects.areaBasedList;
import benkoreatech.me.tour.objects.detailImage;
import benkoreatech.me.tour.objects.detailImageItem;
import benkoreatech.me.tour.objects.detailIntroItem;

public class detailImageVolley implements Response.Listener<JSONObject>,Response.ErrorListener{

    Context context;
    benkoreatech.me.tour.interfaces.placeInfoInterface placeInfoInterface;
    RequestQueue requestQueue;
    Item item;

    public detailImageVolley(Context context, placeInfoInterface placeInfoInterface) {
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

    }

    @Override
    public void onResponse(JSONObject response) {
        Log.d("DetailImage"," Response "+response);
        Gson gson=new Gson();
        try {
          JSONObject items = response.getJSONObject("response").getJSONObject("body").getJSONObject("items");
            JSONArray detailImageItems= (JSONArray) items.get("item");
            List<detailImageItem> listdata = new ArrayList<detailImageItem>();
            Gson json=new Gson();
            if (detailImageItems != null) {
                    for (int i=0;i<detailImageItems.length();i++){
                    detailImageItem _item=json.fromJson(String.valueOf(detailImageItems.getString(i)),detailImageItem.class);
                    listdata.add(_item);
                }
                if(placeInfoInterface!=null){
                        placeInfoInterface.setImages(listdata);
                }
            }
        }
        catch (Exception exception) {
            Log.d("DetailImage","deatilImage fail xx "+exception.getMessage());

        }
    }
}
