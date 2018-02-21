package benkoreatech.me.tour.objects;

import java.io.Serializable;

public class Response implements Serializable{
    private Header header;

    public Header getHeader() { return this.header; }

    public void setHeader(Header header) { this.header = header; }

    private Body body;

    public Body getBody() { return this.body; }

    public void setBody(Body body) { this.body = body; }

    @Override
    public String toString() {
        return "Response{" +
                "header=" + header +
                ", body=" + body +
                '}';
    }
}
