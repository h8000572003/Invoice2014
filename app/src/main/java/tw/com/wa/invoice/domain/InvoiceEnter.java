package tw.com.wa.invoice.domain;

import java.io.Serializable;

/**
 * Created by Andy on 2015/1/20.
 */
public class InvoiceEnter implements Serializable {
    private int id;
    private String number;
    private String inYm;
    private String status;

    private String award;


    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getInYm() {
        return inYm;
    }

    public void setInYm(String inYm) {
        this.inYm = inYm;
    }
}
