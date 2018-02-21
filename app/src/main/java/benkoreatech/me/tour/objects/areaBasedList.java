package benkoreatech.me.tour.objects;

import java.io.Serializable;


public class areaBasedList implements Serializable {
    private areaBasedResponse response;

    public areaBasedResponse getResponse() {
        return response;
    }

    public void setResponse(areaBasedResponse response) {
        this.response = response;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [response = "+response+"]";
    }
}
