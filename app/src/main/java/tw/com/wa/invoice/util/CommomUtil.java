package tw.com.wa.invoice.util;


import android.text.TextUtils;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import tw.com.wa.invoice.domain.Award;
import tw.com.wa.invoice.domain.CheckStatus;
import tw.com.wa.invoice.domain.Invoice;
import tw.com.wa.invoice.domain.WiningInfo;

/**
 * Created by Andy on 14/12/9.
 */
public class CommomUtil {

    private static final String TAG = "CommomUtil";
    private static final SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
    private static final int LAST_CHAR = 8;

    public static void checkIsOverDateOfAward(WiningInfo info) throws RuntimeException {
        Date nowDate = new Date();
        if (nowDate.after(info.getStages().getAwardRangDate().getEnd().getTime())) {
            throw new RuntimeException("此期發票已經超過領獎時間");
        }
    }


    /*
    取得合理時間時間
     */
    public static Date getDateOfSanity(WiningInfo info) throws RuntimeException {
        checkIsOverDateOfAward(info);

        Date nowDate = new Date();

        InvoYm awrard = info.getStages().getAwardRangDate();

        if (nowDate.before(awrard.getBeging().getTime())) {//還不到領獎時間
            return awrard.getBeging().getTime();

        } else if (nowDate.before(awrard.getEnd().getTime())) {//再領獎日期截止之前
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(nowDate);
            calendar.add(Calendar.DAY_OF_MONTH, 1);//+一天
            calendar.set(Calendar.HOUR_OF_DAY, 9);//九點


            return calendar.getTime();

        } else {//還在領獎日期裡面
            return nowDate;
        }


    }


