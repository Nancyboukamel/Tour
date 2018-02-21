package benkoreatech.me.tour.objects;


import java.io.Serializable;

public class Body implements Serializable{
    private Items items;

    public Items getItems() { return this.items; }

    public void setItems(Items items) { this.items = items; }

    private int numOfRows;

    public int getNumOfRows() { return this.numOfRows; }

    public void setNumOfRows(int numOfRows) { this.numOfRows = numOfRows; }

    private int pageNo;

    public int getPageNo() { return this.pageNo; }

    public void setPageNo(int pageNo) { this.pageNo = pageNo; }

    private int totalCount;

    public int getTotalCount() { return this.totalCount; }

    public void setTotalCount(int totalCount) { this.totalCount = totalCount; }

    @Override
    public String toString() {
        return "Body{" +
                "items=" + items +
                ", numOfRows=" + numOfRows +
                ", pageNo=" + pageNo +
                ", totalCount=" + totalCount +
                '}';
    }
}
