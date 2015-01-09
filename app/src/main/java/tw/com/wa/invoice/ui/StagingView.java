package tw.com.wa.invoice.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import tw.com.wa.invoice.R;

/**
 * Created by Andy on 2015/1/9.
 */
public class StagingView extends LinearLayout {


    private OnValueChangeListener onValueChangeListener = new OnValueChangeListenerAdapter();


    private class OnValueChangeListenerAdapter implements OnValueChangeListener {

        @Override
        public void onFail(Throwable e) {

        }

        @Override
        public void onLoad() {

        }

        @Override
        public void onFinish() {

        }
    }

    public void setOnValueChangeListener(OnValueChangeListener onValueChangeListener) {
        this.onValueChangeListener = onValueChangeListener;
    }

    public interface OnValueChangeListener {
        void onFail(Throwable e);

        void onLoad();

        void onFinish();
    }

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


    private void bindView(Context context) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.range_staging_layout, this, true);
    }
}
