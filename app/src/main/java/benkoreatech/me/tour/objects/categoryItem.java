package benkoreatech.me.tour.objects;


import java.io.Serializable;

public class categoryItem implements Serializable {
    private String rnum;

    private String name;

    private String code;

    public categoryItem(String rnum, String name, String code) {
        this.rnum = rnum;
        this.name = name;
        this.code = code;
    }

    public String getRnum ()
    {
        return rnum;
    }

    public void setRnum (String rnum)
    {
        this.rnum = rnum;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getCode ()
    {
        return code;
    }

    public void setCode (String code)
    {
        this.code = code;
    }

    @Override
    public String toString()
    {
        return " [rnum = "+rnum+", name = "+name+", code = "+code+"]";
    }
}
