package tw.com.wa.invoice.domain;

import java.io.Serializable;

/**
 * Created by Andy on 2015/1/7.
 */
public class LoadDTO implements Serializable {
    private String ym = "";


    public String getYm() {
        return ym;
    }

    public void setYm(String ym) {
        this.ym = ym;
    }
}
