package benkoreatech.me.tour.objects;

import java.io.Serializable;
import java.util.List;


public class CommentList implements Serializable {
    private List<Comments> item;

    public void setItem(List<Comments> item){
        this.item = item;
    }
    public List<Comments> getItem(){
        return this.item;
    }

    @Override
    public String toString() {
        return "CommentList{" +
                "item=" + item +
                '}';
    }
}
