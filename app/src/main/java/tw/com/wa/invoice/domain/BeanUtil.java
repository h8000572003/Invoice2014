package tw.com.wa.invoice.domain;

import android.util.Log;

import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tw.com.wa.invoice.util.IWining;

/**
 * Created by Andy on 2014/12/12.
 */
public class BeanUtil {

    private static final String TAG = "BeanUtil";


    public static List<InvoiceKeyIn> allInvoices = new ArrayList<InvoiceKeyIn>();

    public static WiningInfo info = null;
    public static WiningBean WiningBean = null;

    //public static  OutInfo outInfo;

    public static List<Invoice> getInvoicesByLocal(String title) throws Exception {


        final ParseQuery<Invoice> query =
                ParseQuery.getQuery(Invoice.class);
        query.fromLocalDatastore();
        query.whereEqualTo("title", title);

        try {
            query.fromLocalDatastore();
            List<Invoice> invoices = query.find();
            return invoices;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            throw new RuntimeException("取得得獎發票錯誤，請確定網路正常再嘗試看看");
        }


    }

    public static List<Invoice> getInvoicesByLocal() throws Exception {


        final ParseQuery<Invoice> query =
                ParseQuery.getQuery(Invoice.class);


        try {
            query.fromLocalDatastore();
            List<Invoice> invoices = query.find();
            return invoices;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            throw new RuntimeException("取得得獎發票錯誤，請確定網路正常再嘗試看看");
        }


    }

    public static List<InvoiceInfoV2> getInvoiceInfByLocal() throws Exception {


        final ParseQuery<InvoiceInfoV2> inVoiceInfoParseQuery =
                ParseQuery.getQuery(InvoiceInfoV2.class);
        inVoiceInfoParseQuery.fromLocalDatastore();
        inVoiceInfoParseQuery.addAscendingOrder("createdAt");

        //  inVoiceInfoParseQuery.whereEqualTo("isCheck", true);

        try {
            List<InvoiceInfoV2> inVoiceInfons = inVoiceInfoParseQuery.find();
            return inVoiceInfons;


        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            throw new RuntimeException("取得得獎發票錯誤，請確定網路正常再嘗試看看");
        }


    }


}
