package benkoreatech.me.tour.objects;

import java.io.Serializable;

public class Header implements Serializable{

    private String resultCode;

    public String getResultCode() { return this.resultCode; }

    public void setResultCode(String resultCode) { this.resultCode = resultCode; }

    private String resultMsg;

    public String getResultMsg() { return this.resultMsg; }

    public void setResultMsg(String resultMsg) { this.resultMsg = resultMsg; }

    @Override
    public String toString() {
        return "Header{" +
                "resultCode='" + resultCode + '\'' +
                ", resultMsg='" + resultMsg + '\'' +
                '}';
    }
}
