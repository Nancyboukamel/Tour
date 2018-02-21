package benkoreatech.me.tour.objects;

import java.io.Serializable;


public class detailInfo implements Serializable {
    private detailInfoResponse response;

    public detailInfoResponse getResponse() {
        return response;
    }

    public void setResponse(detailInfoResponse response) {
        this.response = response;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [response = "+response+"]";
    }
}
