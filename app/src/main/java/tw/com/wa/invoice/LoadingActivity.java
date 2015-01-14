package tw.com.wa.invoice;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

import tw.com.wa.invoice.api.LoadService;
import tw.com.wa.invoice.core.LoadServiceImpl;
import tw.com.wa.invoice.domain.BeanUtil;
import tw.com.wa.invoice.domain.LoadDTO;
import tw.com.wa.invoice.ui.ToolBar;

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
    private ToolBar toolBar;
    private SwipeRefreshLayout laySwipe;
    private TextView statuLabel;
    private TextView newYmView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.loading_layout);


        this.dto = new LoadDTO();
        this.service = new LoadServiceImpl();

        this.toolBar = (ToolBar) this.findViewById(R.id.toorBar);
        this.statuLabel = (TextView) this.findViewById(R.id.statusLabel);
        this.newYmView = (TextView) this.findViewById(R.id.newYmView);

        this.laySwipe = (SwipeRefreshLayout) this.findViewById(R.id.laySwipe);
        this.laySwipe.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light);
        this.laySwipe.setOnRefreshListener(onSwipeToRefresh);

        this.toolBar.setBtn1OnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(LoadingActivity.this, MainActivityV2.class);
                BeanUtil.outInfo = dto.getOutInfo();
                startActivity(it);

            }
        });
        this.toolBar.setBtn2OnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(LoadingActivity.this, AboutActivity.class);
                startActivity(it);

            }
        });
        this.toolBar.build();


        if (task == null) {
            task = new LoadAsyncTask();
            task.execute("");
        }


    }


    private class LoadAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            if (toolBar.getVisibility() == View.VISIBLE) {
                toolBar.setVisibility(View.GONE);
            }
            newYmView.setVisibility(View.GONE);
            statuLabel.setText(R.string.loading);
            laySwipe.setRefreshing(true);


        }

        @Override
        protected String doInBackground(String... params) {


            ConnectivityManager conManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);//先取得此service

            NetworkInfo networInfo = conManager.getActiveNetworkInfo();       //在取得相關資訊

            if (networInfo == null || !networInfo.isAvailable()) { //判斷是否有網路
                return "尚未連接網路，請連接網路在測試一次";
            }

            service.loadData(dto, activity);

            return null;
        }


        @Override
        protected void onPostExecute(String s) {


            laySwipe.setRefreshing(false);
            task = null;

            if (s != null) {
                statuLabel.setText(s);
                statuLabel.setTextColor(Color.RED);

            } else {
                statuLabel.setTextColor(getResources().getColor(R.color.material_blue_grey_800));

                newYmView.setVisibility(View.VISIBLE);
                newYmView.setText("最新期數：" + dto.getOutInfo().getTitle());


                statuLabel.setVisibility(View.GONE);
                toolBar.setVisibility(View.VISIBLE);


            }

        }
    }
}
