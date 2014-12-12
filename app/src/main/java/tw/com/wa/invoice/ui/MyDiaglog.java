package tw.com.wa.invoice.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import tw.com.wa.invoice.R;
import tw.com.wa.invoice.domain.Award;
import tw.com.wa.invoice.domain.Invoice;
import tw.com.wa.invoice.util.CommomUtil;
import tw.com.wa.invoice.util.GetDataCompent;
import tw.com.wa.invoice.util.GetDataCompentImpl;

/**
 * Created by Andy on 2014/12/12.
 */
public class MyDiaglog implements View.OnClickListener {


    static public interface DialogAction {
        public void close(Award award);
    }

    MyDiaglogDTO dto = new MyDiaglogDTO();


    private class MyDiaglogDTO {
        private String number = "";
        private TextView textView;
        private List<Invoice> invoices;
        private DialogAction dialogAction = null;
    }


    public MyDiaglog(TextView textView, DialogAction dialogAction, List<Invoice> invoiceList) {

        dto.textView = textView;

        dto.dialogAction = dialogAction;
        dto.invoices = invoiceList;
        dto.number = "";

    }

    public static AlertDialog create(Context context, List<Invoice> invoiceList, DialogAction dialogAction) {


        AlertDialog.Builder builder = new AlertDialog.Builder(context);


        View dialog = LayoutInflater.from(context).inflate(R.layout.dialog_layout, null);

        final TextView textView = (TextView) dialog.findViewById(R.id.numberText);

        MyDiaglog myDiaglog = new MyDiaglog(textView, dialogAction, invoiceList);

        dialog.findViewById(R.id.button).setOnClickListener(myDiaglog);
        dialog.findViewById(R.id.btn1).setOnClickListener(myDiaglog);
        dialog.findViewById(R.id.btn2).setOnClickListener(myDiaglog);
        dialog.findViewById(R.id.btn3).setOnClickListener(myDiaglog);
        dialog.findViewById(R.id.btn4).setOnClickListener(myDiaglog);
        dialog.findViewById(R.id.btn5).setOnClickListener(myDiaglog);
        dialog.findViewById(R.id.btn6).setOnClickListener(myDiaglog);
        dialog.findViewById(R.id.btn7).setOnClickListener(myDiaglog);
        dialog.findViewById(R.id.btn8).setOnClickListener(myDiaglog);
        dialog.findViewById(R.id.btn9).setOnClickListener(myDiaglog);

        dialog.findViewById(R.id.cleanBtn).setOnClickListener(myDiaglog);


        builder.setView(dialog);
        return builder.create();
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.cleanBtn) {
            dto.number = "";
            dto.textView.setText("");
        } else {

            dto.number += ((TextView) v).getText();
            dto.textView.setText(dto.number);
            if (dto.number.length() == 8) {
                CommomUtil commomUtil = new CommomUtil();


                dto.dialogAction.close(commomUtil.checkAward(dto.number, dto.invoices));
                dto.number = "";
            }
        }

    }

}
