package tw.com.wa.invoice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CalendarContract;
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
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tw.com.wa.invoice.domain.Award;
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
    private ViewGroup containerView = null;
    private int orgHeight = 0;

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
        final View rootView = inflater.inflate(R.layout.rec_layout, container, false);


        this.containerView = (ViewGroup) rootView.findViewById(R.id.bottombar);
        {
            this.orgHeight = this.containerView.getHeight();

        }


        this.recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        {
            this.mLayoutManager = new LinearLayoutManager(getActivity());
            this.recyclerView.setLayoutManager(mLayoutManager);
            this.recyclerView.setItemAnimator(new DefaultItemAnimator());
            this.recyclerView.setOnScrollListener(
                    new RecyclerView.OnScrollListener() {

                        @Override
                        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                            super.onScrollStateChanged(recyclerView, newState);
                        }

                        @Override
                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                            if (isLookUp(dy)) {
                                if (containerView.getHeight() >= orgHeight) {

                                }
                            } else {

                            }

                            int lastVisibleItem = ((LinearLayoutManager) mLayoutManager).findFirstVisibleItemPosition();
                            if (lastVisibleItem == 0) {
                                laySwipe.setEnabled(true);
                            } else {
                                laySwipe.setEnabled(false);
                            }
                        }
                    }
            );

        }


        this.laySwipe = (SwipeRefreshLayout) rootView.findViewById(R.id.laySwipe);
        {
            this.laySwipe.setColorSchemeResources(
                    android.R.color.holo_red_light,
                    android.R.color.holo_blue_light,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light);
            this.laySwipe.setOnRefreshListener(onSwipeToRefresh);
        }
        //insert  Calendar
        rootView.findViewById(R.id.ccalendarBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent calendarIntent = new Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI);
                Calendar beginTime = Calendar.getInstance();
                beginTime.setTime(BeanUtil.infoV2.getDateOfBegin());
                Calendar endTime = Calendar.getInstance();
                endTime.setTime(BeanUtil.infoV2.getDateOfEnd());


                calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis());
                calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis());
                calendarIntent.putExtra(CalendarContract.Events.TITLE, "發票獎金");

                Map<Award, Integer> map = new HashMap<Award, Integer>();

                for (InvoiceKeyIn keyIn : keyIns) {
                    if (keyIn.getAward() == null) {
                        continue;

                    }
                    Integer no = map.get(keyIn.getAward());

                    if (no == null) {
                        map.put(keyIn.getAward(), new Integer(1));
                    } else {
                        map.put(keyIn.getAward(), ++no);
                    }


                }

                StringBuffer message = new StringBuffer();
                int totalMoney = 0;

                for (Map.Entry<Award, Integer> pMap : map.entrySet()) {
                    message.append(pMap.getKey().message);
                    message.append("-");
                    message.append(pMap.getValue() + "張\n");

                    totalMoney += pMap.getKey().dollar * pMap.getValue();
                }
                message.append("總金額-" + totalMoney + "元");


                calendarIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, message.toString());

                startActivity(calendarIntent);
            }
        });


        this.setInvoiceNumAdapter();

        return rootView;
    }

    private boolean isLookUp(int dy) {
        return dy > 0;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }


    private void setInvoiceNumAdapter() {


        this.adapter = new NumberAdapter(keyIns, getActivity());
        this.recyclerView.setAdapter(adapter);
        this.adapter.setOnItemClickListener(new NumberAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int location) {
                //   Toast.makeText(getActivity(), "location=" + location, Toast.LENGTH_SHORT).show();
            }
        });

        this.refreshNumAdapter();
        ;
    }

    private void refreshNumAdapter() {
        adapter.notifyDataSetChanged();

        ;
    }


}
