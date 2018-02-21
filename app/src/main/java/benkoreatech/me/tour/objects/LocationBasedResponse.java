package benkoreatech.me.tour.objects;

import java.io.Serializable;

public class LocationBasedResponse implements Serializable {
    private LocationBasedBody body;

    private LocationBasedHeader header;

    public LocationBasedBody getBody() {
        return body;
    }

    public void setBody(LocationBasedBody body) {
        this.body = body;
    }

    public LocationBasedHeader getHeader() {
        return header;
    }

    public void setHeader(LocationBasedHeader header) {
        this.header = header;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [body = "+body+", header = "+header+"]";
    }
}
