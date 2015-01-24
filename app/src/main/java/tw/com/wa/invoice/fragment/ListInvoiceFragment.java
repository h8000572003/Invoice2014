package tw.com.wa.invoice.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import tw.com.wa.invoice.AddInvoiceActivity;
import tw.com.wa.invoice.R;
import tw.com.wa.invoice.domain.Award;
import tw.com.wa.invoice.domain.BeanUtil;
import tw.com.wa.invoice.domain.Invoice;
import tw.com.wa.invoice.domain.InvoiceEnter;
import tw.com.wa.invoice.domain.ListInvoiceDTO;
import tw.com.wa.invoice.ui.RecyAdapter;
import tw.com.wa.invoice.util.CommomUtil;
import tw.com.wa.invoice.util.DbHelper;
import tw.com.wa.invoice.util.InvoiceBusinessException;
import tw.com.wa.invoice.util.InvoiceEnterDAO;
import tw.com.wa.invoice.util.OnItemClickListner;

/**
 * Created by Andy on 15/1/17.
 */
public class ListInvoiceFragment extends Fragment {

    public static final int ADD_CODE = 1;
    private static final String TAG = "ListInvoiceFragment";
    private Spinner status_spinner = null;
    private RecyclerView recyclerView = null;
    private SwipeRefreshLayout laySwipe;
    private RecyclerView.LayoutManager mLayoutManager;
    private View blankView = null;
    private TextView bottombar = null;


    private ListInvoiceDTO dto = null;
    private View rootView = null;

