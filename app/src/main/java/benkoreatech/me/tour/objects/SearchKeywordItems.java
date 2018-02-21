package benkoreatech.me.tour.objects;

import java.io.Serializable;


public class SearchKeywordItems implements Serializable {
    private SearchKeywordItem[] item;

    public SearchKeywordItem[] getItem() {
        return item;
    }

    public void setItem(SearchKeywordItem[] item) {
        this.item = item;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [item = "+item+"]";
    }
}
