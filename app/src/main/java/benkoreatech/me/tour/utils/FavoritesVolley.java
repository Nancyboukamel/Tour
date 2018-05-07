package benkoreatech.me.tour.utils;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import benkoreatech.me.tour.interfaces.categoryInterface;
import benkoreatech.me.tour.objects.Constants;
import benkoreatech.me.tour.objects.FavoriteList;
import benkoreatech.me.tour.objects.Favorites;
import benkoreatech.me.tour.objects.Item;
import benkoreatech.me.tour.objects.areaCode;


public class FavoritesVolley  implements Response.Listener<JSONObject>,Response.ErrorListener {

    categoryInterface  categoryInterface;
    Context context;
    RequestQueue requestQueue;

    public FavoritesVolley(benkoreatech.me.tour.interfaces.categoryInterface categoryInterface, Context context) {
        this.categoryInterface = categoryInterface;
        this.context=context;
        requestQueue = Volley.newRequestQueue(context);
    }

    public void getFavorites(final String email, final String URL){
        String url=URL+"?email="+email;
        // In login or registration case we do post request
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null,this,this);
        requestQueue.add(request);
    }


    @Override
    public void onErrorResponse(VolleyError error) {
    }

    @Override
    public void onResponse(JSONObject response) {
        if(response!=null) {
            Gson gson = new Gson();
            // api call to fetch the area codes
                try {
                    // area code
                    FavoriteList favoriteList = gson.fromJson(String.valueOf(response), FavoriteList.class);
                    // list of items for area code
                   List<Favorites> itemfavList = favoriteList.getItem();
                   List<Object> objectList=new ArrayList<>();
                   objectList.addAll(itemfavList);
                    if(categoryInterface!=null && itemfavList.size()>0){
                        categoryInterface.setPinsonMap(objectList);
                        categoryInterface.setListareaBasedItems(objectList);
                    }
                } catch (Exception exception) {
                }
            }
        }
    }

