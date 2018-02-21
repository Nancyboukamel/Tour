package benkoreatech.me.tour.interfaces;

import java.util.List;

import benkoreatech.me.tour.objects.areaBasedItem;
import benkoreatech.me.tour.objects.categoryItem;

public interface categoryInterface {
    public void BigCategory(List<categoryItem> categoryItems,int code);
    public void onItemBigSelected(categoryItem categoryItem,int code);
    public void MediumCategory(List<categoryItem> categoryItems,int code);
    public void onItemMediumSelected(categoryItem categoryItem,int code);
    public void SmallCategory(List<categoryItem> categoryItems,int code);
    public void CloseSlider();
    public void PlotPins(categoryItem BigItem,categoryItem MediumItem,categoryItem SmallItem,int code);
    public void setPins(List<areaBasedItem> areaBasedItems,int code);
}
