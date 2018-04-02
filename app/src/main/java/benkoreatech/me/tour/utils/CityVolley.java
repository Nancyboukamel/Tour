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
import benkoreatech.me.tour.interfaces.TourSettings;
import benkoreatech.me.tour.objects.Item;
import benkoreatech.me.tour.objects.areaCode;

public class CityVolley implements Response.Listener<JSONObject>,Response.ErrorListener {

    Context context;
    TourSettings tourSettings;
    RequestQueue requestQueue;
    Item item;

    public CityVolley(Context context, TourSettings tourSettings) {
        this.context = context;
        this.tourSettings = tourSettings;
        requestQueue = Volley.newRequestQueue(context);
    }

    public void fetchData(String URL,Item item){
        this.item=item;
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, URL, null,this,this);
        requestQueue.add(request);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("HeroJongi","Error in volley "+error.getMessage());
    }

    @Override
    public void onResponse(JSONObject response) {
        Gson gson=new Gson();
            try {
                areaCode areaCode = gson.fromJson(String.valueOf(response), areaCode.class);
                List<Item> itemListsub = areaCode.getResponse().getBody().getItems().getItem();
                // get sub cities and fill them
                tourSettings.FillSubCity(itemListsub, item);
            }
            catch (Exception exception){
                try {
                    // if an error appear then it may be a json object contain 1 item not json array so fetch it like that
                    JSONObject items=response.getJSONObject("response").getJSONObject("body").getJSONObject("items");
                    JSONObject items1=items.getJSONObject("item");
                    Item _item=gson.fromJson(String.valueOf(items1),Item.class);
                    List<Item> itemList=new ArrayList<>();
                    itemList.add(_item);
                    tourSettings.FillSubCity(itemList,item);
                } catch (JSONException e) {
                }

            }
    }
}
