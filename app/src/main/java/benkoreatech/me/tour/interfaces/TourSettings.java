package benkoreatech.me.tour.interfaces;


import java.util.LinkedHashMap;
import java.util.List;

import benkoreatech.me.tour.objects.Item;
import benkoreatech.me.tour.objects.StayItem;
import benkoreatech.me.tour.objects.categoryItem;

public interface TourSettings {
    public void FillCity(List<Item> itemList);
    public void FillSubCity(List<Item> items,Item item);
}
