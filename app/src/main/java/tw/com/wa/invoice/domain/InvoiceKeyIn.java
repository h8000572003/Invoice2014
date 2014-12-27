package tw.com.wa.invoice.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 使用者輸入發票內容
 * Created by Andy on 2014/12/18.
 */
public class InvoiceKeyIn implements Serializable {

    final SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");

    private String keyNumber = "";
    private Award award = null;
    private String title = "";
    private Date generationDateStamp;


    public Date getGenerationDateStamp() {
        return generationDateStamp;
    }

    public String getKeyInTime() {
        return
                sdFormat.format(this.generationDateStamp);
    }

    public InvoiceKeyIn(String keyNumber) {
        this.keyNumber = keyNumber;
        this.generationDateStamp = new Date();
    }

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

    public String getKeyNumber() {
        return keyNumber;
    }

    public void setKeyNumber(String keyNumber) {
        this.keyNumber = keyNumber;
    }

    public boolean isAwardFlag() {
        return this.award != null ? true : false;

    }
}
