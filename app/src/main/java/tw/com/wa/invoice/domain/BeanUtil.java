package tw.com.wa.invoice.domain;

/**
 * Created by Andy on 2014/12/12.
 */
public class BeanUtil {

    private static final String TAG = "BeanUtil";


    private static WiningInfo info = null;

    public static WiningInfo getInfo() {
        return info;
    }

    public static void setInfo(WiningInfo info) {
        BeanUtil.info = info;
    }


    //    public static List<Invoice> getInvoicesByLocal(String title) throws Exception {
//
//
//        final ParseQuery<Invoice> query =
//                ParseQuery.getQuery(Invoice.class);
//        query.fromLocalDatastore();
//        query.whereEqualTo("title", title);
//
//        try {
//            query.fromLocalDatastore();
//            List<Invoice> invoices = query.find();
//            return invoices;
//        } catch (Exception e) {
//            Log.e(TAG, e.getMessage());
//            throw new RuntimeException("取得得獎發票錯誤，請確定網路正常再嘗試看看");
//        }
//
//
//    }
//
//    public static List<Invoice> getInvoicesByLocal() throws Exception {
//
//
//        final ParseQuery<Invoice> query =
//                ParseQuery.getQuery(Invoice.class);
//
//
//        try {
//            query.fromLocalDatastore();
//            List<Invoice> invoices = query.find();
//            return invoices;
//        } catch (Exception e) {
//            Log.e(TAG, e.getMessage());
//            throw new RuntimeException("取得得獎發票錯誤，請確定網路正常再嘗試看看");
//        }
//
//
//    }
//
//    public static List<InvoiceInfoV2> getInvoiceInfByLocal() throws Exception {
//
//
//        final ParseQuery<InvoiceInfoV2> inVoiceInfoParseQuery =
//                ParseQuery.getQuery(InvoiceInfoV2.class);
//        inVoiceInfoParseQuery.fromLocalDatastore();
//        inVoiceInfoParseQuery.addAscendingOrder("createdAt");
//
//        //  inVoiceInfoParseQuery.whereEqualTo("isCheck", true);
//
//        try {
//            List<InvoiceInfoV2> inVoiceInfons = inVoiceInfoParseQuery.find();
//            return inVoiceInfons;
//
//
//        } catch (Exception e) {
//            Log.e(TAG, e.getMessage());
//            throw new RuntimeException("取得得獎發票錯誤，請確定網路正常再嘗試看看");
//        }
//
//
//    }


}
