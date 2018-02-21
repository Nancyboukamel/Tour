package benkoreatech.me.tour.objects;


import java.io.Serializable;

public class areaBasedBody implements Serializable {
    private String totalCount;

    private areaBasedItems items;

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

    public areaBasedItems getItems ()
    {
        return items;
    }

    public void setItems (areaBasedItems items)
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
    public String toString()
    {
        return "[totalCount = "+totalCount+", items = "+items+", pageNo = "+pageNo+", numOfRows = "+numOfRows+"]";
    }
}
