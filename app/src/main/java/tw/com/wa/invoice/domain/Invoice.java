package tw.com.wa.invoice.domain;

import java.io.Serializable;

/**
 * Created by Andy on 14/12/9.
 */
public class Invoice implements Serializable{

    /**
     * 獎項等級
     */
    private String awards;

    /**
     * 發票號碼
     */
    private String number;

    public Invoice(String awards, String number) {
        this.awards = awards;
        this.number = number;
    }

    public Invoice() {
        this("","");
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAwards() {
        return awards;
    }


}
