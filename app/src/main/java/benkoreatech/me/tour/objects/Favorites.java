package benkoreatech.me.tour.objects;


import java.io.Serializable;

public class Favorites implements Serializable {
    private String mapY;

    private String id;

    private String mapX;

    private String title;

    private String name;

    private String contenttypeid;

    public String getMapY ()
    {
        return mapY;
    }

    public void setMapY (String mapY)
    {
        this.mapY = mapY;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getMapX ()
    {
        return mapX;
    }

    public void setMapX (String mapX)
    {
        this.mapX = mapX;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
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
    public String toString() {
        return "Favorites{" +
                "mapY='" + mapY + '\'' +
                ", id='" + id + '\'' +
                ", mapX='" + mapX + '\'' +
                ", title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", contenttypeid='" + contenttypeid + '\'' +
                '}';
    }
}

