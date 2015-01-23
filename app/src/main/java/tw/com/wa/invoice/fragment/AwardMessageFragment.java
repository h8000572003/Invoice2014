package tw.com.wa.invoice.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import tw.com.wa.invoice.R;
import tw.com.wa.invoice.domain.BeanUtil;
import tw.com.wa.invoice.domain.Invoice;
import tw.com.wa.invoice.domain.Way;

/**
 * Created by Andy on 15/1/23.
 */
public class AwardMessageFragment extends Fragment {

    private RecyclerView recyclerView;

    private List<WayDTO> wayDTOs = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.award_message_layout, container, false);

        this.recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);


        final List<Invoice> invoices = BeanUtil.info.getInvoice();
        for (Way way : Way.values()) {
            final WayDTO wayDTO = new WayDTO();
            wayDTO.way = way;
            for (Invoice invoice : invoices) {
                if (invoice.getAwards().equals(way.getCode())) {
                    wayDTO.invoices.add(invoice);
                }
            }

        }


        return rootView;
    }


    private class WayDTO {
        private Way way;
        private List<Invoice> invoices = new ArrayList<Invoice>();
    }
}
