package benkoreatech.me.tour.objects;

import java.io.Serializable;
import java.util.List;

public class LocationBasedItems implements Serializable {
    private List<LocationBasedItem> item;

    public LocationBasedItems(List<LocationBasedItem> item) {
        this.item = item;
    }

    public List<LocationBasedItem> getItem() {
        return item;
    }

    public void setItem(List<LocationBasedItem> item) {
        this.item = item;
    }


    @Override
    public String toString() {
        return "LocationBasedItems{" +
                "item=" + item +
                '}';
    }
}
