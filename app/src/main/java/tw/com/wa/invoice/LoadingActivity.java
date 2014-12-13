package tw.com.wa.invoice;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import tw.com.wa.invoice.domain.BeanUtil;
import tw.com.wa.invoice.domain.Invoice;
import tw.com.wa.invoice.domain.InvoiceInfoV2;

/**
 * Created by Andy on 2014/12/12.
 */
public class LoadingActivity extends Activity {

    private static final String TAG = "LoadingActivity";

    private List<Invoice> getInvoices(String title) throws Exception {


        final ParseQuery<Invoice> query =
                ParseQuery.getQuery(Invoice.class);
        query.whereEqualTo("title", title);

        try {
            List<Invoice> invoices = query.find();
            return invoices;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            throw new RuntimeException("取得得獎發票錯誤，請確定網路正常再嘗試看看");
        }


    }

    private List<InvoiceInfoV2> getInvoiceInf() throws Exception {


        final ParseQuery<InvoiceInfoV2> inVoiceInfoParseQuery =
                ParseQuery.getQuery(InvoiceInfoV2.class);
        //  inVoiceInfoParseQuery.whereEqualTo("isCheck", true);

        try {
            List<InvoiceInfoV2> inVoiceInfons = inVoiceInfoParseQuery.find();
            return inVoiceInfons;


        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            throw new RuntimeException("取得得獎發票錯誤，請確定網路正常再嘗試看看");
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_layout);

        ParseObject.registerSubclass(Invoice.class);
        ParseObject.registerSubclass(InvoiceInfoV2.class);
        Parse.initialize(this, "hgne1bjc7IaI7ZmpBN7dobThoeVzGy6RirURDo44", "K9Qum9KClGT789nE2fkleYqXa294NVO9I12cHxQI");


        new AsyncTask<String, String, String>() {


            @Override
            protected String doInBackground(String... params) {

                try {
                    BeanUtil.map.clear();

                    List<InvoiceInfoV2> infos = getInvoiceInf();
                    for (InvoiceInfoV2 info : infos) {

                        List<Invoice> invoices = getInvoices(info.getTitle());

                        info.getInvoice().addAll(invoices);

                        BeanUtil.map.put(info.getTitle(), info);

                    }

                } catch (Exception e) {
                    return "取得得獎發票錯誤，請確定網路正常再嘗試看看";
                }

                return null;
            }


            @Override
            protected void onPostExecute(String s) {

                if (s != null) {
                    Toast.makeText(LoadingActivity.this, s, Toast.LENGTH_SHORT).show();
                } else {

                    Intent it = new Intent(LoadingActivity.this, MainActivity.class);
                    startActivity(it);
                    finish();
                    ;
                }


            }
        }.execute("");


    }
}
