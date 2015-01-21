package tw.com.wa.invoice.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import tw.com.wa.invoice.R;
import tw.com.wa.invoice.domain.Award;
import tw.com.wa.invoice.domain.Invoice;
import tw.com.wa.invoice.domain.OutInfo;
import tw.com.wa.invoice.domain.WiningBean;
import tw.com.wa.invoice.domain.WiningInfo;
import tw.com.wa.invoice.marker.ApiGetter;
import tw.com.wa.invoice.marker.WiningsAdapter;
import tw.com.wa.invoice.util.DateUtil;
import tw.com.wa.invoice.util.InvoYm;
import tw.com.wa.invoice.util.InvoiceBusinessException;

/**
 * 發票期之畫面
 * Created by Andy on 2015/1/9.
 */
public class StagingView extends LinearLayout implements View.OnClickListener {

    private static final String TAG = "StagingView";
    private Button beforeBtn = null;
    private Button nextBtn = null;
    private TextView stagingText = null;

    private Context context;

    private ApiGetter<WiningBean> marker = new ApiGetter();
    private LoadJob job;

    private WiningInfo outInfo;
    private InvoYm invoYm;

    private OnValueChangeListener onValueChangeListener = new OnValueChangeListenerAdapter();


    public StagingView(Context context) {
        super(context);
        this.bindView(context);
    }

    public StagingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.bindView(context);
    }

    public StagingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.bindView(context);
    }

    public void init(WiningInfo info) {
        this.outInfo = info;
        this.invoYm = info.getStages();


        this.stagingText.setText(outInfo.getTitle());
    }

    public void setOnValueChangeListener(OnValueChangeListener onValueChangeListener) {
        this.onValueChangeListener = onValueChangeListener;
    }

    private void bindView(Context context) {
        this.context = context;
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.range_staging_layout, this, true);

        findUI();


    }

    public void run(String invoiceYm) {
        if (this.job == null) {
            this.job = new LoadJob(invoiceYm);
            this.job.execute();
        }
    }

    @Override
    public void onClick(View v) {
        String invoceYm = "";
        switch (v.getId()) {

            case R.id.nextBtn:
                invoceYm = InvoYm.after(invoYm).getStatge();
                break;

            case R.id.beforeBtn:
                invoceYm = InvoYm.before(invoYm).getStatge();
                break;

            default:
                invoceYm = invoYm.getStatge();
                break;
        }

        run(invoceYm);

    }

    private void findUI() {
        this.beforeBtn = (Button) this.findViewById(R.id.beforeBtn);
        this.nextBtn = (Button) this.findViewById(R.id.nextBtn);
        this.stagingText = (TextView) this.findViewById(R.id.stagingText);

        this.beforeBtn.setOnClickListener(this);
        this.nextBtn.setOnClickListener(this);
        this.stagingText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder MyAlertDialog = new AlertDialog.Builder(context);

                MyAlertDialog.setTitle(context.getString(R.string.status_label));


                List<Invoice> invoiceList = outInfo.getInvoice();

                StringBuffer message = new StringBuffer();
                message.append(context.getString(R.string.dateOfAward) + ":");
                message.append(outInfo.getStages().getAwardRangDate().toString() + "\n");
                for (Invoice invoice : invoiceList) {

                    Log.i(TAG, "invoice=" + invoice.getAwards() + "/" + invoice.getAwards());
                    Log.i(TAG, "number=" + invoice.getNumber());

                    message.append(Award.lookup(invoice.getAwards()).message);
                    message.append(":");
                    message.append(invoice.getNumber());
                    message.append("\n");
                }

                MyAlertDialog.setMessage(message.toString());
                MyAlertDialog.setNeutralButton(context.getString(R.string.ok), null);

                MyAlertDialog.show();
            }
        });
    }


    public WiningInfo getOutInfo() {
        return outInfo;
    }

    public void setOutInfo(WiningInfo outInfo) {
        this.outInfo = outInfo;
    }

    public interface OnValueChangeListener {
        void onFail(Throwable e, String messsage);

        void onLoad();

        void onFinish();

        void onSuccessfully(WiningInfo winingInfo);

    }

    private class OnValueChangeListenerAdapter implements OnValueChangeListener {

        @Override
        public void onFail(Throwable e, String message) {

        }

        @Override
        public void onLoad() {

        }

        @Override
        public void onFinish() {

        }

        @Override
        public void onSuccessfully(WiningInfo winingInfo) {

        }


    }

    private class LoadJob extends AsyncTask<Void, Void, Void> {

        private static final String TAG = "LoadJob";
        private String invoiceYm;


        private LoadJob(String invoiceYm) {
            this.invoiceYm = invoiceYm;
        }

        @Override
        protected void onPreExecute() {
            StagingView.this.onValueChangeListener.onLoad();
        }

        @Override
        protected Void doInBackground(Void... params) {


            try {
                marker.setAdapter(new WiningsAdapter(invoiceYm));
                WiningBean bean = marker.getQuery();


                if (TextUtils.equals(bean.getCode(), "901")) {
                    throw new InvoiceBusinessException(bean.getMsg());
                }

                StagingView.this.invoYm = DateUtil.getYm(invoiceYm);

                OutInfo info = new OutInfo(bean);
                StagingView.this.outInfo = info;


                StagingView.this.onValueChangeListener.onSuccessfully(info);

            } catch (InvoiceBusinessException e) {
                Log.e(TAG, "e:" + e.getMessage());
                StagingView.this.onValueChangeListener.onFail(e, e.getMessage());
                onCancelled();
            } catch (Exception e) {
                Log.e(TAG, "e:" + e.getMessage());
                StagingView.this.onValueChangeListener.onFail(e, "查詢失敗晚點在測試");
                onCancelled();
            }


            return null;
        }


        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                stagingText.setText(outInfo.getTitle());

            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            } finally {
                StagingView.this.job = null;
                StagingView.this.onValueChangeListener.onFinish();
            }
        }
    }
}
