package benkoreatech.me.tour.objects;

import java.io.Serializable;

public class detailCommonItem implements Serializable {
    private String masterid;

    private String contentid;

    private String contenttypeid;

    public String getMasterid ()
    {
        return masterid;
    }

    public void setMasterid (String masterid)
    {
        this.masterid = masterid;
    }

    public String getContentid ()
    {
        return contentid;
    }

    public void setContentid (String contentid)
    {
        this.contentid = contentid;
    }

    public String getContenttypeid ()
    {
        return contenttypeid;
    }

    public void setContenttypeid (String contenttypeid)
    {
        this.contenttypeid = contenttypeid;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [masterid = "+masterid+", contentid = "+contentid+", contenttypeid = "+contenttypeid+"]";
    }
}
