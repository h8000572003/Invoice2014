package tw.com.wa.invoice.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import tw.com.wa.invoice.R;

/**
 * Created by Andy on 2014/12/30.
 */
public class TeachingLayout extends LinearLayout {

    private ImageView pic = null;
    private TextView txt;
    private Context context;


    public TeachingLayout(Context context) {
        super(context);
        this.bindView(context);
    }

    public TeachingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.bindView(context);


        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.TeachingLayout,
                0, 0);


        this.setPicUrl(a.getInt(R.styleable.TeachingLayout_src, R.drawable.logo));
        this.setTxt(a.getInt(R.styleable.TeachingLayout_txt, R.string.teachContent));


    }

    public TeachingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.bindView(context);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.TeachingLayout,
                0, 0);


        this.setPicUrl(a.getInt(R.styleable.TeachingLayout_src, R.drawable.logo));
        this.setTxt(a.getInt(R.styleable.TeachingLayout_txt, R.string.teachContent));

    }

    private void bindView(Context context) {
        this.context = context;
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.teaching_view, this, true);


        this.pic = (ImageView) findViewById(R.id.pic);
        this.txt = (TextView) findViewById(R.id.txt);


    }

    public void setPicUrl(int resource) {

        pic.setImageResource(resource);
    }

    public void setTxt(String txt) {
        this.txt.setText(txt);
    }

    public void setTxt(int resourceFromString) {
        this.txt.setText(resourceFromString);
    }


}
