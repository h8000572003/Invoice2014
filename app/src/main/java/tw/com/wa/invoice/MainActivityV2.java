package tw.com.wa.invoice;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import tw.com.wa.invoice.domain.MainDTO;
import tw.com.wa.invoice.domain.OutInfo;
import tw.com.wa.invoice.ui.KeyBoardLayout;
import tw.com.wa.invoice.ui.StagingView;

/**
 * Created by Andy on 2015/1/9.
 */
public class MainActivityV2 extends ActionBarActivity {

    private KeyBoardLayout keyboardView = null;
    private StagingView stagingView = null;

    private MainDTO dto;
    private Vibrator myVibrator = null;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        this.dto = new MainDTO();
        this.myVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        this.keyboardView = (KeyBoardLayout) this.findViewById(R.id.keyboardLayout);
        this.stagingView = (StagingView) this.findViewById(R.id.stagingView);


        this.stagingView.run("10310");


        this.stagingView.setOnValueChangeListener(new StagingView.OnValueChangeListener() {
            @Override
            public void onFail(final Throwable e,String messsage) {
                runOnUiThread(new Runnable() {
                    public void run() {

                        Toast.makeText(MainActivityV2.this, "onfail message=" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onLoad() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        dialog = ProgressDialog.show(MainActivityV2.this, "", "load",true,false);

                        Toast.makeText(MainActivityV2.this, "onLoad ", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onFinish() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        dialog.dismiss();
                        Toast.makeText(MainActivityV2.this, "onFinish ", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

    }
}
