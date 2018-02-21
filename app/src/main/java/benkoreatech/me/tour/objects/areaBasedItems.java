package benkoreatech.me.tour.objects;

import java.io.Serializable;
import java.util.List;

public class areaBasedItems implements Serializable {
    private List<areaBasedItem> item;

    public List<areaBasedItem> getItem() {
        return item;
    }

    public void setItem(List<areaBasedItem> item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "areaBasedItems{" +
                "item=" + item +
                '}';
    }
}
