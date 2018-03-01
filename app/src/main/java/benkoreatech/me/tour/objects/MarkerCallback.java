package benkoreatech.me.tour.objects;

import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Callback;

public class MarkerCallback implements Callback
{
    Marker marker = null;

    public MarkerCallback(Marker marker)
    {
        this.marker = marker;
    }

    @Override
    public void onError() {}

    @Override
    public void onSuccess()
    {
        if (marker == null)
        {
            return;
        }

        if (!marker.isInfoWindowShown())
        {
            return;
        }

        // If Info Window is showing, then refresh it's contents:

        marker.hideInfoWindow(); // Calling only showInfoWindow() throws an error
        marker.showInfoWindow();
    }
}
