package benkoreatech.me.tour.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FestivalItems implements Serializable {
   List<FestivalItem> festivalItemList=new ArrayList<>();

    public FestivalItems(List<FestivalItem> festivalItemList) {
        this.festivalItemList = festivalItemList;
    }

    public List<FestivalItem> getFestivalItemList() {
        return festivalItemList;
    }

    public void setFestivalItemList(List<FestivalItem> festivalItemList) {
        this.festivalItemList = festivalItemList;
    }

    @Override
    public String toString() {
        return "FestivalItems{" +
                "festivalItemList=" + festivalItemList +
                '}';
    }
}
