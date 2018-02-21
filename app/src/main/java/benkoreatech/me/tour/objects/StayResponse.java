package benkoreatech.me.tour.objects;

import java.io.Serializable;

public class StayResponse implements Serializable{
    private StayBody body;

    private StayHeader header;

    public StayBody getBody ()
    {
        return body;
    }

    public void setBody (StayBody body)
    {
        this.body = body;
    }

    public StayHeader getHeader ()
    {
        return header;
    }

    public void setHeader (StayHeader header)
    {
        this.header = header;
    }

    @Override
    public String toString()
    {
        return "[body = "+body+", header = "+header+"]";
    }
}
