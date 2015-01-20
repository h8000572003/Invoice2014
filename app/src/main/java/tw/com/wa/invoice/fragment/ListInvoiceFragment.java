package tw.com.wa.invoice.fragment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.List;

import tw.com.wa.invoice.R;
import tw.com.wa.invoice.domain.InvoiceEnter;
import tw.com.wa.invoice.util.DbHelper;
import tw.com.wa.invoice.util.InvoiceBusinessException;
import tw.com.wa.invoice.util.InvoiceEnterDAO;

/**
 * Created by Andy on 15/1/17.
 */
public class ListInvoiceFragment extends Fragment {

    private static final String TAG = "ListInvoiceFragment";


    private RecyclerView recyclerView = null;
    private SwipeRefreshLayout laySwipe;
    private RecyclerView.LayoutManager mLayoutManager;

    private String inYm = "";


    private QueryJob job = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getActivity().getIntent().getExtras();
        this.inYm = bundle.getString("inYm");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        final View rootView = inflater.inflate(R.layout.list_invoice_layout, container, false);

        this.recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        this.laySwipe = (SwipeRefreshLayout) rootView.findViewById(R.id.laySwipe);


        this.mLayoutManager = new LinearLayoutManager(getActivity());
        this.recyclerView.setLayoutManager(mLayoutManager);


        if (job == null) {
            job = new QueryJob();
            job.execute(inYm);
        }


        return rootView;
    }

    public void reFresh() {
        job = new QueryJob();
        job.execute(inYm);
    }

    private class QueryJob extends AsyncTask<String, List<InvoiceEnter>, List<InvoiceEnter>> {


        private InvoiceEnterDAO enterDAO = null;


        @Override
        protected List<InvoiceEnter> doInBackground(String... params) {

            DbHelper dbHelper = new DbHelper(getActivity());

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
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(List<InvoiceEnter> invoiceEnters) {
            //   super.onPostExecute(invoiceEnters);
            recyclerView.setAdapter(new InvAdapter(invoiceEnters, getActivity()));
        }
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
                    .inflate(R.layout.enter_layout, viewGroup, false);
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
