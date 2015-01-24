package tw.com.wa.invoice;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import tw.com.wa.invoice.domain.BeanUtil;
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


    private String inYm = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_invoice_activity_layout);


        this.inYm = BeanUtil.info.getStages().getStatge();


        this.keyBoardLayout = (KeyBoardLayout) this.findViewById(R.id.keyboardLayout);
        this.editText = (EditText) this.findViewById(R.id.invoviceLabel);


        this.keyBoardLayout.setOnValueChangeListener(new KeyBoardLayout.OnValueChangeListener() {
            @Override
            public void onChange(String value) {
                editText.setText(value);
                if (value.length() >= 3) {


                    insert();

                }

            }
        });
        keyBoardLayout.unEnable();


    }

    private void insert() {
        this.insertInvoice();


        keyBoardLayout.cleanValueWithoutUI();

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.slide_out_right);


        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                keyBoardLayout.unEnable();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                keyBoardLayout.enable();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        editText.startAnimation(animation);


        setResult(Activity.RESULT_OK);


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

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
