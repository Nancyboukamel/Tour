package benkoreatech.me.tour.objects;


import java.io.Serializable;

public class detailCommonItems implements Serializable{

    private detailCommonItem item;

    public detailCommonItem getItem() {
        return item;
    }

    public void setItem(detailCommonItem item) {
        this.item = item;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [item = "+item+"]";
    }
}
