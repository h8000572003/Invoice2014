package tw.com.wa.invoice.domain;

import java.io.Serializable;

/**
 * Created by Andy on 2014/12/18.
 */
public class InvoiceKeyIn  implements Serializable{
    private String keyNumber = "";
    private Award award = null;
    private String title = "";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Award getAward() {
        return award;
    }

    public void setAward(Award award) {
        this.award = award;
    }

    public InvoiceKeyIn(String keyNumber) {
        this.keyNumber = keyNumber;
    }

    public String getKeyNumber() {
        return keyNumber;
    }

    public void setKeyNumber(String keyNumber) {
        this.keyNumber = keyNumber;
    }
}
