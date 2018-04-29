package benkoreatech.me.tour.utils;


import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class Utils {

    public static float[] getMarkerColors(){
        return  new float[]{BitmapDescriptorFactory.HUE_RED,BitmapDescriptorFactory.HUE_ORANGE,BitmapDescriptorFactory.HUE_YELLOW,BitmapDescriptorFactory.HUE_GREEN,
                BitmapDescriptorFactory.HUE_CYAN,BitmapDescriptorFactory.HUE_AZURE,BitmapDescriptorFactory.HUE_BLUE,BitmapDescriptorFactory.HUE_VIOLET,BitmapDescriptorFactory.HUE_MAGENTA,
                BitmapDescriptorFactory.HUE_ROSE};
    }
}
