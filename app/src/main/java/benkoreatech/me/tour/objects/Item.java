package benkoreatech.me.tour.objects;


import java.io.Serializable;

public class Item implements Serializable{
    private int code;

    private String name;

    public int getCode() { return this.code; }

    public void setCode(int code) { this.code = code; }


    public String getName() { return this.name; }

    public void setName(String name) { this.name = name; }

    private int rnum;

    public int getRnum() { return this.rnum; }

    public void setRnum(int rnum) { this.rnum = rnum; }

    @Override
    public String toString() {
        return "Item{" +
                "code=" + code +
                ", name='" + name + '\'' +
                ", rnum=" + rnum +
                '}';
    }
}


