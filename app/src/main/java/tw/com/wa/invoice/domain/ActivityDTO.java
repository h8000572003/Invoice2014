package tw.com.wa.invoice.domain;

import java.io.Serializable;
import java.util.List;

import tw.com.wa.invoice.util.InvoYm;

/**
 * Created by Andy on 15/1/22.
 */
public class ActivityDTO implements IActivityDTO, Serializable {

    @Override
    public List<Invoice> getInvoice() {
        return null;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public InvoiceInfoV2 getInfoV2() {
        return null;
    }

    @Override
    public InvoYm getStages() {
        return null;
    }

    @Override
    public WiningBean getBean() {
        return null;
    }
}
