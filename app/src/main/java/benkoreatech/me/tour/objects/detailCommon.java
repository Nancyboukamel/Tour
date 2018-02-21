package benkoreatech.me.tour.objects;

import java.io.Serializable;

public class detailCommon implements Serializable {
    private detailCommonResponse response;

    public detailCommonResponse getResponse() {
        return response;
    }

    public void setResponse(detailCommonResponse response) {
        this.response = response;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [response = "+response+"]";
    }
}
