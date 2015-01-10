package tw.com.wa.invoice.ui;

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
import android.widget.Toast;

import tw.com.wa.invoice.R;
import tw.com.wa.invoice.domain.OutInfo;
import tw.com.wa.invoice.domain.WiningBean;
import tw.com.wa.invoice.marker.ApiGetter;
import tw.com.wa.invoice.marker.WiningsAdapter;
import tw.com.wa.invoice.util.DateUtil;
import tw.com.wa.invoice.util.InvoYm;
import tw.com.wa.invoice.util.InvoiceBusinessException;

/**
 * Created by Andy on 2015/1/9.
 */
public class StagingView extends LinearLayout implements View.OnClickListener {

    private Button beforeBtn = null;
    private Button nextBtn = null;
    private TextView stagingText = null;

    private Context context;

    private ApiGetter<WiningBean> marker = new ApiGetter();
    private LoadJob job;

    private OutInfo outInfo;
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
                invoceYm = InvoYm.after(invoYm).toString();
                break;

            case R.id.beforeBtn:
                invoceYm = InvoYm.before(invoYm).toString();
                break;

            default:
                invoceYm = invoYm.toString();
                break;
        }
        Toast.makeText(context, "invoiceYM=" + invoceYm, Toast.LENGTH_SHORT).show();
        run(invoceYm);

    }

    private void findUI() {
        this.beforeBtn = (Button) this.findViewById(R.id.beforeBtn);
        this.nextBtn = (Button) this.findViewById(R.id.nextBtn);
        this.stagingText = (TextView) this.findViewById(R.id.stagingText);

        this.beforeBtn.setOnClickListener(this);
        this.nextBtn.setOnClickListener(this);
    }


    public OutInfo getOutInfo() {
        return outInfo;
    }

    public void setOutInfo(OutInfo outInfo) {
        this.outInfo = outInfo;
    }

    public interface OnValueChangeListener {
        void onFail(Throwable e, String messsage);

        void onLoad();

        void onFinish();

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


    }

    private class LoadJob extends AsyncTask<Void, Void, Void> {

        private static final String TAG = "LoadJob";
        private String invoiceYm;


        private LoadJob(String invoiceYm) {
            this.invoiceYm = invoiceYm;
        }

        @Override
        protected Void doInBackground(Void... params) {
            StagingView.this.onValueChangeListener.onLoad();


            try {
                marker.setAdapter(new WiningsAdapter(invoiceYm));
                WiningBean bean = marker.getQuery();
                StagingView.this.invoYm =
                        DateUtil.getYm(invoiceYm);

                if (TextUtils.equals(bean.getCode(), "901")) {
                    throw new InvoiceBusinessException(bean.getMsg());
                }


                OutInfo info = new OutInfo(bean);
                StagingView.this.outInfo = info;



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
