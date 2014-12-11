package tw.com.wa.invoice.util;

import java.util.List;

import tw.com.wa.invoice.domain.Invoice;


/**
 * Created by Andy on 14/12/9.
 */
public interface GetDataCompent {
    public List<Invoice> getInvoice(String yyymm);

    /**
     * 判斷是否有中獎
     * @param yyymm
     * @param number
     * @return
     */
    public Invoice checkNumber(String yyymm, String number);
}
