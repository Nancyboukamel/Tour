package benkoreatech.me.tour.objects;

import java.io.Serializable;

public class SearchKeyword implements Serializable {
    private SearchKeywordResponse response;

    public SearchKeywordResponse getResponse() {
        return response;
    }

    public void setResponse(SearchKeywordResponse response) {
        this.response = response;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [response = "+response+"]";
    }
}
