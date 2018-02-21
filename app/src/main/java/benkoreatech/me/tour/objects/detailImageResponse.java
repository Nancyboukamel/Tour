package benkoreatech.me.tour.objects;

import java.io.Serializable;



public class detailImageResponse implements Serializable {
    private detailImageBody body;

    private detailImageHeader header;

    public detailImageBody getBody() {
        return body;
    }

    public void setBody(detailImageBody body) {
        this.body = body;
    }

    public detailImageHeader getHeader() {
        return header;
    }

    public void setHeader(detailImageHeader header) {
        this.header = header;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [body = "+body+", header = "+header+"]";
    }
}
