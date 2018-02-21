package benkoreatech.me.tour.objects;

import java.io.Serializable;


public class detailImage implements Serializable {
    private detailImageResponse response;

    public detailImageResponse getResponse() {
        return response;
    }

    public void setResponse(detailImageResponse response) {
        this.response = response;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [response = "+response+"]";
    }
}
