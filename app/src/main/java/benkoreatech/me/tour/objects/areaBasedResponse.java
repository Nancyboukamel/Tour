package benkoreatech.me.tour.objects;

import java.io.Serializable;


public class areaBasedResponse implements Serializable {
    private areaBasedBody body;

    private areaBasedHeader header;

    public areaBasedBody getBody() {
        return body;
    }

    public void setBody(areaBasedBody body) {
        this.body = body;
    }

    public areaBasedHeader getHeader() {
        return header;
    }

    public void setHeader(areaBasedHeader header) {
        this.header = header;
    }

    @Override
    public String toString()
    {
        return "[body = "+body+", header = "+header+"]";
    }
}
