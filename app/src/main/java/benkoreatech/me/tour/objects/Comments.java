package benkoreatech.me.tour.objects;


import java.io.Serializable;
import java.util.Date;

public class Comments implements Serializable {
    String id;
    String name,comment;
    String date;
    String rate;
    String commentTitle;
    String mapX,mapY;

    public Comments(String id, String name, String comment, String date, String rate, String commentTitle, String mapX, String mapY) {
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.date = date;
        this.rate = rate;
        this.commentTitle = commentTitle;
        this.mapX = mapX;
        this.mapY = mapY;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getMapX() {
        return mapX;
    }

    public void setMapX(String mapX) {
        this.mapX = mapX;
    }

    public String getMapY() {
        return mapY;
    }

    public void setMapY(String mapY) {
        this.mapY = mapY;
    }

    public String getCommentTitle() {
        return commentTitle;
    }

    public void setCommentTitle(String commentTitle) {
        this.commentTitle = commentTitle;
    }

    @Override
    public String toString() {
        return "Comments{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", comment='" + comment + '\'' +
                ", date='" + date + '\'' +
                ", rate='" + rate + '\'' +
                ", commentTitle='" + commentTitle + '\'' +
                ", mapX='" + mapX + '\'' +
                ", mapY='" + mapY + '\'' +
                '}';
    }
}