    /**
     * 取得前一期的對獎日
     *
     * @param ym
     * @return
     */
    public static String getPreYm(String ym) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, Integer.parseInt(ym.substring(0, 3)) + 1911);
        calendar.set(Calendar.MONTH, Integer.parseInt(ym.substring(3)) - 1);
        calendar
                .add(Calendar.MONTH, -2);


        int year = calendar.get(Calendar.YEAR) - 1911;
        int month = calendar.get(Calendar.MONTH) + 1;

        return String.format("%03d%02d", year, month);

    }

    /**
     * 取得最新一期對獎日，根據現在時間判斷
     *
     * @return
     */
    public static String getLastYm() {


        Calendar calendar = Calendar.getInstance();

        int nowMonth = calendar.get(Calendar.MONTH) + 1;
        if (isEvenNumber(nowMonth)) {//

            //二十五
            final int nowDay = calendar.get(Calendar.DAY_OF_MONTH);
            if (nowDay >= 25) {
                calendar.add(Calendar.MONTH, -1);
            } else {
                calendar.add(Calendar.MONTH, -3);
            }
            return getTwYearMon(calendar);

        } else {
            calendar.add(Calendar.MONTH, -2);

            return getTwYearMon(calendar);
        }
    }

    private static String getTwYearMon(Calendar calendar) {
        int year = convetzTwYear(calendar.get(Calendar.YEAR));
        int mon = calendar.get(Calendar.MONTH) + 1;
        return String.format("%03d%02d", year, mon);
    }

    private static int convetzTwYear(int yyyy) {
        return yyyy - 1911;
    }

    /**
     * 是否為偶數
     *
     * @param number
     * @return
     */
    private static boolean isEvenNumber(int number) {
        return number % 2 == 1;
    }

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

    public CheckStatus checkStatus(String number, List<Invoice> invoices) throws RuntimeException {

        CheckStatus status = CheckStatus.None;

        for (Invoice invoice : invoices) {
            if (invoice.isSpecialize()) {
                final String numOfInvoice = invoice.getNumber();
                if (numOfInvoice.length() > number.length()) {

                    final int cutIndex = numOfInvoice.length() - number.length();
                    final String cutInvoice = numOfInvoice.substring(cutIndex);
                    if (number.equals(cutInvoice)) {
                        return CheckStatus.Continue;
                    }

                } else {
                    final int cutIndex = number.length() - numOfInvoice.length();
                    final String cutNumber = number.substring(cutIndex);

                    if (TextUtils.equals(cutNumber, numOfInvoice)) {
                        return CheckStatus.Get;
                    }

                }

            }
        }

        for (Invoice invoice : invoices) {
            if (!invoice.isSpecialize()) {

                boolean isConutine = false;
                int checkLength = 0;

                int legnth = number.length();
                for (int i = 3; i <= legnth; i++) {
                    if (this.matchLastChar(invoice, number, i)) {
                        isConutine = true;
                        checkLength = i;
                    } else {
                        isConutine=false;
                        break;
                    }
                }
                if (isConutine) {
                    if (checkLength == 8) {
                        return CheckStatus.Get;
                    }
                    return CheckStatus.Continue;
                }else{
                    if(checkLength>=3){
                        return CheckStatus.Get;
                    }
                }

            }
        }
        return status;
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

    public CheckStatus checkAward3Number(String number, List<Invoice> invoices) throws RuntimeException {
        CheckStatus checkStatus = CheckStatus.None;
        for (Invoice invoice : invoices) {
            checkStatus = CheckStatus.None;

            String matchNumber = invoice.getNumber();
            if (invoice.isSpecialize()) {//
                if (invoice.getNumber().length() == 3) {//對獎號碼為三碼
                    if (matchNumber.equals(number) && number.length() == 3) {
                        return CheckStatus.Get;
                    }

                    if (matchNumber.startsWith(number)) {
                        return CheckStatus.Continue;
                    }
                } else {//
                    matchNumber = matchNumber.substring(5, 8);


                    if (matchNumber.equals(number) && number.length() == 3) {
                        return CheckStatus.Continue;
                    } else if (matchNumber.startsWith(number)) {
                        return CheckStatus.Continue;
                    }

                }


            } else {//原本的

                matchNumber = matchNumber.substring(5, 8);

                if (matchNumber.equals(number) && number.length() == 3) {
                    return CheckStatus.Continue;
                } else if (matchNumber.startsWith(number)) {
                    return CheckStatus.Continue;
                }

            }


        }
        return CheckStatus.None;
    }

    /**
     * @param number
     * @param invoices
     * @return
     * @throws RuntimeException
     */
    public List<Award> checkAwardLists(List<Award> awards, String number, List<Invoice> invoices) throws RuntimeException {


        for (Invoice invoice : invoices) {
            CheckStatus checkStatus = CheckStatus.None;

            Log.d(TAG, "number=" + invoice.getNumber());
            if (invoice.isSpecialize()) {//
                Log.d(TAG, "isSpecialize");

                String specialNumber = "";
                if (invoice.getNumber().length() > number.length()) {
                    specialNumber = number.substring(8 - invoice.getNumber().length());
                } else {
                    specialNumber = number;
                }


                Log.d(TAG, "specialNumber=" + specialNumber);

                if (invoice.getNumber().equals(specialNumber)) {
                    awards.add(this.from(invoice, invoice.getNumber().length()));

                }

            } else {//原本的
                Log.d(TAG, "isNotSpecialize");
                for (int i = 3; i <= number.length(); i++) {
                    if (this.matchLastChar(invoice, number, i)) {
                        Log.d(TAG, "match..." + i);
                        checkStatus = CheckStatus.Continue;
                        awards.add(this.from(invoice, i));
                    } else {

                    }
                }

            }

        }
        return awards;
    }


    public Award checkBestAward(String number, List<Invoice> invoices) throws RuntimeException {

        number = number.trim();
        final List<Award> awards = new ArrayList<Award>();

        checkAwardLists(awards, number, invoices);

        Collections.sort(awards, new Comparator<Award>() {

            @Override
            public int compare(Award lhs, Award rhs) {
                return lhs.dollar >= rhs.dollar ? -1 : 1;
            }
        });
        if (awards.isEmpty()) {
            return null;
        } else {
            return awards.get(0);
        }


    }

    private Award from(Invoice invoice, int numberOfMatch) {
        if (invoice.isSpecialize()) {
            return Award.lookup(invoice.getAwards());
        } else {

            for (Award a : Award.values()) {

                if (a.checKLegth == numberOfMatch) {
                    if (invoice.getAwards().substring(0, 1).startsWith(a.unCode.substring(0, 1))) {
                        return a;
                    }
                }
            }

        }
        return null;
    }
}
