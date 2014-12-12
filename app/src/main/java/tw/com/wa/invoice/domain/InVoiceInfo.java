package tw.com.wa.invoice.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andy on 2014/12/12.
 */
public class InVoiceInfo {
    private List<Invoice> invoice = new ArrayList<>();
    private String describe = "";

    public List<Invoice> getInvoice() {
        return invoice;
    }

    public void setInvoice(List<Invoice> invoice) {
        this.invoice = invoice;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
