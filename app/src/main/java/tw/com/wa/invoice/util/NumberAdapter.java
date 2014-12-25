package tw.com.wa.invoice.util;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.parse.codec.binary.StringUtils;

import java.util.List;

import tw.com.wa.invoice.R;
import tw.com.wa.invoice.domain.InvoiceKeyIn;

/**
 * Created by Andy on 2014/12/22.
 */
public class NumberAdapter extends RecyclerView.Adapter<NumberAdapter.ViewHolder> {

    private int lastPosition = -1;
    private List<InvoiceKeyIn> mainNumbers = null;
    private Context context;
    private OnItemClickListener listener;


    public NumberAdapter(List<InvoiceKeyIn> mainNumbers, Context context) {
        this.mainNumbers = mainNumbers;
        this.context = context;
        this.listener = listener;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.number_layout, viewGroup, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {


        final InvoiceKeyIn mainNumber = mainNumbers.get(position);

        mainNumber.setTitle(mainNumber.getAward() != null ? //
                mainNumber.getAward().message ://
                context.getString(R.string.now_win));//

        mainNumber.setKeyNumber(mainNumber.getKeyNumber());

        viewHolder.titileView.setText(mainNumber.getTitle());

        int distinct = 8 - mainNumber.getKeyNumber().length();


        viewHolder.contentView.setText(this.leftPandSignal("□", distinct, mainNumber.getKeyNumber()));
        viewHolder.moneyView.setText("$" + mainNumber.getAward().dollar);
        viewHolder.keyTime.setText(mainNumber.getKeyInTime());
        viewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(viewHolder.container, position);
                }
            }
        });

        this.setAnimation(viewHolder.container, position);

//            viewHolder.awardText.setText(mainNumber.getAward().message);
        //   viewHolder.numberText.setText(mainNumber.getCountOfInvoice());
        //          viewHolder.doorText.setText(mainNumber.sum() + "元");
    }

    private String leftPandSignal(String signal, int legth, String orgContent) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < legth; i++) {
            buffer.append(signal);
        }
        return buffer.toString() + orgContent;

    }

    @Override
    public int getItemCount() {
        return mainNumbers.size();
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int location);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        private TextView titileView;
        private TextView contentView;
        private TextView moneyView;
        private TextView keyTime;
        private CardView container;


        public ViewHolder(View v) {
            super(v);
            this.titileView = (TextView) v.findViewById(R.id.titileView);
            this.contentView = (TextView) v.findViewById(R.id.contentView);
            this.container = (CardView) v.findViewById(R.id.container);
            this.moneyView = (TextView) v.findViewById(R.id.moneyView);
            this.keyTime= (TextView) v.findViewById(R.id.keyTime);



        }
    }
}
