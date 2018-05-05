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

import java.util.ArrayList;
import java.util.List;

import benkoreatech.me.tour.interfaces.categoryInterface;
import benkoreatech.me.tour.objects.FestivalItem;
import benkoreatech.me.tour.objects.areaBasedItem;
import benkoreatech.me.tour.objects.areaBasedList;
import benkoreatech.me.tour.objects.searchFestival;

public class FestivalVolley   implements Response.Listener<JSONObject>,Response.ErrorListener {
    Context context;
    benkoreatech.me.tour.interfaces.categoryInterface categoryInterface;
    RequestQueue requestQueue;

    public FestivalVolley(Context context, categoryInterface categoryInterface) {
        this.context = context;
        this.categoryInterface=categoryInterface;
        requestQueue = Volley.newRequestQueue(context);
    }

    public void fetchData(String URL){
        Log.d("position"," area based item "+URL);
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, URL, null,this,this);
        requestQueue.add(request);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {
        Log.d("position"," Response "+response);
        Gson gson=new Gson();
        try{
            searchFestival searchFestival = gson.fromJson(String.valueOf(response), searchFestival.class);
            Log.d("position"," here "+searchFestival);
          List<FestivalItem> itemListsub = searchFestival.getResponse().getBody().getItems().getItem();

             if(categoryInterface!=null){
                 categoryInterface.setPins(itemListsub);
             }
        }
        catch(Exception exception){
            JSONObject items = null;
            Log.d("position"," error "+exception.getMessage());
            try {
                items = response.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONObject("item");
                FestivalItem _item = gson.fromJson(String.valueOf(items), FestivalItem.class);
                List<FestivalItem> itemList = new ArrayList<>();
                itemList.add(_item);
                for(FestivalItem festivalItem:itemList){
                    Log.d("position"," Item name2 "+festivalItem.getTitle());
                }
                if(categoryInterface!=null){
                    categoryInterface.setPins(itemList);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}
