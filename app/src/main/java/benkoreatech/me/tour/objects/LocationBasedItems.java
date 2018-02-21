package benkoreatech.me.tour.objects;

import java.io.Serializable;

public class LocationBasedItems implements Serializable {
    private LocationBasedItem[] item;

    public LocationBasedItem[] getItem() {
        return item;
    }

    public void setItem(LocationBasedItem[] item) {
        this.item = item;
    }

    @Override
    public String toString()
    {
        return "[item = "+item+"]";
    }
}
