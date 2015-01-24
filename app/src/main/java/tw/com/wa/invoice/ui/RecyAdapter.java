package tw.com.wa.invoice.ui;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.List;

import tw.com.wa.invoice.R;
import tw.com.wa.invoice.domain.Award;
import tw.com.wa.invoice.domain.InvoiceEnter;
import tw.com.wa.invoice.util.OnItemClickListner;

/**
 * Created by Andy on 15/1/24.
 */
public class RecyAdapter extends RecyclerView.Adapter<RecyAdapter.ViewHolder> {

    private OnItemClickListner onItemClickListner = null;

    private List<InvoiceEnter> enters = null;

    private int lastPosition = -1;

    private Context context;


    public RecyAdapter(List<InvoiceEnter> enters) {
        this.enters = enters;
    }

    public void setOnItemClickListner(OnItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        this.context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.number_layout_v2, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final InvoiceEnter enter = this.enters.get(position);

        if (TextUtils.isEmpty(enter.getStatus())) {

            holder.contentView.setText(enter.getNumber());
            holder.moneyView.setText(String.format("+$%,d", 0));
            holder.titileView.setText("無中獎");



        } else {
            final Award awrar = Award.lookup(enter.getStatus());

            holder.contentView.setText(enter.getNumber());
            holder.moneyView.setText(String.format("+$%,d", awrar.dollar));
            holder.titileView.setText(awrar.message);

        }

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListner != null) {
                    onItemClickListner.onItemClick(position);
                }
            }
        });

        this.setAnimation(holder.container, position);
    }

    @Override
    public int getItemCount() {
        return enters.size();
    }

    private synchronized void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.abc_slide_in_top);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        private TextView titileView;
        private TextView contentView;
        private TextView moneyView;

        private CardView container;


        public ViewHolder(View v) {
            super(v);


            this.titileView = (TextView) v.findViewById(R.id.titileView);
            this.contentView = (TextView) v.findViewById(R.id.contentView);
            this.container = (CardView) v.findViewById(R.id.container);
            this.moneyView = (TextView) v.findViewById(R.id.moneyView);


            this.titileView.setTextColor(Color.GRAY);
            this.contentView.setTextColor(Color.GRAY);


        }
    }

}
