package tw.com.wa.invoice;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

import tw.com.wa.invoice.teach.TeachingCameraFragment;
import tw.com.wa.invoice.teach.TeachingGetAwardFragment;
import tw.com.wa.invoice.teach.TeachingKeepCalendarFragment;
import tw.com.wa.invoice.teach.TeachingKey1Fragment;

/**
 * Created by Andy on 2014/12/30.
 */


/**
 * Created by Andy on 2014/12/30.
 */
public class TeachungActivity extends FragmentActivity {

    private static final int NUM_PAGES = 4;

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teaching_layout);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new TeachPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);


    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    private class TeachPagerAdapter extends FragmentStatePagerAdapter {


        public TeachPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new TeachingCameraFragment();
                case 1:
                    return new TeachingKey1Fragment();
//                case 2:
//                    return new TeachingKey2Fragment();
                case 2:
                    return new TeachingGetAwardFragment();
                case 3:
                    return new TeachingKeepCalendarFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }


}
