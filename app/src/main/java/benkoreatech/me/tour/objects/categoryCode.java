package benkoreatech.me.tour.objects;


import java.io.Serializable;

public class categoryCode implements Serializable{
    private categoryResponse response;

    public categoryResponse getResponse ()
    {
        return response;
    }

    public void setResponse (categoryResponse response)
    {
        this.response = response;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [response = "+response+"]";
    }
}
