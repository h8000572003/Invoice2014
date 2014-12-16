package tw.com.wa.invoice;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.w3c.dom.Text;

import java.util.List;

import tw.com.wa.invoice.domain.BeanUtil;
import tw.com.wa.invoice.domain.Invoice;
import tw.com.wa.invoice.domain.InvoiceInfoV2;

/**
 * Created by Andy on 2014/12/12.
 */
public class LoadingActivity extends Activity {

    private static final String TAG = "LoadingActivity";

    private TextView statuLabel;

    private LoadAsyncTask task = null;

    private Button refresh = null;
    private ProgressBar progressBar = null;


    private class LoadAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {


            statuLabel.setText("取得兌獎資訊中");
            progressBar.setVisibility(View.VISIBLE);
            statuLabel.setTextColor(Color.WHITE);
            refresh.setVisibility(View.GONE);
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                BeanUtil.map.clear();


                for (InvoiceInfoV2 info : getInvoiceInf()) {

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
            progressBar.setVisibility(View.INVISIBLE);
            task = null;

            if (s != null) {
                statuLabel.setText("資料取得錯誤，下拉重新刷新");
                statuLabel.setTextColor(Color.RED);
                refresh.setVisibility(View.VISIBLE);
            } else {

                Intent it = new Intent(LoadingActivity.this, MainActivity.class);
                startActivity(it);
                finish();
                ;
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_layout);


        ParseObject.registerSubclass(Invoice.class);
        ParseObject.registerSubclass(InvoiceInfoV2.class);
        Parse.initialize(this, "hgne1bjc7IaI7ZmpBN7dobThoeVzGy6RirURDo44", "K9Qum9KClGT789nE2fkleYqXa294NVO9I12cHxQI");
        ParseInstallation.getCurrentInstallation().saveInBackground();

        this.statuLabel = (TextView) this.findViewById(R.id.statusLabel);
        this.refresh = (Button) this.findViewById(R.id.refresh);
        this.progressBar = (ProgressBar) this.findViewById(R.id.progressBar2);

        this.setRefreshLister();


        if (task == null) {
            task = new LoadAsyncTask();
            task.execute("");
        }


    }

    private void setRefreshLister() {
        this.refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (task == null) {
                    task = new LoadAsyncTask();
                    task.execute("");
                }
            }
        });
    }


    /**
     * @param title
     * @return
     * @throws Exception
     */
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
}
