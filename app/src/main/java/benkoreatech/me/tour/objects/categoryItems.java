package benkoreatech.me.tour.objects;

import java.io.Serializable;
import java.util.List;


public class categoryItems implements Serializable {

    private List<categoryItem> item;

    public List<categoryItem> getItem() {
        return item;
    }

    public void setItem(List<categoryItem> item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "categoryItems{" +
                "item=" + item +
                '}';
    }
}
