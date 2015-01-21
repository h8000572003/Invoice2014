//package tw.com.wa.invoice.job;
//
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//import android.os.AsyncTask;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.widget.TextView;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import tw.com.wa.invoice.R;
//import tw.com.wa.invoice.domain.InvoiceEnter;
//import tw.com.wa.invoice.util.DbHelper;
//import tw.com.wa.invoice.util.InvoiceBusinessException;
//import tw.com.wa.invoice.util.InvoiceEnterDAO;
//
///**
// * Created by Andy on 15/1/20.
// */
//public class QueryJob extends AsyncTask<String, List<InvoiceEnter>, List<InvoiceEnter>> {
//
//    private static final String TAG = "QueryJob";
//
//    private static QueryJob job;
//
//    private InvoiceEnterDAO enterDAO = null;
//
//    private Context context = null;
//    private String inYm;
//
//    private List<InvoiceEnter> enters = null;
//
//
//
//
//    public interface
//
//
//
//
//    @Override
//    protected List<InvoiceEnter> doInBackground(String... params) {
//
//        DbHelper dbHelper = new DbHelper(context);
//
//        SQLiteDatabase con = null;
//        try {
//            con = dbHelper.getReadableDatabase();
//
//            this.enterDAO = new InvoiceEnterDAO(con, context);
//            return enterDAO.get(inYm);
//
//        } catch (InvoiceBusinessException e) {
//            Log.e(TAG, "e" + e.getMessage());
//        } finally {
//            if (con != null) {
//                con.close();
//
//            }
//
//        }
//        return null;
//
//
//    }
//
//    @Override
//    protected void onPostExecute(List<InvoiceEnter> invoiceEnters) {
//        dto.setEnters(invoiceEnters);
//        laySwipe.setRefreshing(false);
//
//        if (invoiceEnters == null || invoiceEnters.isEmpty()) {
//            blankView.setVisibility(View.VISIBLE);
//            recyclerView.setVisibility(View.INVISIBLE);
//        } else {
//            recyclerView
//                    .setVisibility(View.VISIBLE);
//            blankView.setVisibility(View.INVISIBLE);
//        }
//
//        recyclerView.setAdapter(new InvAdapter(invoiceEnters, getActivity()));
//    }
//
//
//    private interface OnListener {
//        void onPreExecute();
//
//        void onPostExeute();
//    }
//
//    public class InvAdapter extends RecyclerView.Adapter<InvAdapter.ViewHolder> {
//
//        private int lastPosition = -1;
//        private List<InvoiceEnter> enters = null;
//        private Context context;
//
//
//        public InvAdapter(List<InvoiceEnter> enters, Context context) {
//            this.enters = enters;
//            this.context = context;
//        }
//
//
//        @Override
//        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//
//            View v = LayoutInflater.from(viewGroup.getContext())
//                    .inflate(R.layout.enter_layout, viewGroup, false);
//            // set the view's size, margins, paddings and layout parameters
//
//            ViewHolder vh = new ViewHolder(v);
//            return vh;
//        }
//
//        @Override
//        public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
//
//
//            final InvoiceEnter mainNumber = enters.get(position);
//
//            viewHolder.label.setText(mainNumber.getNumber());
//
//            this.setAnimation(viewHolder.container, position);
//
//
//        }
//
//
//        @Override
//        public int getItemCount() {
//            return enters.size();
//        }
//
//        private void setAnimation(View viewToAnimate, int position) {
//            // If the bound view wasn't previously displayed on screen, it's animated
//            if (position > lastPosition) {
//                Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
//                viewToAnimate.startAnimation(animation);
//                lastPosition = position;
//            }
//        }
//
//
//        public class ViewHolder extends RecyclerView.ViewHolder {
//            // each data item is just a string in this case
//
//            private TextView label;
//            private View container;
//
//
//            public ViewHolder(View v) {
//                super(v);
//                this.container = v.findViewById(R.id.container);
//                this.label = (TextView) v.findViewById(R.id.label);
//
//            }
//        }
//    }
//
//}
