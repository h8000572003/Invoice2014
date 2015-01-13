package tw.com.wa.invoice.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Andy on 2015/1/7.
 */
public class LoadDTO implements Serializable {
    private String ym="";
    private OutInfo outInfo;

    public OutInfo getOutInfo() {
        return outInfo;
    }

    public void setOutInfo(OutInfo outInfo) {
        this.outInfo = outInfo;
    }

    public String getYm() {
        return ym;
    }

    public void setYm(String ym) {
        this.ym = ym;
    }
}
