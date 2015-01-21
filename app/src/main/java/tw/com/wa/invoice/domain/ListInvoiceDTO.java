package tw.com.wa.invoice.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Andy on 15/1/20.
 */
public class ListInvoiceDTO implements Serializable {

    private List<InvoiceEnter> enters = null;
  //  private String inYm = "";

    public List<InvoiceEnter> getEnters() {
        return enters;
    }

    public void setEnters(List<InvoiceEnter> enters) {
        this.enters = enters;
    }

//    public String getInYm() {
//        return inYm;
//    }
//
//    public void setInYm(String inYm) {
//        this.inYm = inYm;
//    }
}
