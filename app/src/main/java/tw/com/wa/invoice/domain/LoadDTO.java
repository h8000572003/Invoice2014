package tw.com.wa.invoice.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Andy on 2015/1/7.
 */
public class LoadDTO implements Serializable {
    private WiningBean winingBean;


    public WiningBean getWiningBean() {
        return winingBean;
    }

    public void setWiningBean(WiningBean winingBean) {
        this.winingBean = winingBean;
    }
}
