package benkoreatech.me.tour.objects;

import java.io.Serializable;

public class detailIntro implements Serializable {
    private detailIntroResponse response;

    public detailIntroResponse getResponse() {
        return response;
    }

    public void setResponse(detailIntroResponse response) {
        this.response = response;
    }

    @Override
    public String toString()
    {
        return "[response = "+response+"]";
    }
}
