package tw.com.wa.invoice.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andy on 14/12/9.
 */
public class MainDTO {
    private String number = "";


    private List<Invoice> invoices = null;

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
