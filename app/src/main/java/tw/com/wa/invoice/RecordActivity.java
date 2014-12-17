//package tw.com.wa.invoice;
//
//import android.app.ActionBar;
//import android.content.Context;
//import android.os.Bundle;
//import android.os.Handler;
//import android.support.v4.widget.SwipeRefreshLayout;
//import android.support.v7.app.ActionBarActivity;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.Toolbar;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import java.util.List;
//
//import tw.com.wa.invoice.domain.BeanUtil;
//import tw.com.wa.invoice.domain.MainNumber;
//import tw.com.wa.invoice.domain.RecDTO;
//
///**
// * Created by Andy on 2014/12/17.
// */
//public class RecordActivity extends ActionBarActivity {
//
//    private static final String TAG = "RecordActivity";
//
//    private NumberAdapter adapter = null;
//    private RecyclerView recyclerView = null;
//    private SwipeRefreshLayout laySwipe;
//    private RecyclerView.LayoutManager mLayoutManager;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.rec_layout);
//
//        Toolbar mToolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
//
//
//
//        this.recyclerView = (RecyclerView) this.findViewById(R.id.my_recycler_view);
//
//
//        this.mLayoutManager = new LinearLayoutManager(this);
//
//
//        this.recyclerView.setLayoutManager(mLayoutManager);
//        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
//
//        this.laySwipe = (SwipeRefreshLayout) this.findViewById(R.id.laySwipe);
//
//        this.recyclerView.setOnScrollListener(
//                new RecyclerView.OnScrollListener() {
//                    @Override
//                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                        super.onScrollStateChanged(recyclerView, newState);
//                    }
//
//                    @Override
//                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//
//                        int lastVisibleItem = ((LinearLayoutManager) mLayoutManager).findFirstVisibleItemPosition();
//
//
//                        if (lastVisibleItem == 0) {
//                            laySwipe.setEnabled(true);
//                        } else {
//                            laySwipe.setEnabled(false);
//                        }
//                    }
//                }
//        );
//        this.laySwipe.setOnRefreshListener(onSwipeToRefresh);
//
//
//        this.laySwipe.setColorSchemeResources(
//                android.R.color.holo_red_light,
//                android.R.color.holo_blue_light,
//                android.R.color.holo_green_light,setInvoiceNumAdapter
//                android.R.color.holo_orange_light);
//        this.setInvoiceNumAdapter();
//    }
//
//
//
//
//    private void setInvoiceNumAdapter() {
//
//
//        adapter = new NumberAdapter(BeanUtil.mainNumbers, this);
//        this.recyclerView.setAdapter(adapter);
//        this.refreshNumAdapter();
//        ;
//    }
//
//    private void refreshNumAdapter() {
//        adapter.notifyDataSetChanged();
//        ;
//    }
//
//
//}