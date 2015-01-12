package tw.com.wa.invoice.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andy on 14/12/9.
 */
public class MainDTO {


    private Winning winning = new Winning();
    private List<Invoice> invoices = null;

    private InvoiceInfoV2 invoiceInfoV2;

    private WiningInfo info;


    public Winning getWinning() {

        return winning;
    }

    public void setWinning(Winning winning) {
        this.winning = winning;
    }

    public InvoiceInfoV2 getInvoiceInfoV2() {
        return invoiceInfoV2;
    }

    public void setInvoiceInfoV2(InvoiceInfoV2 invoiceInfoV2) {
        this.invoiceInfoV2 = invoiceInfoV2;
    }


    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }


    public WiningInfo getInfo() {
        return info;
    }

    public void setInfo(WiningInfo info) {
        this.info = info;
    }
}
