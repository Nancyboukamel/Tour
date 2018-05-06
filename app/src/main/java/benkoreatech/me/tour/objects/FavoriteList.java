package benkoreatech.me.tour.objects;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FavoriteList implements Serializable{
    private List<Favorites> item;

    public List<Favorites> getItem ()
    {
        return item;
    }

    public void setItem (List<Favorites> item)
    {
        this.item = item;
    }

    @Override
    public String toString() {
        return "{" +
                "item=" + item +
                '}';
    }
}
