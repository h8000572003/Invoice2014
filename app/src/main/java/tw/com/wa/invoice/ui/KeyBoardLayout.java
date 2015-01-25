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
public class KeyBoardLayout extends LinearLayout implements View.OnClickListener {


    private String value = "";
    private OnValueChangeListener onValueChangeListener;


    private TextView monitorView;


    public KeyBoardLayout(Context context) {
        super(context);
        this.bindView(context);
        this.setActionListener();
    }

    public KeyBoardLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.bindView(context);
        this.setActionListener();
    }

    public KeyBoardLayout(Context context, AttributeSet attrs, int defStyleAttr) {
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
        this.findViewById(R.id.delBtn).setOnClickListener(this);

    }

    public void setEnable(Boolean isEnable) {
        this.findViewById(R.id.button).setEnabled(isEnable);
        this.findViewById(R.id.btn1).setEnabled(isEnable);
        this.findViewById(R.id.btn2).setEnabled(isEnable);
        this.findViewById(R.id.btn3).setEnabled(isEnable);
        this.findViewById(R.id.btn4).setEnabled(isEnable);
        this.findViewById(R.id.btn5).setEnabled(isEnable);
        this.findViewById(R.id.btn6).setEnabled(isEnable);
        this.findViewById(R.id.btn7).setEnabled(isEnable);
        this.findViewById(R.id.btn8).setEnabled(isEnable);
        this.findViewById(R.id.btn9).setEnabled(isEnable);
        this.findViewById(R.id.cleanBtn).setEnabled(isEnable);
    }

    public void unEnable() {
        this.setEnabled(false);
    }

    public void enable() {
        this.setEnabled(true);
    }

    @Override
    public void onClick(View v) {


        TextView valueBtnView = (TextView) v;
        switch (v.getId()) {

            case R.id.cleanBtn:
                cleanValueWithoutUI();
                break;

            case R.id.delBtn:


                if (value.length() >= 1) {
                    this.value = this.value.substring(0, this.value.length() - 1);
                }
                break;
            default:

                this.value += valueBtnView.getText();
                break;

        }

        this.changeValueAction();

    }

    private void changeValueAction() {
        if (onValueChangeListener != null) {
            onValueChangeListener.onChange(value);
        }
        if (getMonitorView() != null) {
            getMonitorView().setText(value);
        }


    }

    /*
    清除資料
     */
    public void cleanValueWithUI() {
        this.value = "";
        this.changeValueAction();
    }

    public void cleanValueWithoutUI() {


        this.value = "";

    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        this.changeValueAction();

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

    public interface OnValueChangeListener {
        public void onChange(String value);
    }
}