    private String inYm = "";
    private SwipeRefreshLayout.OnRefreshListener onSwipeToRefresh = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {

            CheckJob job = new CheckJob();
            job.execute(inYm);
        }
    };
    private int status = 0;
    private QueryJob job = null;
    private boolean isCreateOn = true;
    private FloatingActionButton fab = null;


    public static ListInvoiceFragment newInstance() {

        final ListInvoiceFragment fragment = new ListInvoiceFragment();


        return fragment;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_CODE) {
            if (resultCode == Activity.RESULT_OK) {

                reFresh();


            }
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.dto = new ListInvoiceDTO();


        inYm = BeanUtil.info.getStages().getStatge();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        this.rootView = inflater.inflate(R.layout.list_invoice_layout, container, false);

        this.recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        this.laySwipe = (SwipeRefreshLayout) rootView.findViewById(R.id.laySwipe);
        this.blankView = rootView.findViewById(R.id.blankView);
        this.status_spinner = (Spinner) rootView.findViewById(R.id.status_spinner);
        this.bottombar = (TextView) rootView.findViewById(R.id.bottombar);
        this.fab = (FloatingActionButton) rootView.findViewById(R.id.fab);


        this.laySwipe.setOnRefreshListener(onSwipeToRefresh);
        this.laySwipe.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light);


        this.blankView.setVisibility(View.VISIBLE);
        this.mLayoutManager = new LinearLayoutManager(getActivity());
        this.recyclerView.setLayoutManager(mLayoutManager);
        this.recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                int firstItem = ((LinearLayoutManager) mLayoutManager).findFirstCompletelyVisibleItemPosition();
                laySwipe.setEnabled(firstItem == 0);

            }
        });
        String[] items = getResources().getStringArray(R.array.list_items);
        SpinnerAdapter spinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, items);
        this.status_spinner.setAdapter(spinnerAdapter);
        this.status_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                status = position;


                if (!isCreateOn) {
                    CheckJob job = new CheckJob();
                    job.execute(inYm);
                }
                isCreateOn = false;


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        this.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), AddInvoiceActivity.class);


                Bundle bundle = new Bundle();


                it.putExtras(bundle);
                startActivityForResult(it, ADD_CODE);
                getActivity().overridePendingTransition(R.anim.push_up_in, R.anim.abc_fade_out);
            }
        });


        if (job == null) {
            job = new QueryJob();
            job.execute(inYm);
        }


        return rootView;
    }

    public void deletList() {


        final DbHelper dbHelper = new DbHelper(getActivity());

        SQLiteDatabase con = null;
        try {
            con = dbHelper.getWritableDatabase();

            InvoiceEnterDAO enterDAO = new InvoiceEnterDAO(con, getActivity());
            enterDAO.delete(inYm);

        } catch (InvoiceBusinessException e) {
            Log.e(TAG, "e" + e.getMessage());
        } finally {
            if (con != null) {
                con.close();

            }

        }

        this.reFresh();

    }

    /**
     *
     */
    public void reFresh() {
        job = new QueryJob();
        job.execute(inYm);
    }

    private class CheckJob extends AsyncTask<String, List<InvoiceEnter>, List<InvoiceEnter>> {


        private CommomUtil commonUtil;

        @Override
        protected void onPreExecute() {

            bottombar.setVisibility(View.GONE);
            this.commonUtil = new CommomUtil();
        }

        @Override
        protected List<InvoiceEnter> doInBackground(String... params) {

            dto.setShowLists(new ArrayList<InvoiceEnter>());
            List<Invoice> v2 = BeanUtil.info.getInvoice();
            for (InvoiceEnter enter : dto.getEnters()) {
                Award award =
                        this.commonUtil.checkBestAward(enter.getNumber(), v2);
                enter.setStatus(award != null ? award.unCode : "");
            }

            if (status == 0) {
                dto.getShowLists().addAll(dto.getEnters());


            } else if (status == 1) {
                for (InvoiceEnter enter : dto.getEnters()) {


                    if (!TextUtils.isEmpty(enter.getStatus())) {
                        dto.getShowLists().add(enter);
                    }
                }

            } else if (status == 2) {
                for (InvoiceEnter enter : dto.getEnters()) {


                    if (TextUtils.isEmpty(enter.getStatus())) {
                        dto.getShowLists().add(enter);
                    }
                }

            }

            return dto.getEnters();
        }

        @Override
        protected void onPostExecute(List<InvoiceEnter> invoiceEnters) {
            dto.setEnters(invoiceEnters);
            laySwipe.setRefreshing(false);

            if (invoiceEnters == null || invoiceEnters.isEmpty()) {
                blankView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
            } else {
                status_spinner.setVisibility(View.VISIBLE);
                recyclerView
                        .setVisibility(View.VISIBLE);
                blankView.setVisibility(View.INVISIBLE);
            }

            RecyAdapter adapte = new RecyAdapter(dto.getShowLists());
            adapte.setOnItemClickListner(new OnItemClickListner() {
                @Override
                public void onItemClick(int pos) {

                    Toast.makeText(getActivity(), "1", Toast.LENGTH_SHORT).show();
                }
            });

            recyclerView.setAdapter(adapte);
        }


    }

    public class QueryJob extends AsyncTask<String, List<InvoiceEnter>, List<InvoiceEnter>> {

        private static final String TAG = "QueryJob";


        private InvoiceEnterDAO enterDAO = null;


        @Override
        protected void onPreExecute() {
            bottombar.setVisibility(View.VISIBLE);
            status_spinner.setVisibility(View.GONE);
        }

        @Override
        protected List<InvoiceEnter> doInBackground(String... params) {

            final DbHelper dbHelper = new DbHelper(getActivity());

            SQLiteDatabase con = null;
            try {
                con = dbHelper.getReadableDatabase();

                this.enterDAO = new InvoiceEnterDAO(con, getActivity());
                return enterDAO.get(params[0]);

            } catch (InvoiceBusinessException e) {
                Log.e(TAG, "e" + e.getMessage());
            } finally {
                if (con != null) {
                    con.close();

                }

            }
            return null;


        }

        @Override
        protected void onPostExecute(List<InvoiceEnter> invoiceEnters) {
            dto.setEnters(invoiceEnters);
            laySwipe.setRefreshing(false);

            if (invoiceEnters == null || invoiceEnters.isEmpty()) {
                blankView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
            } else {
                recyclerView
                        .setVisibility(View.VISIBLE);
                blankView.setVisibility(View.INVISIBLE);
            }

            recyclerView.setAdapter(new InvAdapter(invoiceEnters, getActivity()));
        }


        public class InvAdapter extends RecyclerView.Adapter<InvAdapter.ViewHolder> {

            private int lastPosition = -1;
            private List<InvoiceEnter> enters = null;
            private Context context;


            public InvAdapter(List<InvoiceEnter> enters, Context context) {
                this.enters = enters;
                this.context = context;
            }


            @Override
            public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

                View v = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.enter_layout
                                , viewGroup, false);
                // set the view's size, margins, paddings and layout parameters

                ViewHolder vh = new ViewHolder(v);
                return vh;
            }

            @Override
            public void onBindViewHolder(final ViewHolder viewHolder, final int position) {


                final InvoiceEnter mainNumber = enters.get(position);

                viewHolder.label.setText(mainNumber.getNumber());

                this.setAnimation(viewHolder.container, position);


            }


            @Override
            public int getItemCount() {
                return enters.size();
            }

            private void setAnimation(View viewToAnimate, int position) {
                // If the bound view wasn't previously displayed on screen, it's animated
                if (position > lastPosition) {
                    Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
                    viewToAnimate.startAnimation(animation);
                    lastPosition = position;
                }
            }


            public class ViewHolder extends RecyclerView.ViewHolder {
                // each data item is just a string in this case

                private TextView label;
                private View container;


                public ViewHolder(View v) {
                    super(v);
                    this.container = v.findViewById(R.id.container);
                    this.label = (TextView) v.findViewById(R.id.label);

                }
            }
        }

    }


}
