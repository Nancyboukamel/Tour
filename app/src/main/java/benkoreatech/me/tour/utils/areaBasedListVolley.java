package benkoreatech.me.tour.utils;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import benkoreatech.me.tour.interfaces.TourSettings;
import benkoreatech.me.tour.interfaces.categoryInterface;
import benkoreatech.me.tour.objects.Item;
import benkoreatech.me.tour.objects.areaBasedItem;
import benkoreatech.me.tour.objects.areaBasedList;
import benkoreatech.me.tour.objects.areaCode;

public class areaBasedListVolley  implements Response.Listener<JSONObject>,Response.ErrorListener {
    Context context;
    categoryInterface categoryInterface;
    RequestQueue requestQueue;
    int code;

    public areaBasedListVolley(Context context, categoryInterface categoryInterface) {
        this.context = context;
        this.categoryInterface=categoryInterface;
        requestQueue = Volley.newRequestQueue(context);
    }

    public void fetchData(String URL,int code){
        this.code=code;
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, URL, null,this,this);
        requestQueue.add(request);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {
        Gson gson=new Gson();
        try{
            areaBasedList areaBasedList=gson.fromJson(String.valueOf(response), areaBasedList.class);
            List<areaBasedItem> itemListsub = areaBasedList.getResponse().getBody().getItems().getItem();
            if(categoryInterface!=null){
                categoryInterface.setPins(itemListsub,code);
            }
        }
        catch (Exception exception){
            try{
                JSONObject items=response.getJSONObject("response").getJSONObject("body").getJSONObject("items");
                JSONObject items1=items.getJSONObject("item");
                areaBasedItem _item=gson.fromJson(String.valueOf(items1),areaBasedItem.class);
                List<areaBasedItem> itemList=new ArrayList<>();
                itemList.add(_item);
                if(categoryInterface!=null) {
                    categoryInterface.setPins(itemList, code);
                }
            }
            catch(Exception ex){

            }
        }
    }
}
