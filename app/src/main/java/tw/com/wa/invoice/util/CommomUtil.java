package tw.com.wa.invoice.util;


import tw.com.wa.invoice.domain.Award;
import tw.com.wa.invoice.domain.Invoice;

/**
 * Created by Andy on 14/12/9.
 */
public class CommomUtil {

    private GetDataCompent getDataCompent=null;

    public CommomUtil() {
        getDataCompent = new GetDataCompentImpl();
    }

    public Award checkAward(String number, String yyymmd) {
        final Invoice invoice = getDataCompent.checkNumber(yyymmd, number);



        if (invoice == null) {
            return Award.None;
        }
        return Award.VerySpecial;


    }

}
