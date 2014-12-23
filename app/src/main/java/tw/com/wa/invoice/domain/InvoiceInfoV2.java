package tw.com.wa.invoice.domain;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Andy on 2014/12/12.
 */

@ParseClassName("InvoiceInfo")
public class InvoiceInfoV2 extends ParseObject {
    private List<Invoice> invoice = new ArrayList<>();


//    private String info = "";
//    private String title = "";
//    private boolean isCheck;

//    private Date dateOfBegin;
//    private Date dateOfEnd;

    public List<Invoice> getInvoice() {
        return invoice;
    }

    public void setInvoice(List<Invoice> invoice) {
        this.invoice = invoice;
    }

    public String getInfo() {
        return getString("info");

    }

    public void setInfo(String info) {
        put("info", info);

    }

    public String getTitle() {
        return getString("title");

    }

    public void setTitle(String title) {
        put("title", title);

    }

    public boolean isCheck() {
        return getBoolean("isCheck");
    }

    public void setCheck(boolean isCheck) {
        put("isCheck", isCheck);

    }

    public Date getDateOfBegin() {

        return getDate("dateOfBegin");
    }

    public void setDateOfBegin(Date dateOfBegin) {
        this.put("dateOfBegin", dateOfBegin);

    }

    public Date getDateOfEnd() {

        return getDate("dateOfEnd");
    }

    public void setDateOfEnd(Date dateOfEnd) {
        this.put("dateOfEnd", dateOfEnd);

    }
}
