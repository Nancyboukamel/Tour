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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import benkoreatech.me.tour.interfaces.placeInfoInterface;
import benkoreatech.me.tour.objects.detailImageItem;

public class detailImageVolley implements Response.Listener<JSONObject>,Response.ErrorListener{

    Context context;
    benkoreatech.me.tour.interfaces.placeInfoInterface placeInfoInterface;
    RequestQueue requestQueue;

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
            // if no images is found then set it to first image
            if(placeInfoInterface!=null){
                placeInfoInterface.setImagesIfNoExist();
            }
        }
    }
}
