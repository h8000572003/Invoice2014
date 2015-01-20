package tw.com.wa.invoice.ui;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;

import tw.com.wa.invoice.AboutActivity;
import tw.com.wa.invoice.ListInvoiceActivity;
import tw.com.wa.invoice.LoadingActivity;
import tw.com.wa.invoice.R;

/**
 * Created by Andy on 2015/1/13.
 */
public class ToolBar extends FrameLayout {

    private Context context;

    private Button btn1;
    private Button btn2;
    private Button btn3;


    private OnClickListener onClickListener1;
    private OnClickListener onClickListener2;
    private OnClickListener onClickListener3;

    public ToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.bindView(context);
    }

    public ToolBar(Context context) {
        super(context);
        this.bindView(context);
    }

    public ToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.bindView(context);
    }


    public void setBtn1OnClickListener(OnClickListener onClickListener) {
        this.onClickListener1 = onClickListener;
    }

    public void setBtn2OnClickListener(OnClickListener onClickListener) {
        this.onClickListener2 = onClickListener;
    }
    public void setBtn3OnClickListener(OnClickListener onClickListener) {
        this.onClickListener3 = onClickListener;
    }


    private void bindView(Context context) {
        this.context = context;
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.tool_bar_layout, this, true);

        this.btn1 = (Button) findViewById(R.id.button1);
        this.btn2 = (Button) findViewById(R.id.button2);
        this.btn3 = (Button) this.findViewById(R.id.addInvoiceTn);

        this.setonClickListener();
    }

    public ToolBar build() {
        this.setonClickListener();
        return this;
    }

    private void setonClickListener() {
        this.btn1.setOnClickListener(new OnClickListenerAdapter(onClickListener1));
        this.btn2.setOnClickListener(new OnClickListenerAdapter(onClickListener2));

        this.btn3.setOnClickListener(new OnClickListenerAdapter(onClickListener3));


    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility == VISIBLE) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.abc_slide_in_bottom);
            this.startAnimation(animation);


        } else {

            Animation animation = AnimationUtils.loadAnimation(context, R.anim.abc_slide_out_bottom);
            this.startAnimation(animation);
        }


    }

    private class OnClickListenerAdapter implements OnClickListener {

        private OnClickListener onClickListener = null;

        private OnClickListenerAdapter(OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
        }

        @Override
        public void onClick(View v) {
            this.onClickListener.onClick(v);
        }
    }
}
