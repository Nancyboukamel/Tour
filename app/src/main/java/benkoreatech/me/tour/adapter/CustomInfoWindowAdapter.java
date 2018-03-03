package benkoreatech.me.tour.adapter;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;

import benkoreatech.me.tour.PlaceInfo;
import benkoreatech.me.tour.R;
import benkoreatech.me.tour.objects.InfoWindowData;
import benkoreatech.me.tour.objects.LocationBasedItem;
import benkoreatech.me.tour.objects.MarkerCallback;
import de.hdodenhof.circleimageview.CircleImageView;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private Activity activity;
    Context context;
    TextView place_name;

    public CustomInfoWindowAdapter(Activity activity,Context context){
        this.activity = activity;
        this.context=context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = activity.getLayoutInflater().inflate(R.layout.windowinfo, null);
         place_name=(TextView) view.findViewById(R.id.place_name);
        InfoWindowData infoWindowData = (InfoWindowData) marker.getTag();
        if(infoWindowData!=null && infoWindowData.getLocationBasedData()!=null) {
           final String data = infoWindowData.getLocationBasedData();
            Gson gson = new Gson();
            if(data!=null && !data.equalsIgnoreCase("") && context!=null) {
                LocationBasedItem locationBasedItem = gson.fromJson(data, LocationBasedItem.class);
                place_name.setText(locationBasedItem.getTitle());


            }
        }
        return view;
    }


}
