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

import java.util.ArrayList;
import java.util.List;

import benkoreatech.me.tour.interfaces.TourSettings;
import benkoreatech.me.tour.interfaces.categoryInterface;
import benkoreatech.me.tour.objects.Item;
import benkoreatech.me.tour.objects.LocationBasedItem;
import benkoreatech.me.tour.objects.LocationBasedList;
import benkoreatech.me.tour.objects.areaBasedItem;
import benkoreatech.me.tour.objects.areaBasedList;
import benkoreatech.me.tour.objects.areaCode;

public class areaBasedListVolley  implements Response.Listener<JSONObject>,Response.ErrorListener {
    Context context;
    categoryInterface categoryInterface;
    RequestQueue requestQueue;
    int code,status;

    public areaBasedListVolley(Context context, categoryInterface categoryInterface) {
        this.context = context;
        this.categoryInterface=categoryInterface;
        requestQueue = Volley.newRequestQueue(context);
    }

    public void fetchData(String URL,int code,int status){
        this.code=code;
        this.status=status;
        Log.d("HeroJongi"," area based item "+URL);
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, URL, null,this,this);
        requestQueue.add(request);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
    }

    @Override
    public void onResponse(JSONObject response) {
        Log.d("SPECA"," Response "+response);
        Gson gson=new Gson();
        try{
            if(status==0) {
                areaBasedList areaBasedList = gson.fromJson(String.valueOf(response), areaBasedList.class);
                List<areaBasedItem> itemListsub = areaBasedList.getResponse().getBody().getItems().getItem();
                if (categoryInterface != null) {
                    categoryInterface.setPins(itemListsub, code);
                    categoryInterface.setListareaBasedItems(itemListsub);
                }
            }
            // fetching response of a specific marker of location
            else if(status==1){
                // get location based list of a specific marker
                LocationBasedList locationBasedList=gson.fromJson(String.valueOf(response),LocationBasedList.class);
                // list of location based item
                List<LocationBasedItem> locationBasedItems=locationBasedList.getResponse().getBody().getItems().getItem();
                if(categoryInterface!=null){
                    // call the interface category Interface and send locationBasedItems to the set Pin Info
                    categoryInterface.setPinInfo(locationBasedItems);
                }
            }
        }
        catch (Exception exception){
            // some time the response violets the form of location based item for exanple it contain 1 item and its not json array but json object then we fetch it like that
            try{
                JSONObject items = response.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONObject("item");
                if(status==0) {
                    areaBasedItem _item = gson.fromJson(String.valueOf(items), areaBasedItem.class);
                    List<areaBasedItem> itemList = new ArrayList<>();
                    itemList.add(_item);
                    if (categoryInterface != null) {
                        categoryInterface.setPins(itemList, code);
                    }
                }
                else{
                    LocationBasedItem _item = gson.fromJson(String.valueOf(items), LocationBasedItem.class);
                    List<LocationBasedItem> itemList = new ArrayList<>();
                    itemList.add(_item);
                    if (categoryInterface != null) {
                        categoryInterface.setPinInfo(itemList);
                    }
                }
            }
            catch(Exception ex){

            }
        }
    }
}
