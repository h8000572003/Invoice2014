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
import tw.com.wa.invoice.domain.BeanUtil;
import tw.com.wa.invoice.domain.Invoice;
import tw.com.wa.invoice.domain.OutInfo;
import tw.com.wa.invoice.domain.WiningBean;
import tw.com.wa.invoice.domain.WiningInfo;
import tw.com.wa.invoice.marker.ApiGetter;
import tw.com.wa.invoice.marker.WiningsAdapter;
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
    private TextView stageView = null;
    private Context context;

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
        stageView.setText(context.getString(R.string.main_topic_lab, outInfo.getStages().getAwardRangDate().toString()));
    }

    public void setOnValueChangeListener(OnValueChangeListener onValueChangeListener) {
        this.onValueChangeListener = onValueChangeListener;
    }

    private void bindView(Context context) {
        this.context = context;
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.range_staging_layout, this, true);

        this.findUI();
        this.setViewListener();


    }

    public void run(String invoiceYm) {
        if (this.job == null) {
            this.job = new LoadJob(invoiceYm);
            this.job.execute();
        }
    }

    @Override
    public void onClick(View v) {
        this.run(this.getInYm(v));
    }

    private String getInYm(View v) {
        switch (v.getId()) {

            case R.id.nextBtn:
                return InvoYm.after(invoYm).getStatge();

            case R.id.beforeBtn:
                return InvoYm.before(invoYm).getStatge();

            default:
                return invoYm.getStatge();

        }

    }

    private void findUI() {
        this.beforeBtn = (Button) this.findViewById(R.id.beforeBtn);
        this.nextBtn = (Button) this.findViewById(R.id.nextBtn);
        this.stagingText = (TextView) this.findViewById(R.id.stagingText);
        this.stageView = (TextView) this.findViewById(R.id.stageView);


    }

    private void setViewListener() {
        this.beforeBtn.setOnClickListener(this);
        this.nextBtn.setOnClickListener(this);
        this.stagingText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

                alertDialog.setTitle(context.getString(R.string.status_label));


                final List<Invoice> invoiceList = outInfo.getInvoice();

                final StringBuffer message = new StringBuffer();

                for (Invoice invoice : invoiceList) {


                    message.append(Award.lookup(invoice.getAwards()).message);
                    message.append(":");
                    message.append(invoice.getNumber());
                    message.append("\n");
                }


                alertDialog.setMessage(message.toString());
                alertDialog.setNeutralButton(context.getString(R.string.ok), null);

                alertDialog.show();
            }
        });
    }

    public WiningInfo getOutInfo() {
        return outInfo;
    }

    public void setOutInfo(WiningInfo outInfo) {
        this.outInfo = outInfo;
    }

    public TextView getStagingText() {
        return stagingText;
    }

    private void setGobalValue(WiningBean requstBean) {
        final OutInfo info = new OutInfo(requstBean);
        StagingView.this.outInfo = info;
        BeanUtil.info = info;
    }

    /**
     * 按鍵輸入監聽行為
     */
    public interface OnValueChangeListener {
        /**
         * @param e
         * @param messsage
         */
        void onFail(Throwable e, String messsage);

        void onLoad();

        void onFinish();

        void onSuccessfully(WiningInfo winingInfo);

    }

    private class OnValueChangeListenerAdapter implements OnValueChangeListener {

        /**
         * @param e
         * @param message
         */
        @Override
        public void onFail(Throwable e, String message) {

        }

        /**
         * 讀取中
         */
        @Override
        public void onLoad() {

        }

        /**
         * 執行完成
         */
        @Override
        public void onFinish() {

        }

        /**
         * 執行成功
         *
         * @param winingInfo
         */
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

            stageView.setText(R.string.loading);

            StagingView.this.onValueChangeListener.onLoad();
        }

        @Override
        protected Void doInBackground(Void... params) {


            try {

                WiningBean bean = getInvoiceInfoFromWeb(invoiceYm);


                if (TextUtils.equals(bean.getCode(), "901")) {
                    throw new InvoiceBusinessException(bean.getMsg());
                }


                setGobalValue(bean);

                StagingView.this.onValueChangeListener.onSuccessfully(BeanUtil.info);

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

        private WiningBean getInvoiceInfoFromWeb(String invoiceYm) {
            ApiGetter<WiningBean> marker = ApiGetter.getApi();
            marker.setAdapter(new WiningsAdapter(invoiceYm));
            return marker.getQuery();

        }


        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                stageView.setText(context.getString(R.string.main_topic_lab, outInfo.getStages().getAwardRangDate().toString()));
                stagingText.setText(outInfo.getTitle());
                stageView.setVisibility(View.VISIBLE);


            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            } finally {
                StagingView.this.job = null;
                StagingView.this.onValueChangeListener.onFinish();
            }
        }
    }
}
