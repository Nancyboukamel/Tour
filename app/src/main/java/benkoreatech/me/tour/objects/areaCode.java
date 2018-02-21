package benkoreatech.me.tour.objects;


import java.io.Serializable;

public class areaCode implements Serializable{
    private Response response;

    public Response getResponse() { return this.response; }

    public void setResponse(Response response) { this.response = response; }

    @Override
    public String toString() {
        return "areaCode{" +
                "response=" + response +
                '}';
    }
}
