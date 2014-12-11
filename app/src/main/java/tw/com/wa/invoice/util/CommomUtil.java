package tw.com.wa.invoice.util;


import java.util.List;

import tw.com.wa.invoice.domain.Award;
import tw.com.wa.invoice.domain.Invoice;

/**
 * Created by Andy on 14/12/9.
 */
public class CommomUtil {

    private GetDataCompent getDataCompent = null;

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

    public Award check3NumberAward(String number, List<Invoice> invoices) {


        for (Invoice invoice : invoices) {


            String math = "";
            if (invoice.getAwards().equals("6")) {
                math = invoice.getNumber();
            } else {
                math = invoice.getNumber().substring(5, 8);
            }

            int lengthOfCutting = number.length();

            if (math.substring(0, lengthOfCutting).equals(number))
                if (lengthOfCutting < 3) {//未達三碼
                    return Award.Wait;

                } else {
                    if (invoice.getAwards().equals("6")) {
                        return Award.Sixth;//
                    } else {
                        return Award.Finding;//
                    }


                }


        }

        return Award.None;

    }

}
