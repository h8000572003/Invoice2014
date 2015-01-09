package tw.com.wa.invoice.util;

import java.util.List;

import tw.com.wa.invoice.domain.Invoice;

/**
 * Created by Andy on 2015/1/9.
 */
public interface IWining {
    String getTitle();

    String stagingYm();

    List<Invoice> getInvoices();
}
