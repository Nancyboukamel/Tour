package benkoreatech.me.tour.objects;

import java.io.Serializable;

public class FestivalItems implements Serializable {
    private FestivalItem[] item;

    public FestivalItem[] getItem ()
    {
        return item;
    }

    public void setItem (FestivalItem[] item)
    {
        this.item = item;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [item = "+item+"]";
    }
}
