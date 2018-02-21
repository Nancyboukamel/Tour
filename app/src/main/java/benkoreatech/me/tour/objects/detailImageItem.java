package benkoreatech.me.tour.objects;

import java.io.Serializable;

public class detailImageItem implements Serializable {
    private String originimgurl;

    private String contentid;

    private String serialnum;

    private String smallimageurl;

    public String getOriginimgurl ()
    {
        return originimgurl;
    }

    public void setOriginimgurl (String originimgurl)
    {
        this.originimgurl = originimgurl;
    }

    public String getContentid ()
    {
        return contentid;
    }

    public void setContentid (String contentid)
    {
        this.contentid = contentid;
    }

    public String getSerialnum ()
    {
        return serialnum;
    }

    public void setSerialnum (String serialnum)
    {
        this.serialnum = serialnum;
    }

    public String getSmallimageurl ()
    {
        return smallimageurl;
    }

    public void setSmallimageurl (String smallimageurl)
    {
        this.smallimageurl = smallimageurl;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [originimgurl = "+originimgurl+", contentid = "+contentid+", serialnum = "+serialnum+", smallimageurl = "+smallimageurl+"]";
    }
}
