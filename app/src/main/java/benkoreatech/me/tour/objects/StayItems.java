package benkoreatech.me.tour.objects;

import java.io.Serializable;


public class StayItems implements Serializable {

    private StayItem[] item;

    public StayItem[] getItem ()
    {
        return item;
    }

    public void setItem (StayItem[] item)
    {
        this.item = item;
    }

    @Override
    public String toString()
    {
        return "[item = "+item+"]";
    }
}
