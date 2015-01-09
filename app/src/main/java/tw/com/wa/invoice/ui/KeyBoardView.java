package tw.com.wa.invoice.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import tw.com.wa.invoice.R;

/**
 * 鍵盤
 * Created by Andy on 2014/12/27.
 */
public class KeyBoardView extends LinearLayout implements View.OnClickListener {


    private String value = "";
    private OnValueChangeListener onValueChangeListener = new OnValueChangeListenerAdapter();
    private TextView monitorView;


    /**
     * 職變更觸發事件
     */
    public interface OnValueChangeListener {
        public void onChange(String value);
    }

    private class OnValueChangeListenerAdapter implements OnValueChangeListener {

        @Override
        public void onChange(String value) {

        }
    }


    public KeyBoardView(Context context) {
        super(context);
        this.bindView(context);
        this.setActionListener();
    }

    public KeyBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.bindView(context);
        this.setActionListener();
    }


    public KeyBoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.bindView(context);
        this.setActionListener();
    }

    private void bindView(Context context) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.keyboard, this, true);
    }

    public void setActionListener() {
        this.findViewById(R.id.button).setOnClickListener(this);
        this.findViewById(R.id.btn1).setOnClickListener(this);
        this.findViewById(R.id.btn2).setOnClickListener(this);
        this.findViewById(R.id.btn3).setOnClickListener(this);
        this.findViewById(R.id.btn4).setOnClickListener(this);
        this.findViewById(R.id.btn5).setOnClickListener(this);
        this.findViewById(R.id.btn6).setOnClickListener(this);
        this.findViewById(R.id.btn7).setOnClickListener(this);
        this.findViewById(R.id.btn8).setOnClickListener(this);
        this.findViewById(R.id.btn9).setOnClickListener(this);
        this.findViewById(R.id.cleanBtn).setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.cleanBtn:
                cleanValueWithoutUI();
                this.changeValueAction("");
                break;


            default:
                TextView valueBtnView = (TextView) v;
                this.value += valueBtnView.getText();
                this.changeValueAction(this.value);
                break;

        }


    }

    private void changeValueAction(String value) {
        this.onValueChangeListener.onChange(value);
        this.changeMonitorView();

    }

    private void changeMonitorView() {
        if (getMonitorView() != null) {
            getMonitorView().setText(this.value);
        }
    }

    /*
    清除資料
     */
    public void cleanValueWithUI() {
        this.value = "";
        this.changeValueAction(this.value);
    }

    public void cleanValueWithoutUI() {
        this.value = "";

    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        this.changeValueAction(value);

    }


    public void setOnValueChangeListener(OnValueChangeListener onValueChangeListener) {
        this.onValueChangeListener = onValueChangeListener;
    }


    public TextView getMonitorView() {
        return monitorView;
    }

    public void setMonitorView(TextView monitorView) {
        this.monitorView = monitorView;
    }
}
