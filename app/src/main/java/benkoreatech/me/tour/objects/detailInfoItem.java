package benkoreatech.me.tour.objects;

import java.io.Serializable;


public class detailInfoItem implements Serializable {
    private String infotext;

    private String infoname;

    private String contentid;

    private String serialnum;

    private String contenttypeid;

    private String fldgubun;

    public String getInfotext ()
    {
        return infotext;
    }

    public void setInfotext (String infotext)
    {
        this.infotext = infotext;
    }

    public String getInfoname ()
    {
        return infoname;
    }

    public void setInfoname (String infoname)
    {
        this.infoname = infoname;
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

    public String getContenttypeid ()
    {
        return contenttypeid;
    }

    public void setContenttypeid (String contenttypeid)
    {
        this.contenttypeid = contenttypeid;
    }

    public String getFldgubun ()
    {
        return fldgubun;
    }

    public void setFldgubun (String fldgubun)
    {
        this.fldgubun = fldgubun;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [infotext = "+infotext+", infoname = "+infoname+", contentid = "+contentid+", serialnum = "+serialnum+", contenttypeid = "+contenttypeid+", fldgubun = "+fldgubun+"]";
    }
}
