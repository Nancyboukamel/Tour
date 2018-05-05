package benkoreatech.me.tour.objects;

import java.io.Serializable;


public class FestivalHeader implements Serializable {
    private String resultMsg;

    private String resultCode;

    public String getResultMsg ()
    {
        return resultMsg;
    }

    public void setResultMsg (String resultMsg)
    {
        this.resultMsg = resultMsg;
    }

    public String getResultCode ()
    {
        return resultCode;
    }

    public void setResultCode (String resultCode)
    {
        this.resultCode = resultCode;
    }

    @Override
    public String toString() {
        return "FestivalHeader{" +
                "resultMsg='" + resultMsg + '\'' +
                ", resultCode='" + resultCode + '\'' +
                '}';
    }
}
