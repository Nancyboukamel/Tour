package benkoreatech.me.tour.objects;

import java.io.Serializable;


public class detailInfoItems implements Serializable {
    private detailInfoItem item;

    public detailInfoItem getItem() {
        return item;
    }

    public void setItem(detailInfoItem item) {
        this.item = item;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [item = "+item+"]";
    }
}
