package benkoreatech.me.tour.objects;

import java.io.Serializable;


public class LocationBasedList implements Serializable {
    private LocationBasedResponse response;

    public LocationBasedResponse getResponse ()
    {
        return response;
    }

    public void setResponse (LocationBasedResponse response)
    {
        this.response = response;
    }

    @Override
    public String toString()
    {
        return "[response = "+response+"]";
    }
}
