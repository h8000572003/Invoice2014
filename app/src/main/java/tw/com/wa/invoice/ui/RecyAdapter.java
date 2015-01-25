package tw.com.wa.invoice.ui;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import tw.com.wa.invoice.R;
import tw.com.wa.invoice.domain.Award;
import tw.com.wa.invoice.domain.CheckStatus;
import tw.com.wa.invoice.domain.InvoiceEnter;
import tw.com.wa.invoice.util.OnItemClickListner;
import tw.com.wa.invoice.util.OnValueClickListner;

/**
 * Created by Andy on 15/1/24.
 */
public class RecyAdapter extends RecyclerView.Adapter<RecyAdapter.ViewHolder> {

    private OnItemClickListner onItemClickListner = null;
    private OnValueClickListner onBtnListner = null;

    private OnValueClickListner onKeyboardClickListner = null;

    private List<InvoiceEnter> enters = null;

    private int lastPosition = -1;

    private Context context;


    public RecyAdapter(List<InvoiceEnter> enters) {
        this.enters = enters;
    }

    public void setOnBtnListner(OnValueClickListner onBtnListner) {
        this.onBtnListner = onBtnListner;
    }

    public void setOnKeyboardClickListner(OnValueClickListner onKeyboardClickListner) {
        this.onKeyboardClickListner = onKeyboardClickListner;
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final InvoiceEnter enter = this.enters.get(position);

        final CheckStatus status = CheckStatus.valueOf(enter.getStatus());
        switch (status) {

            case Get:

                final Award awrar = Award.lookup(enter.getAward());
                holder.contentView.setText(enter.getNumber());
                holder.moneyView.setText(String.format("+$%,d", awrar.dollar));
                holder.titileView.setText(awrar.message);
                holder.editView.setEnabled(false);
                holder.editView.setVisibility(View.GONE);

                break;

            case None:
                holder.contentView.setText(enter.getNumber());
                holder.moneyView.setText(String.format("+$%,d", 0));
                holder.titileView.setText(context.getString(R.string.recy_adtper_lab1));
                holder.editView.setEnabled(false);
                holder.editView.setVisibility(View.GONE);

                break;

            case Continue:
                holder.contentView.setText(enter.getNumber());
                holder.moneyView.setText(String.format("+$%,d", 0));
                holder.titileView.setText(context.getString(R.string.recy_adtper_lab2));
                holder.editView.setEnabled(true);
                holder.editView.setVisibility(View.VISIBLE);
                holder.container.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListner != null) {
                            onItemClickListner.onItemClick(holder.editView, position);
                        }
                    }
                });
                break;

        }
        holder.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.hint.setVisibility(View.GONE);
                if (onBtnListner != null) {
                    onBtnListner.onItemClick(holder.keyBoardLayout.getValue(), position);
                    Animation animation = AnimationUtils.loadAnimation(context, R.anim.abc_fade_out);
                    holder.editView.startAnimation(animation);
                    holder.editView.setVisibility(View.GONE);
                }

            }
        });


        holder.keyBoardLayout.setOnValueChangeListener(new KeyBoardLayout.OnValueChangeListener() {
            @Override
            public void onChange(String value) {
                if (onKeyboardClickListner != null) {
                    onKeyboardClickListner.onItemClick(value, position);
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

        private TextView titileView = null;
        private TextView contentView = null;
        private TextView moneyView = null;
        private View editView = null;
        private KeyBoardLayout keyBoardLayout;
        private Button saveBtn = null;
        private TextView hint= null;
        private CardView container = null;


        public ViewHolder(final View v) {
            super(v);

            this.titileView = (TextView) v.findViewById(R.id.titileView);
            this.contentView = (TextView) v.findViewById(R.id.contentView);
            this.container = (CardView) v.findViewById(R.id.container);
            this.moneyView = (TextView) v.findViewById(R.id.moneyView);
            this.editView = v.findViewById(R.id.editView);
            this.keyBoardLayout = (KeyBoardLayout) v.findViewById(R.id.keyboardLayout);
            this.saveBtn = (Button) v.findViewById(R.id.saveBtn);
            this.hint = (TextView) v.findViewById(R.id.hint);

            this.keyBoardLayout.setMonitorView(this.contentView);
            this.titileView.setTextColor(Color.GRAY);
            this.contentView.setTextColor(Color.GRAY);


        }
    }

}
