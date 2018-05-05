package benkoreatech.me.tour.objects;

import java.io.Serializable;


public class FestivalResponse implements Serializable {

    private FestivalBody body;

    private FestivalHeader header;

    public FestivalBody getBody ()
    {
        return body;
    }

    public void setBody (FestivalBody body)
    {
        this.body = body;
    }

    public FestivalHeader getHeader ()
    {
        return header;
    }

    public void setHeader (FestivalHeader header)
    {
        this.header = header;
    }

    @Override
    public String toString() {
        return "FestivalResponse{" +
                "body=" + body +
                ", header=" + header +
                '}';
    }
}
