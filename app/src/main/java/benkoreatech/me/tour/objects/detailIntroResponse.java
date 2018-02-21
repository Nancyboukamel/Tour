package benkoreatech.me.tour.objects;

import java.io.Serializable;


public class detailIntroResponse implements Serializable {
    private detailIntroBody body;

    private detailIntroHeader header;

    public detailIntroBody getBody() {
        return body;
    }

    public void setBody(detailIntroBody body) {
        this.body = body;
    }

    public detailIntroHeader getHeader() {
        return header;
    }

    public void setHeader(detailIntroHeader header) {
        this.header = header;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [body = "+body+", header = "+header+"]";
    }
}
