package benkoreatech.me.tour.objects;

import java.io.Serializable;



public class detailInfoBody implements Serializable {
    private String totalCount;

    private detailInfoItems items;

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

    public detailInfoItems getItems() {
        return items;
    }

    public void setItems(detailInfoItems items) {
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
        return "ClassPojo [totalCount = "+totalCount+", items = "+items+", pageNo = "+pageNo+", numOfRows = "+numOfRows+"]";
    }
}
