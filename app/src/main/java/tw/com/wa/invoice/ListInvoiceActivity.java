package tw.com.wa.invoice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import tw.com.wa.invoice.domain.BeanUtil;
import tw.com.wa.invoice.fragment.ListInvoiceFragment;
import tw.com.wa.invoice.util.RisCommon;

/**
 * Created by Andy on 15/1/17.
 */
public class ListInvoiceActivity extends ActionBarActivity {

    private static final int ADD_CODE = 1;


    private ListInvoiceFragment listInvoiceFragment;


    private RisCommon risCommon = null;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ADD_CODE:
                this.onAddActionResult(resultCode, data);


                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void onAddActionResult(int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {

            this.listInvoiceFragment.reFresh();

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.invoice_layout);

        this.risCommon = RisCommon.getRisCommon();


        getSupportActionBar().setSubtitle(this.risCommon.getTitle(BeanUtil.getInfo()));


        this.listInvoiceFragment = (ListInvoiceFragment) getSupportFragmentManager().findFragmentById(R.id.listInvoiceFragment);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_list_invoice, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {


        }

        return super.onOptionsItemSelected(item);
    }

}
