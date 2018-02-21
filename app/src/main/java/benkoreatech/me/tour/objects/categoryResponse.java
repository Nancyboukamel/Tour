package benkoreatech.me.tour.objects;


public class categoryResponse {

    private categoryBody body;

    private categoryHeader header;

    public categoryBody getBody() {
        return body;
    }

    public void setBody(categoryBody body) {
        this.body = body;
    }

    public categoryHeader getHeader() {
        return header;
    }

    public void setHeader(categoryHeader header) {
        this.header = header;
    }

    @Override
    public String toString()
    {
        return "[body = "+body+", header = "+header+"]";
    }
}
