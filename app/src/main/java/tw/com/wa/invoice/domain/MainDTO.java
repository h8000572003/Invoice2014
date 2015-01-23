package tw.com.wa.invoice.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Andy on 14/12/9.
 */
public class MainDTO {


    private Winning winning = new Winning();
    private List<Invoice> invoices = null;

    private InvoiceInfoV2 invoiceInfoV2;

    private WiningInfo info;

    private String nowStageTitle = "";
    private Map<String, List<InvoiceKeyIn>> keyIn = new HashMap<>();




    public String getNowStageTitle() {
        return nowStageTitle;
    }

    public void setNowStageTitle(String nowStageTitle) {
        this.nowStageTitle = nowStageTitle;
    }

    public Map<String, List<InvoiceKeyIn>> getKeyIn() {
        return keyIn;
    }

    public void setKeyIn(Map<String, List<InvoiceKeyIn>> keyIn) {
        this.keyIn = keyIn;
    }

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
