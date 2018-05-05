package benkoreatech.me.tour.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FestivalItems implements Serializable {
    private List<FestivalItem> item;

    public List<FestivalItem> getItem ()
    {
        return item;
    }

    public void setItem (List<FestivalItem> item)
    {
        this.item = item;
    }

    @Override
    public String toString() {
        return "FestivalItems{" +
                "item=" + item +
                '}';
    }
}
