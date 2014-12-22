package tw.com.wa.invoice;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import tw.com.wa.invoice.domain.BeanUtil;
import tw.com.wa.invoice.domain.InvoiceKeyIn;
import tw.com.wa.invoice.util.NumberAdapter;

/**
 * Created by Andy on 14/12/18.
 */
public class PlaceholderFragment extends Fragment {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private List<InvoiceKeyIn> keyIns = null;
    private NumberAdapter adapter = null;
    private RecyclerView recyclerView = null;
    private SwipeRefreshLayout laySwipe;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout.OnRefreshListener onSwipeToRefresh = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            laySwipe.setRefreshing(true);
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    laySwipe.setRefreshing(false);

                    refreshNumAdapter();
                    Toast.makeText(getActivity(), "Refresh done!", Toast.LENGTH_SHORT).show();
                }
            }, 3000);
        }
    };


    public PlaceholderFragment() {
        keyIns = BeanUtil.allInvoices;
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber, List<InvoiceKeyIn> keyIns) {

        final PlaceholderFragment fragment = new PlaceholderFragment();
        {

            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            fragment.keyIns = keyIns;
        }


        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.rec_layout, container, false);


        this.recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);


        this.mLayoutManager = new LinearLayoutManager(getActivity());


        this.recyclerView.setLayoutManager(mLayoutManager);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());


        this.laySwipe = (SwipeRefreshLayout) rootView.findViewById(R.id.laySwipe);
        this.laySwipe.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light);

        this.recyclerView.setOnScrollListener(
                new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                        int lastVisibleItem = ((LinearLayoutManager) mLayoutManager).findFirstVisibleItemPosition();


                        if (lastVisibleItem == 0) {
                            laySwipe.setEnabled(true);
                        } else {
                            laySwipe.setEnabled(false);
                        }
                    }
                }
        );
        this.laySwipe.setOnRefreshListener(onSwipeToRefresh);
        setInvoiceNumAdapter();

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }


    private void setInvoiceNumAdapter() {


        this.adapter = new NumberAdapter(keyIns, getActivity());
        this.recyclerView.setAdapter(adapter);

        this.refreshNumAdapter();
        ;
    }

    private void refreshNumAdapter() {
        adapter.notifyDataSetChanged();

        ;
    }


}
