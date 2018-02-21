package benkoreatech.me.tour.objects;

import java.io.Serializable;


public class SearchStay implements Serializable {
    private StayResponse response;

    public StayResponse getResponse ()
    {
        return response;
    }

    public void setResponse (StayResponse response)
    {
        this.response = response;
    }

    @Override
    public String toString()
    {
        return "[response = "+response+"]";
    }
}
