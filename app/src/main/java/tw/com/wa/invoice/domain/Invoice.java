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

    /**
     *
     */
    private boolean specialize=false;


    public Invoice(String awards, String number, boolean specialize) {
        this.awards = awards;
        this.number = number;
        this.specialize = specialize;
    }

    public boolean isSpecialize() {
        return specialize;
    }

    public void setSpecialize(boolean specialize) {
        this.specialize = specialize;
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
