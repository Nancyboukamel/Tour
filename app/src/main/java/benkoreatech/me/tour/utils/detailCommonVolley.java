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

import benkoreatech.me.tour.interfaces.placeInfoInterface;
import benkoreatech.me.tour.objects.detailCommonItem;

/**
 * Created by nancy on 2/25/2018.
 */

public class detailCommonVolley  implements Response.Listener<JSONObject>,Response.ErrorListener  {

    Context context;
    placeInfoInterface detailCommonInterface;
    RequestQueue requestQueue;

    public detailCommonVolley(Context context, placeInfoInterface detailCommonInterface) {
        this.context = context;
        this.detailCommonInterface = detailCommonInterface;
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
        Gson gson=new Gson();
        try {
            JSONObject items=response.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONObject("item");
            detailCommonItem _item=gson.fromJson(String.valueOf(items),detailCommonItem.class);
            if(detailCommonInterface!=null) {
               detailCommonInterface.details(_item);
            }
        }
        catch (Exception exception) {
        }
        }
    }

