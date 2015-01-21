package tw.com.wa.invoice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import tw.com.wa.invoice.fragment.ListInvoiceFragment;
import tw.com.wa.invoice.fragment.MessageFragment;

/**
 * Created by Andy on 15/1/17.
 */
public class ListInvoiceActivity extends ActionBarActivity {

    private static final int ADD_CODE = 1;


//    private MessageFragment messageFragment;
    private ListInvoiceFragment listInvoiceFragment;


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
            //TODO add item
            this.listInvoiceFragment.reFresh();

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.invoice_layout);

        if (savedInstanceState == null) {

        }
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
            case R.id.action_add:

                Intent it = new Intent(ListInvoiceActivity.this, AddInvoiceActivity.class);


                Bundle bundle = getIntent().getExtras();


                it.putExtras(bundle);
                startActivityForResult(it, ADD_CODE);
                //     overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);


                break;

        }

        return super.onOptionsItemSelected(item);
    }
}
