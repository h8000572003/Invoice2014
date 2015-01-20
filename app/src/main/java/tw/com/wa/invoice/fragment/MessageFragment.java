package tw.com.wa.invoice.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import tw.com.wa.invoice.R;

/**
 * Created by Andy on 15/1/17.
 */
public class MessageFragment extends Fragment {

    private TextView messageView = null;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        final View rootView = inflater.inflate(R.layout.blank_layout, container, false);

        this.messageView = (TextView) rootView.findViewById(R.id.messageLabel);

        return rootView;

    }

    public void setMessageText(String messageText) {
        this.messageView.setText(messageText);
    }
}
