package benkoreatech.me.tour.objects;

import java.io.Serializable;

public class searchFestival implements Serializable{
    private FestivalResponse response;

    public FestivalResponse getResponse ()
    {
        return response;
    }

    public void setResponse (FestivalResponse response)
    {
        this.response = response;
    }

    public searchFestival(FestivalResponse response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "searchFestival{" +
                "response=" + response +
                '}';
    }
}
