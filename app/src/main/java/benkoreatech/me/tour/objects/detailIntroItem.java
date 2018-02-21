package benkoreatech.me.tour.objects;

import java.io.Serializable;

public class detailIntroItem implements Serializable {
    private String usetimeculture;

    private String usefee;

    private String parkingculture;

    private String contentid;

    private String restdateculture;

    private String parkingfee;

    private String contenttypeid;

    private String infocenterculture;

    public String getUsetimeculture ()
    {
        return usetimeculture;
    }

    public void setUsetimeculture (String usetimeculture)
    {
        this.usetimeculture = usetimeculture;
    }

    public String getUsefee ()
    {
        return usefee;
    }

    public void setUsefee (String usefee)
    {
        this.usefee = usefee;
    }

    public String getParkingculture ()
    {
        return parkingculture;
    }

    public void setParkingculture (String parkingculture)
    {
        this.parkingculture = parkingculture;
    }

    public String getContentid ()
    {
        return contentid;
    }

    public void setContentid (String contentid)
    {
        this.contentid = contentid;
    }

    public String getRestdateculture ()
    {
        return restdateculture;
    }

    public void setRestdateculture (String restdateculture)
    {
        this.restdateculture = restdateculture;
    }

    public String getParkingfee ()
    {
        return parkingfee;
    }

    public void setParkingfee (String parkingfee)
    {
        this.parkingfee = parkingfee;
    }

    public String getContenttypeid ()
    {
        return contenttypeid;
    }

    public void setContenttypeid (String contenttypeid)
    {
        this.contenttypeid = contenttypeid;
    }

    public String getInfocenterculture ()
    {
        return infocenterculture;
    }

    public void setInfocenterculture (String infocenterculture)
    {
        this.infocenterculture = infocenterculture;
    }

    @Override
    public String toString()
    {
        return "[usetimeculture = "+usetimeculture+", usefee = "+usefee+", parkingculture = "+parkingculture+", contentid = "+contentid+", restdateculture = "+restdateculture+", parkingfee = "+parkingfee+", contenttypeid = "+contenttypeid+", infocenterculture = "+infocenterculture+"]";
    }
}
