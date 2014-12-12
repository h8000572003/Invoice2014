package tw.com.wa.invoice.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import tw.com.wa.invoice.R;
import tw.com.wa.invoice.domain.Invoice;
import tw.com.wa.invoice.util.CommomUtil;
import tw.com.wa.invoice.util.GetDataCompent;
import tw.com.wa.invoice.util.GetDataCompentImpl;

/**
 * Created by Andy on 2014/12/12.
 */
public class MyDiaglog implements View.OnClickListener {

    private String number = "";
    private TextView textView;
    private static List<Invoice> invoices;

    public MyDiaglog(TextView textView) {
        this.textView = textView;
    }

    public static AlertDialog create(Context context, List<Invoice> invoiceList) {

        invoices = invoiceList;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage("請輸入完整發票號");


        View dialog = LayoutInflater.from(context).inflate(R.layout.dialog_layout, null);

        final TextView textView = (TextView) dialog.findViewById(R.id.numberText);

        MyDiaglog myDiaglog = new MyDiaglog(textView);
        dialog.findViewById(R.id.button).setOnClickListener(myDiaglog);
        dialog.findViewById(R.id.btn1).setOnClickListener(myDiaglog);
        dialog.findViewById(R.id.btn2).setOnClickListener(myDiaglog);
        dialog.findViewById(R.id.btn3).setOnClickListener(myDiaglog);
        dialog.findViewById(R.id.btn4).setOnClickListener(myDiaglog);
        dialog.findViewById(R.id.btn5).setOnClickListener(myDiaglog);
        dialog.findViewById(R.id.btn6).setOnClickListener(myDiaglog);
        dialog.findViewById(R.id.btn7).setOnClickListener(myDiaglog);

        dialog.findViewById(R.id.cleanBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("");
            }
        });


        builder.setView(dialog);
        return builder.create();
    }

    @Override
    public void onClick(View v) {
        number += ((TextView) v).getText();
        textView.setText(number);
        if (number.length() == 8) {
            CommomUtil commomUtil = new CommomUtil();

            commomUtil.checkAward(number, invoices);

            number = "";
        }
    }
}
