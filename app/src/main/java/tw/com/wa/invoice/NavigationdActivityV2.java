package tw.com.wa.invoice;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import tw.com.wa.invoice.domain.BeanUtil;
import tw.com.wa.invoice.domain.OutInfo;
import tw.com.wa.invoice.fragment.AwardMessageFragment;
import tw.com.wa.invoice.fragment.ListInvoiceFragment;
import tw.com.wa.invoice.fragment.MainFragment;
import tw.com.wa.invoice.util.RisCommon;


public class NavigationdActivityV2 extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private static final int[] titile = new int[]{R.string.title_section1, R.string.title_section2, R.string.title_section3};

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Fragment fragment;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private int pos = 0;


    private RisCommon risCommon = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_v2);

        risCommon=RisCommon.getRisCommon();

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();



        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();

        pos = position;
        onSectionAttached(position);


        fragment = getFragment(position);

        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    private Fragment getFragment(int pos) {

        OutInfo outInfo = (OutInfo) BeanUtil.getInfo();
        switch (pos) {

            case 0:
                return MainFragment.newInstance();


            case 1:

                return ListInvoiceFragment.newInstance();

            default:
                return new AwardMessageFragment();
        }

    }

    public void onSectionAttached(int number) {

        mTitle = getString(titile[number]);

    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.


            if (pos == 1) {
                getMenuInflater().inflate(R.menu.menu_list_invoice, menu);
            }
            //  getMenuInflater().inflate(R.menu.main_activity_v2, menu);
            restoreActionBar();
            return true;
        }
//        if (pos == 2) {
//            getMenuInflater().inflate(R.menu.menu_list_invoice, menu);
//        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        switch (item.getItemId()) {


            case R.id.action_delete:

                if (fragment instanceof ListInvoiceFragment) {
                    ((ListInvoiceFragment) fragment).deletList();
                }

                break;

        }

        return super.onOptionsItemSelected(item);
    }


}
