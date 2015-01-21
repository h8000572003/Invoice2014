package tw.com.wa.invoice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import tw.com.wa.invoice.fragment.ListInvoiceFragment;

/**
 * Created by Andy on 15/1/17.
 */
public class ListInvoiceActivity extends ActionBarActivity {

    private static final int ADD_CODE = 1;


    private ListInvoiceFragment listInvoiceFragment;

    private Toolbar topToolBar = null;
    private Toolbar bottomToolBar = null;

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


      //  Toolbar toolbar= getSupportActionBar();

//        this.topToolBar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
//        topToolBar.setTitle(R.string.app_name);
//        topToolBar.setSubtitle(getIntent().getExtras().getString("subTitle"));

      //  setSupportActionBar(topToolBar);

   //     this.bottomToolBar= (Toolbar) this.findViewById(R.id.my_bottom_tool_bar);


        // setSupportActionBar(toolbar);

        // getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

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
