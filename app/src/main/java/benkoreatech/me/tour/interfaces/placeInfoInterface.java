package benkoreatech.me.tour.interfaces;


import java.util.List;

import benkoreatech.me.tour.objects.Comments;
import benkoreatech.me.tour.objects.detailCommonItem;
import benkoreatech.me.tour.objects.detailImageItem;
import benkoreatech.me.tour.objects.detailIntroItem;

public interface placeInfoInterface {
    public void details(detailCommonItem detailCommonItem);
    public void detailsIntro(detailIntroItem detailIntroItem);
    public void setImages(List<detailImageItem> detailImageItems);
    public void setImagesIfNoExist();
    public void addToFavorite();
    public void RemoveFromFavorite();
    public void setListofComments(List<Comments> comments);
    public void fetchPlaceComments();
}
