package tw.com.wa.invoice.teach;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tw.com.wa.invoice.R;
import tw.com.wa.invoice.ui.TeachingLayout;

/**
 * Created by Andy on 2014/12/30.
 */
public class TeachingKey1Fragment extends Fragment {

    private TeachingLayout tachingLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.demo_layout, container, false);

        this.tachingLayout = (TeachingLayout) rootView.findViewById(R.id.teachView);

        tachingLayout.setPicUrl(R.drawable.key_1);
        tachingLayout.setTxt(R.string.teachContent);
        return rootView;
    }

}
