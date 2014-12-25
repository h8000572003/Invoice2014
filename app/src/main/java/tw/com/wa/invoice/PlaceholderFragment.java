package tw.com.wa.invoice;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CalendarContract;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
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
    private Button ccalendarBtn = null;
    private TextView blankView = null;


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
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        fragment.keyIns = keyIns;


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
        this.recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        this.laySwipe = (SwipeRefreshLayout) rootView.findViewById(R.id.laySwipe);
        this.ccalendarBtn = (Button) rootView.findViewById(R.id.ccalendarBtn);
        this.blankView = (TextView) rootView.findViewById(R.id.blankView);

        this.mLayoutManager = new LinearLayoutManager(getActivity());
        this.recyclerView.setLayoutManager(mLayoutManager);


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


                Intent calendarIntent = new Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI);
                Calendar beginTime = Calendar.getInstance();
                beginTime.setTime(BeanUtil.infoV2.getDateOfBegin());
                Calendar endTime = Calendar.getInstance();
                endTime.setTime(BeanUtil.infoV2.getDateOfEnd());


                calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis());
                calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, beginTime.getTimeInMillis());
                calendarIntent.putExtra(CalendarContract.Events.TITLE, "發票獎金");

                //取得獎項與數量之關係
                final Map<Award, Integer> map = new HashMap<Award, Integer>();

                for (InvoiceKeyIn keyIn : keyIns) {
                    if (!keyIn.isAwardFlag()) {
                        continue;//沒有得獎的跳過
                    }
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
            public void onItemClick(View view, final int location) {

                AlertDialog.Builder diaglogOfTech = new AlertDialog.Builder(getActivity());
                diaglogOfTech.setTitle(R.string.teachTitle);
                diaglogOfTech.setMessage(R.string.cleanInvoice);
                diaglogOfTech.setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        BeanUtil.allInvoices.remove(location);
                        refreshNumAdapter();
                        checkIsBlankInvoices();
                        ;
                    }
                });
                diaglogOfTech.setPositiveButton(R.string.cancer, null);
                diaglogOfTech.show();

                ;
                //   Toast.makeText(getActivity(), "location=" + location, Toast.LENGTH_SHORT).show();
            }
        });


        this.refreshNumAdapter();
        ;
    }

    private void checkIsBlankInvoices() {
        if (BeanUtil.allInvoices.isEmpty()) {
            AnimationSet animationset = new AnimationSet(true);
            animationset.addAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_out_right));

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
