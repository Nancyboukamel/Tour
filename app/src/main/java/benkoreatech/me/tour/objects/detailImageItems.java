package benkoreatech.me.tour.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class detailImageItems implements Serializable {
    private List<detailImageItem> detailImageItemList=new ArrayList<>();

    public List<detailImageItem> getDetailImageItemList() {
        return detailImageItemList;
    }

    public void setDetailImageItemList(List<detailImageItem> detailImageItemList) {
        this.detailImageItemList = detailImageItemList;
    }

    @Override
    public String toString() {
        return "{" +
                "detailImageItemList=" + detailImageItemList +
                '}';
    }
}
