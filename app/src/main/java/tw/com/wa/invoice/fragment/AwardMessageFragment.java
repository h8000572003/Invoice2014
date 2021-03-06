package tw.com.wa.invoice.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tw.com.wa.invoice.R;
import tw.com.wa.invoice.domain.BeanUtil;
import tw.com.wa.invoice.domain.Invoice;
import tw.com.wa.invoice.domain.Way;
import tw.com.wa.invoice.domain.WiningInfo;
import tw.com.wa.invoice.ui.StagingView;
import tw.com.wa.invoice.util.RisCommon;

/**
 * Created by Andy on 15/1/23.
 */
public class AwardMessageFragment extends Fragment {

    private static final String TAG = "AwardMessageFragment";

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private StagingView stagingView = null;

    private List<WayDTO> wayDTOs = new ArrayList<>();
    private Job job = null;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.award_message_layout, container, false);

        this.stagingView = (StagingView) rootView.findViewById(R.id.stagingView);
        this.recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        this.mLayoutManager = new LinearLayoutManager(getActivity());
        this.recyclerView.setLayoutManager(mLayoutManager);

        this.stagingView.buildNowStatus();

        this.stagingView.setOnValueChangeListener(new StagingView.OnInfoChangeListener() {
            @Override
            public void onFail(final Throwable e, String messsage) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onLoad() {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onSuccessfully(WiningInfo winingInfo) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (getJob() == null) {
                            job = new Job();
                            job.execute();
                        } else {
                            Toast.makeText(getActivity(), "資料更新中請稍後載試", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        if (this.getJob() == null) {
            job = new Job();
            this.getJob().execute();
        }


        return rootView;
    }


    public synchronized Job getJob() {
        return job;
    }

    private class Job extends AsyncTask<Void, Void, Void> {


        private RisCommon risCommon = RisCommon.getRisCommon();

        @Override
        protected void onPreExecute() {
            wayDTOs.clear();
            ;
        }

        @Override
        protected Void doInBackground(Void... params) {


            final List<Invoice> invoices = this.risCommon.getInvoice(BeanUtil.getInfo());
            for (Way way : Way.values()) {
                final WayDTO wayDTO = new WayDTO();
                wayDTOs.add(wayDTO);

                wayDTO.way = way;

                for (Invoice invoice : invoices) {
                    if (invoice.getAwards().equals(way.getCode())) {
                        wayDTO.invoices.add(invoice);
                    }
                }

            }
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            recyclerView.setAdapter(new InvAdapter());
            job = null;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case

            private TextView messageLabel;
            private TextView title;
            private TextView invoviceLabel;
            private CardView container;


            public ViewHolder(View v) {
                super(v);


                this.messageLabel = (TextView) v.findViewById(R.id.messageLabel);
                this.title = (TextView) v.findViewById(R.id.title);
                this.container = (CardView) v.findViewById(R.id.container);
                this.invoviceLabel = (TextView) v.findViewById(R.id.invoviceLabel);


            }
        }

        public class InvAdapter extends RecyclerView.Adapter<Job.ViewHolder> {


            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.award_way_item, parent, false);
                ViewHolder vh = new ViewHolder(v);
                return vh;

            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {

                final WayDTO item = wayDTOs.get(position);
                final Way way = item.way;


                List<String> invs = new ArrayList<>();

                for (Invoice invoice : item.invoices) {
                    invs.add(invoice.getNumber());
                }
                ;


                holder.invoviceLabel.setText(TextUtils.join(" ， ", invs));
                holder.messageLabel.setText(String.format(way.getMessage(), way.getAward().dollar));
                holder.title.setText(way.getAward().message);

                if (invs.isEmpty()) {
                    holder.invoviceLabel.setVisibility(View.GONE);
                } else {
                    holder.invoviceLabel.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public int getItemCount() {
                return wayDTOs.size();
            }
        }
    }

    private class WayDTO {
        private Way way;
        private List<Invoice> invoices = new ArrayList<Invoice>();
    }


}
