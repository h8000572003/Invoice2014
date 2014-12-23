package tw.com.wa.invoice;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import tw.com.wa.invoice.domain.BeanUtil;
import tw.com.wa.invoice.domain.Invoice;
import tw.com.wa.invoice.domain.InvoiceInfoV2;
import tw.com.wa.invoice.util.DbHelper;

/**
 * Created by Andy on 2014/12/12.
 */
public class LoadingActivity extends Activity {

    private static final String TAG = "LoadingActivity";

    private TextView statuLabel;

    private LoadAsyncTask task = null;


    private ProgressBar progressBar = null;
    private SwipeRefreshLayout laySwipe;


    private class LoadAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

            progressBar.setVisibility(View.VISIBLE);
            laySwipe.setRefreshing(true);


        }

        @Override
        protected String doInBackground(String... params) {


            try {
                List<InvoiceInfoV2> releaseInfos =
                        BeanUtil.getInvoiceInfByLocal();
                List<Invoice> releaseInvoice = BeanUtil.getInvoicesByLocal();


                for (InvoiceInfoV2 info : releaseInfos) {
                    info.unpin();
                }
                for (Invoice info : releaseInvoice) {
                    info.unpin();
                }


                for (InvoiceInfoV2 info : getInvoiceInf()) {


                    info.pinInBackground();

                    List<Invoice> invoices = getInvoices(info.getTitle());


                    for (Invoice invoice : invoices) {
                        invoice.pinInBackground();
                    }
                    info.getInvoice().addAll(invoices);


                }


            } catch (Exception e) {
                return "取得得獎發票錯誤，請確定網路正常再嘗試看看";
            } finally {

            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            statuLabel.clearAnimation();
            progressBar.setVisibility(View.GONE);
            laySwipe.setRefreshing(false);
            task = null;

            if (s != null) {
                statuLabel.setText("資料取得錯誤，下拉重新刷新");
                statuLabel.setTextColor(Color.RED);

            } else {


                AnimationSet animationset = new AnimationSet(true);
                animationset.addAnimation(AnimationUtils.loadAnimation(LoadingActivity.this, android.R.anim.slide_out_right));
                statuLabel.startAnimation(animationset);
                animationset.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        statuLabel.setVisibility(View.INVISIBLE);
                        Intent it = new Intent(LoadingActivity.this, MainActivity.class);
                        startActivity(it);
                        finish();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });


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

        Parse.enableLocalDatastore(this);//本地存資料
        Parse.initialize(this, "hgne1bjc7IaI7ZmpBN7dobThoeVzGy6RirURDo44", "K9Qum9KClGT789nE2fkleYqXa294NVO9I12cHxQI");
        ParseInstallation.getCurrentInstallation().saveInBackground();

        this.statuLabel = (TextView) this.findViewById(R.id.statusLabel);

        this.progressBar = (ProgressBar) this.findViewById(R.id.progressBar2);
        this.laySwipe = (SwipeRefreshLayout) this.findViewById(R.id.laySwipe);
        this.laySwipe.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light);
        this.laySwipe.setOnRefreshListener(onSwipeToRefresh);


        if (task == null) {
            task = new LoadAsyncTask();
            task.execute("");
        }


    }


    private SwipeRefreshLayout.OnRefreshListener onSwipeToRefresh = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            task = new LoadAsyncTask();
            task.execute("");
        }
    };


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
