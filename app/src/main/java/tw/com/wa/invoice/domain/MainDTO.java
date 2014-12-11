package tw.com.wa.invoice.domain;

import java.util.List;

/**
 * Created by Andy on 14/12/9.
 */
public class MainDTO {
    private String number = "";
    private String yyymm;

    private List<Invoice> invoices = null;

    public String getYyymm() {
        return yyymm;
    }

    public void setYyymm(String yyymm) {
        this.yyymm = yyymm;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }
}
