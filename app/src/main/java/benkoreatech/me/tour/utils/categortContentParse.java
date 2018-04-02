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
import benkoreatech.me.tour.objects.categoryCode;
import benkoreatech.me.tour.objects.categoryItem;


public class categortContentParse implements Response.Listener<JSONObject>,Response.ErrorListener {
    Context context;
    RequestQueue requestQueue;
    categoryInterface categoryInterface;
    int code,status;

    public categortContentParse(Context context, categoryInterface categoryInterface) {
        this.context = context;
        this.categoryInterface = categoryInterface;
        requestQueue = Volley.newRequestQueue(context);
    }

    public void fetchData(String URL, int code,int status) {
        this.code=code;
        this.status=status;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, this, this);
        requestQueue.add(request);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {
        Gson gson = new Gson();
        if (categoryInterface != null) {
            try {
                categoryCode categoryCode = gson.fromJson(String.valueOf(response), categoryCode.class);
                List<categoryItem> categoryItems = categoryCode.getResponse().getBody().getItems().getItem();
                //
                if(status==1) {
                    categoryInterface.BigCategory(categoryItems, code);
                }
                // cat 1  available
                else if(status==2){
                    categoryInterface.MediumCategory(categoryItems,code);
                }
                // cat 1 , cat2 available
                else if(status==3){
                    categoryInterface.SmallCategory(categoryItems,code);
                }
            } catch (Exception exception) {
                try {
                    JSONObject items = response.getJSONObject("response").getJSONObject("body");
                    JSONObject items1 = items.getJSONObject("items").getJSONObject("item");
                    categoryItem _categoryItem = gson.fromJson(String.valueOf(items1), categoryItem.class);
                    List<categoryItem> itemList = new ArrayList<>();
                    itemList.add(_categoryItem);
                    if(status==1) {
                        categoryInterface.BigCategory(itemList, code);
                    }
                    else if(status==2){
                        categoryInterface.MediumCategory(itemList,code);
                    }
                    else if(status==3){
                        categoryInterface.SmallCategory(itemList,code);
                    }

                } catch (JSONException e) {
                    Log.d("HeroJongi", " this response " + response);
                }
            }
        }
    }
}
