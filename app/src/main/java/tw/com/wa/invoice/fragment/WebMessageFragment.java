package tw.com.wa.invoice.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import tw.com.wa.invoice.R;

/**
 * Created by Andy on 15/1/23.
 */
public class WebMessageFragment extends Fragment {

    private WebView webView = null;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        final View rootView = inflater.inflate(R.layout.fragment_webmessage_layout, container, false);



        return rootView;
    }
}
