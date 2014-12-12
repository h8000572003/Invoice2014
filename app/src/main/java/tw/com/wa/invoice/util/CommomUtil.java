package tw.com.wa.invoice.util;


import android.util.Log;

import java.util.List;

import tw.com.wa.invoice.domain.Award;
import tw.com.wa.invoice.domain.CheckStatus;
import tw.com.wa.invoice.domain.Invoice;

/**
 * Created by Andy on 14/12/9.
 */
public class CommomUtil {

    static final String TAG = "CommomUtil";

    static final int LAST_CHAR = 8;

    private GetDataCompent getDataCompent = null;


    public CommomUtil() {
        getDataCompent = new GetDataCompentImpl();
    }

//    public CheckStatus checkAward(String number, String yyymmd) {
//        final Invoice invoice = getDataCompent.checkNumber(yyymmd, number);
//
//
//        if (invoice == null) {
//            return CheckStatus.None;
//        }
//        return CheckStatus.VerySpecial;
//
//
//    }


    /**
     * @param invoice
     * @param number
     * @param numberOfChar
     * @return
     */
    protected boolean matchLastChar(Invoice invoice, String number, int numberOfChar) {
        Log.i(TAG, String.format("number=%s,numberOfChar=%d", number, numberOfChar));


        //ex : 12345678


        String matchString = invoice.getNumber().substring(LAST_CHAR - numberOfChar, LAST_CHAR);


        return
                number.matches("\\d*" + matchString + "$");

    }

    protected boolean matchLast3Char(Invoice invoice, String number) {


        //ex : 12345678

        String matchString = number;


        return
                number.matches("\\d*" + matchString + "$");

    }

    public CheckStatus checkAward3Number(String number, List<Invoice> invoices) throws RuntimeException {
        CheckStatus checkStatus = CheckStatus.None;
        for (Invoice invoice : invoices) {
            checkStatus = CheckStatus.None;

            String matchNumber = invoice.getNumber();
            if (invoice.isSpecialize()) {//
                if (invoice.getNumber().length() == 3) {


                    if (matchNumber.equals(number) && number.length() == 3) {
                        return CheckStatus.Get;
                    }

                    if (matchNumber.startsWith(number)) {
                        return CheckStatus.Wait;
                    }
                } else {//
                    matchNumber = matchNumber.substring(5, 8);


                    if (matchNumber.equals(number) && number.length() == 3) {
                        return CheckStatus.Continue;
                    } else if (matchNumber.startsWith(number)) {
                        return CheckStatus.Wait;
                    }

                }


            } else {//原本的

                matchNumber = matchNumber.substring(5, 8);

                if (matchNumber.equals(number) && number.length() == 3) {
                    return CheckStatus.Continue;
                } else if (matchNumber.startsWith(number)) {
                    return CheckStatus.Wait;
                }

            }


        }
        return CheckStatus.None;
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


            if (invoice.isSpecialize()) {//

                if (invoice.getNumber().equals(number)) {
                    return this.from(invoice, invoice.getNumber().length());
                }


            } else {//原本的
                for (int i = 3; i < invoice.getNumber().length(); i++) {
                    if (this.matchLastChar(invoice, number, i)) {
                        checkStatus = CheckStatus.Continue;
                    } else {
                        if (checkStatus == CheckStatus.Continue) {
                            return this.from(invoice, i-1);
                        }
                        break;

                    }


                }
            }


        }

        return null;


    }

    private Award from(Invoice invoice, int numberOfMatch) {
        if (invoice.isSpecialize()) {
            return Award.lookup(invoice.getAwards());
        } else {

            for (Award a : Award.values()) {

                if (a.checKLegth == numberOfMatch) {
                    return a;
                }


            }

        }
        return null;
    }

}
