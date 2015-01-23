package tw.com.wa.invoice;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CalendarContract;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tw.com.wa.invoice.domain.Award;
import tw.com.wa.invoice.domain.BeanUtil;
import tw.com.wa.invoice.domain.InvoiceKeyIn;
import tw.com.wa.invoice.domain.WiningInfo;
import tw.com.wa.invoice.util.CommomUtil;
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
    private Button ccalendarBtn = null;
    private TextView blankView = null;
    private TextView dateOfAwardView = null;

    private String awardYm = null;


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


    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public PlaceholderFragment() {


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent it =
                getActivity().getIntent();

        Bundle bundle =
                it.getExtras();

        keyIns = (List<InvoiceKeyIn>) bundle.getSerializable("list");
        awardYm = bundle.getString("Ym");


        awardYm = BeanUtil.info.getStages().getAwardRangDate().toString();


    }

    private Date checkTheInvoiceIsOutOfDate(WiningInfo winingInfo) throws RuntimeException {
        return CommomUtil.getDateOfSanity(winingInfo);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.rec_layout, container, false);


        this.containerView = (ViewGroup) rootView.findViewById(R.id.bottombar);
        this.recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        this.laySwipe = (SwipeRefreshLayout) rootView.findViewById(R.id.laySwipe);
        this.ccalendarBtn = (Button) rootView.findViewById(R.id.ccalendarBtn);
        this.blankView = (TextView) rootView.findViewById(R.id.blankView);
        this.dateOfAwardView = (TextView) rootView.findViewById(R.id.dateOfAwardView);

        this.mLayoutManager = new LinearLayoutManager(getActivity());
        this.recyclerView.setLayoutManager(mLayoutManager);

        this.dateOfAwardView.setText(awardYm);


        this.laySwipe.setOnRefreshListener(onSwipeToRefresh);
        this.laySwipe.setEnabled(false);
        this.laySwipe.setColorSchemeResources(//
                android.R.color.holo_red_light,//
                android.R.color.holo_blue_light,//
                android.R.color.holo_green_light,//
                android.R.color.holo_orange_light);//

        AnimationSet animationset = new AnimationSet(true);
        animationset.addAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_in_left));
        this.ccalendarBtn.startAnimation(animationset);
        //insert  Calendar
        this.ccalendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent calendarIntent = buildCalendarIntent();

                startActivity(calendarIntent);
            }
        });
        this.setInvoiceNumAdapter();


        try {
            CommomUtil.checkIsOverDateOfAward(BeanUtil.info);
        } catch (Exception e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            this.ccalendarBtn.setEnabled(false);
        }


        return rootView;
    }

    private Intent buildCalendarIntent() throws RuntimeException {
        Intent calendarIntent = new Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI);
        Calendar beginTime = Calendar.getInstance();
        beginTime.setTime(checkTheInvoiceIsOutOfDate(BeanUtil.info));
        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis());
        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, beginTime.getTimeInMillis());
        calendarIntent.putExtra(CalendarContract.Events.TITLE, "發票獎金");

        //取得獎項與數量之關係
        final Map<Award, Integer> map = new HashMap<Award, Integer>();

        for (InvoiceKeyIn keyIn : keyIns) {

            Integer no = map.get(keyIn.getAward());
            if (no == null) {
                map.put(keyIn.getAward(), new Integer(1));
            } else {
                map.put(keyIn.getAward(), ++no);
            }

        }

        final StringBuffer message = new StringBuffer();
        int totalMoney = 0;

        for (Map.Entry<Award, Integer> pMap : map.entrySet()) {
            message.append(pMap.getKey().message);
            message.append("-");
            message.append(pMap.getValue() + "張\n");


            totalMoney += pMap.getKey().dollar * pMap.getValue();
        }
        message.append("總金額-" + totalMoney + "元");


        calendarIntent.putExtra(CalendarContract.Events.DESCRIPTION, message.toString());

        return
                calendarIntent;
    }


    private void setInvoiceNumAdapter() {


        this.adapter = new NumberAdapter(keyIns, getActivity());
        this.recyclerView.setAdapter(adapter);


        this.refreshNumAdapter();
        ;
    }

    private void checkIsBlankInvoices() {
        if (keyIns.isEmpty()) {
            AnimationSet animationset = new AnimationSet(true);
            animationset.addAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_down));

            animationset.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    ccalendarBtn.setVisibility(View.GONE);
                    blankView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            this.ccalendarBtn.startAnimation(animationset);


        }

    }


    private void refreshNumAdapter() {
        adapter.notifyDataSetChanged();

        ;
    }


}
