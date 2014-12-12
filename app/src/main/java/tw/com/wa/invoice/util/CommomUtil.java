package tw.com.wa.invoice.util;


import java.util.List;

import tw.com.wa.invoice.domain.Award;
import tw.com.wa.invoice.domain.CheckStatus;
import tw.com.wa.invoice.domain.Invoice;

/**
 * Created by Andy on 14/12/9.
 */
public class CommomUtil {

    static final int LAST_CHAR = 8;

    private GetDataCompent getDataCompent = null;

    private final static CheckStatus[] CHECK_STATUS___RANG =

            {
                    CheckStatus.VerySpecial,
                    CheckStatus.Second

            };

    public CommomUtil() {
        getDataCompent = new GetDataCompentImpl();
    }

    public CheckStatus checkAward(String number, String yyymmd) {
        final Invoice invoice = getDataCompent.checkNumber(yyymmd, number);


        if (invoice == null) {
            return CheckStatus.None;
        }
        return CheckStatus.VerySpecial;


    }


    public CheckStatus check3NumberAward(String number, List<Invoice> invoices) {


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
                    return CheckStatus.Wait;

                } else {
                    if (invoice.getAwards().equals("6")) {
                        return CheckStatus.Sixth;//
                    } else {
                        return CheckStatus.Finding;//
                    }


                }


        }

        return CheckStatus.None;

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


//    中獎號碼
//    特別獎	22267127
//    同期統一發票收執聯8位數號碼與上列號碼相同者獎金1,000 萬元
//    特獎	31075480
//    同期統一發票收執聯8位數號碼與上列號碼相同者獎金200 萬元
//    頭獎	35396804、15352117、54709991
//    同期統一發票收執聯8位數號碼與上列號碼相同者獎金20 萬元
//    二獎	同期統一發票收執聯末7 位數號碼與頭獎中獎號碼末7 位相同者各得獎金4 萬元
//    三獎	同期統一發票收執聯末6 位數號碼與頭獎中獎號碼末6 位相同者各得獎金1 萬元
//    四獎	同期統一發票收執聯末5 位數號碼與頭獎中獎號碼末5 位相同者各得獎金4 千元
//    五獎	同期統一發票收執聯末4 位數號碼與頭獎中獎號碼末4 位相同者各得獎金1 千元
//    六獎	同期統一發票收執聯末3 位數號碼與頭獎中獎號碼末3 位相同者各得獎金2 百元
//    增開六獎	114、068、476、970
//    同期統一發票收執聯末3 位數號碼與上列號碼相同者各得獎金2 百元


    public Award checkAward(String number, List<Invoice> invoices) throws RuntimeException {


        CheckStatus checkStatus = CheckStatus.None;
        for (Invoice invoice : invoices) {
            checkStatus = CheckStatus.None;


            int i = 0;
            if (invoice.isSpecialize()) {
                i = invoice.getNumber().length();
            } else {
                i = 3;
            }


            for (; i < invoice.getNumber().length(); i++) {
               if( this.matchLastChar(invoice,number,i)){

               }
            }


        }


    }

}
