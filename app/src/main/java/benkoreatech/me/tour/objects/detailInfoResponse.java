package benkoreatech.me.tour.objects;


public class detailInfoResponse {
    private detailInfoBody body;

    private detailInfoHeader header;

    public detailInfoBody getBody() {
        return body;
    }

    public void setBody(detailInfoBody body) {
        this.body = body;
    }

    public detailInfoHeader getHeader() {
        return header;
    }

    public void setHeader(detailInfoHeader header) {
        this.header = header;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [body = "+body+", header = "+header+"]";
    }
}
