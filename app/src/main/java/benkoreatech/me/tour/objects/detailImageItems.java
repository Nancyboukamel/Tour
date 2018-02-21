package benkoreatech.me.tour.objects;

import java.io.Serializable;


public class detailImageItems implements Serializable {
    private detailIntroItem[] item;

    public detailIntroItem[] getItem ()
    {
        return item;
    }

    public void setItem (detailIntroItem[] item)
    {
        this.item = item;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [item = "+item+"]";
    }
}
