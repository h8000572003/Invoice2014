package tw.com.wa.invoice;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.parse.ParseQuery;

import java.util.List;

import tw.com.wa.invoice.api.LoadService;
import tw.com.wa.invoice.core.LoadServiceImpl;
import tw.com.wa.invoice.domain.BeanUtil;
import tw.com.wa.invoice.domain.Invoice;
import tw.com.wa.invoice.domain.InvoiceInfoV2;
import tw.com.wa.invoice.domain.LoadDTO;
import tw.com.wa.invoice.domain.WiningBean;
import tw.com.wa.invoice.marker.WiningsAdapter;
import tw.com.wa.invoice.marker.WiningsMarker;
import tw.com.wa.invoice.util.CommomUtil;
import tw.com.wa.invoice.util.InvoiceBusinessException;

/**
 * Created by Andy on 2014/12/12.
 */
public class LoadingActivity extends Activity {

    private static final String TAG = "LoadingActivity";

    private Activity activity = this;

    private LoadService service;
    private LoadDTO dto = null;
    private LoadAsyncTask task = null;


    private SwipeRefreshLayout.OnRefreshListener onSwipeToRefresh = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            task = new LoadAsyncTask();
            task.execute("");
        }
    };
    private ProgressBar progressBar = null;
    private SwipeRefreshLayout laySwipe;
    private TextView statuLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.loading_layout);


        this.dto = new LoadDTO();
        this.service = new LoadServiceImpl();


        this.statuLabel = (TextView) this.findViewById(R.id.statusLabel);
        this.progressBar = (ProgressBar) this.findViewById(R.id.progressBar2);
        this.laySwipe = (SwipeRefreshLayout) this.findViewById(R.id.laySwipe);
        this.laySwipe.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light);
        this.laySwipe.setOnRefreshListener(onSwipeToRefresh);


        if (task == null) {
            task = new LoadAsyncTask();
            task.execute("");
        }


    }




    private class LoadAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

            progressBar.setVisibility(View.VISIBLE);
            laySwipe.setRefreshing(true);


        }

        @Override
        protected String doInBackground(String... params) {



            ConnectivityManager conManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);//先取得此service

            NetworkInfo networInfo = conManager.getActiveNetworkInfo();       //在取得相關資訊

            if (networInfo == null || !networInfo.isAvailable()) { //判斷是否有網路
                return "尚未連接網路，請連接網路在測試一次";
            }


            return null;
        }






        @Override
        protected void onPostExecute(String s) {
            statuLabel.clearAnimation();
            progressBar.setVisibility(View.GONE);
            laySwipe.setRefreshing(false);
            task = null;

            if (s != null) {
                statuLabel.setText(s);
                statuLabel.setTextColor(Color.RED);

            } else {

                Animation animation = AnimationUtils.loadAnimation(activity, R.anim.scale);
                statuLabel.startAnimation(animation);


                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        statuLabel.setVisibility(View.INVISIBLE);
                        Intent it = new Intent(LoadingActivity.this, MainActivityV2.class);
                        startActivity(it);
                        finish();

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });


            }

        }
    }
}
