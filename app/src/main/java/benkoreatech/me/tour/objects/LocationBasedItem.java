package benkoreatech.me.tour.objects;

import java.io.Serializable;

public class LocationBasedItem implements Serializable {
    private String contentid;

    private String tel;

    private String sigungucode;

    private String areacode;

    private String contenttypeid;

    private String masterid;

    private String title;

    private String cat1;

    private String addr1;

    private String cat3;

    private String cat2;

    private String createdtime;

    private String modifiedtime;

    private String readcount;

    private String mapx;

    private String dist;

    private String mapy;

    private String firstimage;

    private String firstimage1;

    private String mlevel;



    public String getContentid ()
    {
        return contentid;
    }

    public void setContentid (String contentid)
    {
        this.contentid = contentid;
    }

    public String getTel ()
    {
        return tel;
    }

    public void setTel (String tel)
    {
        this.tel = tel;
    }

    public String getSigungucode ()
    {
        return sigungucode;
    }

    public void setSigungucode (String sigungucode)
    {
        this.sigungucode = sigungucode;
    }

    public String getAreacode ()
    {
        return areacode;
    }

    public void setAreacode (String areacode)
    {
        this.areacode = areacode;
    }

    public String getContenttypeid ()
    {
        return contenttypeid;
    }

    public void setContenttypeid (String contenttypeid)
    {
        this.contenttypeid = contenttypeid;
    }

    public String getMasterid ()
    {
        return masterid;
    }

    public void setMasterid (String masterid)
    {
        this.masterid = masterid;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getCat1 ()
    {
        return cat1;
    }

    public void setCat1 (String cat1)
    {
        this.cat1 = cat1;
    }

    public String getAddr1 ()
    {
        return addr1;
    }

    public void setAddr1 (String addr1)
    {
        this.addr1 = addr1;
    }

    public String getCat3 ()
    {
        return cat3;
    }

    public void setCat3 (String cat3)
    {
        this.cat3 = cat3;
    }

    public String getCat2 ()
    {
        return cat2;
    }

    public void setCat2 (String cat2)
    {
        this.cat2 = cat2;
    }

    public String getCreatedtime ()
    {
        return createdtime;
    }

    public void setCreatedtime (String createdtime)
    {
        this.createdtime = createdtime;
    }

    public String getModifiedtime ()
    {
        return modifiedtime;
    }

    public void setModifiedtime (String modifiedtime)
    {
        this.modifiedtime = modifiedtime;
    }

    public String getReadcount ()
    {
        return readcount;
    }

    public void setReadcount (String readcount)
    {
        this.readcount = readcount;
    }

    public String getMapx ()
    {
        return mapx;
    }

    public void setMapx (String mapx)
    {
        this.mapx = mapx;
    }

    public String getDist ()
    {
        return dist;
    }

    public void setDist (String dist)
    {
        this.dist = dist;
    }

    public String getMapy ()
    {
        return mapy;
    }

    public String getFirstimage() {
        return firstimage;
    }

    public void setFirstimage(String firstimage) {
        this.firstimage = firstimage;
    }

    public String getFirstimage1() {
        return firstimage1;
    }

    public void setFirstimage1(String firstimage1) {
        this.firstimage1 = firstimage1;
    }

    public void setMapy (String mapy)
    {
        this.mapy = mapy;
    }

    public String getMlevel() {
        return mlevel;
    }

    public void setMlevel(String mlevel) {
        this.mlevel = mlevel;
    }

    @Override
    public String toString() {
        return "LocationBasedItem{" +
                "contentid='" + contentid + '\'' +
                ", tel='" + tel + '\'' +
                ", sigungucode='" + sigungucode + '\'' +
                ", areacode='" + areacode + '\'' +
                ", contenttypeid='" + contenttypeid + '\'' +
                ", masterid='" + masterid + '\'' +
                ", title='" + title + '\'' +
                ", cat1='" + cat1 + '\'' +
                ", addr1='" + addr1 + '\'' +
                ", cat3='" + cat3 + '\'' +
                ", cat2='" + cat2 + '\'' +
                ", createdtime='" + createdtime + '\'' +
                ", modifiedtime='" + modifiedtime + '\'' +
                ", readcount='" + readcount + '\'' +
                ", mapx='" + mapx + '\'' +
                ", dist='" + dist + '\'' +
                ", mapy='" + mapy + '\'' +
                ", firstimage='" + firstimage + '\'' +
                ", firstimage1='" + firstimage1 + '\'' +
                ", mlevel='" + mlevel + '\'' +
                '}';
    }
}
