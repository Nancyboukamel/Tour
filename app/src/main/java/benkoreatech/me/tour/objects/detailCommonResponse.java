package benkoreatech.me.tour.objects;

import java.io.Serializable;

public class detailCommonResponse implements Serializable {
    private  detailCommonBody body;

    private detailCommonHeader header;

    public detailCommonBody getBody() {
        return body;
    }

    public void setBody(detailCommonBody body) {
        this.body = body;
    }

    public detailCommonHeader getHeader() {
        return header;
    }

    public void setHeader(detailCommonHeader header) {
        this.header = header;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [body = "+body+", header = "+header+"]";
    }
}
