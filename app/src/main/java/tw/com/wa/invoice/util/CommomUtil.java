package tw.com.wa.invoice.util;


import java.util.List;

import tw.com.wa.invoice.domain.Award;
import tw.com.wa.invoice.domain.Invoice;

/**
 * Created by Andy on 14/12/9.
 */
public class CommomUtil {

    static final int LAST_CHAR = 8;

    private GetDataCompent getDataCompent = null;

    private final static int[] RANG =

            {
                    3, 4, 5, 6, 7, 8
            };

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

    /**
     * @param invoice
     * @param number
     * @param numberOfChar
     * @return
     */
    private boolean matchLastChar(Invoice invoice, String number, int numberOfChar) {


        String matchString = invoice.getNumber().substring(LAST_CHAR - 3, LAST_CHAR);
        return
                number.matches("\\d*" + matchString + "$");

    }


    public Award checkAward(String number, List<Invoice> invoices) throws RuntimeException {
        if (number.length() != 8) {
            throw new RuntimeException("number legth is not 8");
        }

        int leavelNo = -1;
        for (Invoice invoice : invoices) {
            leavelNo = -1;
            for (int leavel : RANG) {
                if (matchLastChar(invoice, number, leavel)) {

                    leavelNo = leavel;

                } else {
                    if (leavel != -1) {
                        break;
                    }
                }

            }


        }


    }

}
