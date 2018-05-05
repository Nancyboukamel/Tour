package benkoreatech.me.tour.objects;


import java.io.Serializable;

public class FestivalBody implements Serializable{
    private String totalCount;

    private FestivalItems items;

    private String pageNo;

    private String numOfRows;

    public String getTotalCount ()
    {
        return totalCount;
    }

    public void setTotalCount (String totalCount)
    {
        this.totalCount = totalCount;
    }

    public FestivalItems getItems ()
    {
        return items;
    }

    public void setItems (FestivalItems items)
    {
        this.items = items;
    }

    public String getPageNo ()
    {
        return pageNo;
    }

    public void setPageNo (String pageNo)
    {
        this.pageNo = pageNo;
    }

    public String getNumOfRows ()
    {
        return numOfRows;
    }

    public void setNumOfRows (String numOfRows)
    {
        this.numOfRows = numOfRows;
    }

    @Override
    public String toString() {
        return "FestivalBody{" +
                "totalCount='" + totalCount + '\'' +
                ", items=" + items +
                ", pageNo='" + pageNo + '\'' +
                ", numOfRows='" + numOfRows + '\'' +
                '}';
    }
}
