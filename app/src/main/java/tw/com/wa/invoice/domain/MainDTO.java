package tw.com.wa.invoice.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andy on 14/12/9.
 */
public class MainDTO {
    private String number = "";


    private List<Invoice> invoices = null;
    private List<MainNumber>mainNumbers=new ArrayList<MainNumber>();


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

    public List<MainNumber> getMainNumbers() {
        return mainNumbers;
    }

    public void setMainNumbers(List<MainNumber> mainNumbers) {
        this.mainNumbers = mainNumbers;
    }
}
