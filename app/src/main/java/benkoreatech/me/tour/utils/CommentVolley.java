package benkoreatech.me.tour.utils;


import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import benkoreatech.me.tour.interfaces.placeInfoInterface;
import benkoreatech.me.tour.objects.CommentList;
import benkoreatech.me.tour.objects.Comments;
import benkoreatech.me.tour.objects.areaBasedList;

public class CommentVolley implements Response.Listener<JSONObject>,Response.ErrorListener {

    Context context;
    benkoreatech.me.tour.interfaces.placeInfoInterface placeInfoInterface;
    RequestQueue requestQueue;
    int status;

    public CommentVolley(Context context, placeInfoInterface placeInfoInterface) {
        this.context = context;
        this.placeInfoInterface=placeInfoInterface;
        requestQueue = Volley.newRequestQueue(context);
    }


    public void fetchData(final String URL,int status){
        this.status=status;
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, URL, null,this,this);
        requestQueue.add(request);

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("HeroJongi"," response error "+error.getMessage());
    }

    @Override
    public void onResponse(JSONObject response) {
        Log.d("HeroJongi", " response comments " + response);
        if (response != null) {
            if(status==1) {
                Gson gson = new Gson();
                CommentList commentList = gson.fromJson(String.valueOf(response), CommentList.class);
                List<Comments> comments = commentList.getItem();
                if (placeInfoInterface != null) {
                    placeInfoInterface.setListofComments(comments);
                }
            }
            else if(status==2){
              if(placeInfoInterface!=null){
                  placeInfoInterface.fetchPlaceComments();
              }
            }

        }
    }
}
