package benkoreatech.me.tour.objects;

import java.io.Serializable;


public class SearchKeywordResponse implements Serializable {
    private SearchKeywordBody body;

    private SearchKeywordHeader header;

    public SearchKeywordBody getBody() {
        return body;
    }

    public void setBody(SearchKeywordBody body) {
        this.body = body;
    }

    public SearchKeywordHeader getHeader() {
        return header;
    }

    public void setHeader(SearchKeywordHeader header) {
        this.header = header;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [body = "+body+", header = "+header+"]";
    }
}
