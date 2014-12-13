package tw.com.wa.invoice.domain;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.io.Serializable;

/**
 * Created by Andy on 14/12/9.
 */

@ParseClassName("Invoice")
public class Invoice extends ParseObject implements Serializable {

    //    /**
//     * 獎項等級
//     */
//    private String awards;
//
//    /**
//     * 發票號碼
//     */
//    private String number;
//
//    /**
//     *
//     */
//    private boolean specialize = false;
    // private String title;


//    public Invoice(String awards, String number, boolean specialize) {
//        this.awards = awards;
//        this.number = number;
//        this.specialize = specialize;
//    }

    public boolean isSpecialize() {

        return getBoolean("specialize");
    }

    public void setSpecialize(boolean specialize) {
        this.put("specialize", specialize);

    }

    public void setAwards(String awards) {
        this.put("awards", awards);

    }

    public String getNumber() {
        return getString("number");

    }

    public void setNumber(String number) {
        put("number", number);
    }

    public String getAwards() {
        return getString("awards");


    }

    public String getTitle() {
        return getString("title");
    }

    public void setTitle(String title) {
        this.put("title", title);

    }
}
