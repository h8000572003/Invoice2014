package tw.com.wa.invoice.domain;

import java.io.Serializable;
import java.util.List;

import tw.com.wa.invoice.util.InvoYm;

/**
 * Created by Andy on 2015/1/9.
 */
public interface WiningInfo extends Serializable {

    public List<Invoice> getInvoice();

    public String getTitle();

    public InvoiceInfoV2 getInfoV2();

    public InvoYm getStages();

    public WiningBean getBean();


}
