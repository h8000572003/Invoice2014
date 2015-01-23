package tw.com.wa.invoice;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import tw.com.wa.invoice.domain.InvoiceEnter;
import tw.com.wa.invoice.ui.KeyBoardLayout;
import tw.com.wa.invoice.util.DbHelper;
import tw.com.wa.invoice.util.InvoiceBusinessException;
import tw.com.wa.invoice.util.InvoiceEnterDAO;

/**
 * Created by Andy on 15/1/17.
 */
public class AddInvoiceActivity extends ActionBarActivity {

    private static final String TAG = "AddInvoiceActivity";
    private KeyBoardLayout keyBoardLayout;
    private EditText editText;
    private Button addInvoiceBtn;

    private String inYm = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_invoice_activity_layout);

        Bundle bundle = getIntent().getExtras();
        this.inYm = bundle.getString("inYm");


        this.keyBoardLayout = (KeyBoardLayout) this.findViewById(R.id.keyboardLayout);
        this.editText = (EditText) this.findViewById(R.id.invoviceLabel);
        this.addInvoiceBtn = (Button) this.findViewById(R.id.addInvoiceBtn);



        this.addInvoiceBtn.setEnabled(false);
        this.keyBoardLayout.setOnValueChangeListener(new KeyBoardLayout.OnValueChangeListener() {
            @Override
            public void onChange(String value) {
                editText.setText(value);
                if (value.length() >= 3 && value.length() <= 8) {
                    addInvoiceBtn.setEnabled(true);
                } else {
                    addInvoiceBtn.setEnabled(false);
                }

            }
        });


        this.addInvoiceBtn.setOnClickListener(new AddInvoiceListener());
    }

    private class AddInvoiceListener implements View.OnClickListener {


        @Override
        public void onClick(View v) {
            final String number = editText.getText().toString();


            try {
                this.check(number);

                this.insertInvoice();

                editText.setText("");
                keyBoardLayout.cleanValueWithoutUI();


                setResult(Activity.RESULT_OK);
                Toast.makeText(AddInvoiceActivity.this, "新增發票成功", Toast.LENGTH_LONG).show();

            } catch (InvoiceBusinessException e) {
                Toast.makeText(AddInvoiceActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }

        }

        private void check(String number) throws InvoiceBusinessException {
            if (TextUtils.isEmpty(number)) {
                throw new InvoiceBusinessException("發票號碼需大於3碼");
            } else if (number.length() < 3 && number.length() > 9) {
                throw new InvoiceBusinessException("發票號碼需大於3碼");
            }
        }

        private void insertInvoice() {
            DbHelper helper = new DbHelper(AddInvoiceActivity.this);

            SQLiteDatabase con
                    = null;
            try {

                con = helper.getWritableDatabase();
                con.beginTransaction();

                InvoiceEnterDAO dao = new InvoiceEnterDAO(con, AddInvoiceActivity.this);

                InvoiceEnter enterDomain = new InvoiceEnter();
                enterDomain.setInYm(inYm);
                enterDomain.setNumber(editText.getText().toString());
                enterDomain.setStatus("");

                dao.insert(enterDomain);
                con.setTransactionSuccessful();
            } catch (Exception e) {
                Log.e(TAG, "E:" + e.getMessage());
                throw new InvoiceBusinessException("新增發票失敗，請稍候再測試");

            } finally {
                con.endTransaction();
                con.close();
            }
        }

    }

}
