package benkoreatech.me.tour.objects;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by nancy on 2/1/2018.
 */

public class Items implements Serializable{
    private ArrayList<Item> item;

    public ArrayList<Item> getItem() { return this.item; }

    public void setItem(ArrayList<Item> item) { this.item = item; }

    @Override
    public String toString() {
        return "Items{" +
                "item=" + item +
                '}';
    }
}
