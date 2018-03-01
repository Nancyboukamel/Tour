package benkoreatech.me.tour.objects;

import java.io.Serializable;

public class detailCommonItem implements Serializable {
    private String masterid;

    private String contentid;

    private String contenttypeid;

    private String overview;

    private String directions;

    private String tel;

    private String title;

    private String zipcode;

    private String homepage;

    public String getMasterid() {
        return masterid;
    }

    public void setMasterid(String masterid) {
        this.masterid = masterid;
    }

    public String getContentid() {
        return contentid;
    }

    public void setContentid(String contentid) {
        this.contentid = contentid;
    }

    public String getContenttypeid() {
        return contenttypeid;
    }

    public void setContenttypeid(String contenttypeid) {
        this.contenttypeid = contenttypeid;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    @Override
    public String toString() {
        return "detailCommonItem{" +
                "masterid='" + masterid + '\'' +
                ", contentid='" + contentid + '\'' +
                ", contenttypeid='" + contenttypeid + '\'' +
                ", overview='" + overview + '\'' +
                ", directions='" + directions + '\'' +
                ", tel='" + tel + '\'' +
                ", title='" + title + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", homepage='" + homepage + '\'' +
                '}';
    }
}
