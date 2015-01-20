package tw.com.wa.invoice;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import tw.com.wa.invoice.compent.BaseCheckInvoiceAward;
import tw.com.wa.invoice.compent.ExecutionCheckInvoiceAward;
import tw.com.wa.invoice.compent.ExecutionCheckManger;
import tw.com.wa.invoice.domain.Award;
import tw.com.wa.invoice.domain.BeanUtil;
import tw.com.wa.invoice.domain.CheckStatus;
import tw.com.wa.invoice.domain.InvoiceKeyIn;
import tw.com.wa.invoice.domain.MainDTO;
import tw.com.wa.invoice.domain.OutInfo;
import tw.com.wa.invoice.domain.WiningInfo;
import tw.com.wa.invoice.ui.KeyBoardLayout;
import tw.com.wa.invoice.ui.StagingView;
import tw.com.wa.invoice.ui.ToolBar;
import tw.com.wa.invoice.util.CommomUtil;

/**
 * Created by Andy on 2015/1/9.
 */
public class MainActivityV2 extends ActionBarActivity {

    private final static int GO_SEE_INVOICE_CODE = 001;

    private KeyBoardLayout keyboardView = null;

    private StagingView stagingView = null;
    private TextView messageLabel = null;
    private TextView invoviceLabel = null;
    private CommomUtil commomUtil = new CommomUtil();
    private Button addCalendarBtn;

    private MainDTO dto;
    private Vibrator myVibrator = null;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        this.dto = new MainDTO();


        this.myVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        this.invoviceLabel = (TextView) this.findViewById(R.id.invoviceLabel);
        this.keyboardView = (KeyBoardLayout) this.findViewById(R.id.keyboardLayout);
        this.stagingView = (StagingView) this.findViewById(R.id.stagingView);
        this.messageLabel = (TextView) this.findViewById(R.id.messageLabel);
        this.addCalendarBtn = (Button) this.findViewById(R.id.addCalendarBtn);


        this.setKeyBoardListener();

        this.stagingView.setOnValueChangeListener(new StagingView.OnValueChangeListener() {
            @Override
            public void onFail(final Throwable e, final String messsage) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(MainActivityV2.this, messsage, Toast.LENGTH_SHORT).show();

                    }
                });
            }

            @Override
            public void onLoad() {
                dialog = ProgressDialog.show(MainActivityV2.this, "", "取得發票資訊", true, false);
                runOnUiThread(new Runnable() {
                    public void run() {

                    }
                });
            }

            @Override
            public void onFinish() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                            dialog = null;
                        }


                    }
                });

            }

            @Override
            public void onSuccessfully(WiningInfo outInfo) {
                WiningInfo info = stagingView.getOutInfo();

                dto.setInfo(outInfo);
                dto.setInvoiceInfoV2(info.getInfoV2());
                dto.setInvoices(info.getInvoice());
            }
        });


        this.stagingView.init(BeanUtil.outInfo);

        WiningInfo info = stagingView.getOutInfo();
        dto.setInfo(BeanUtil.outInfo);
        dto.setInvoiceInfoV2(info.getInfoV2());
        dto.setInvoices(info.getInvoice());


    }

    /**
     * 設定鍵盤監控事件
     */
    public void setKeyBoardListener() {


        this.keyboardView.setMonitorView(this.invoviceLabel);
        this.keyboardView.setOnValueChangeListener(new KeyBoardLayout.OnValueChangeListener() {
            @Override
            public void onChange(String value) {

                messageLabel.setText(getString(R.string.plsEnter));


                if (value.length() == 3) {


                    keyboardView.cleanValueWithoutUI();

                    final CheckStatus checkStatus =
                            commomUtil.checkAward3Number(value, dto.getInvoices());

                    switch (checkStatus) {
                        case None:
                            messageLabel.setText(R.string.no_award_change);
                            break;

                        case Continue:
                            messageLabel.setText(R.string.haveOpportunity);
                            work2Continue(value);
                            break;

                        case Get:
                            messageLabel.setText(getString(R.string.get6Award));
                            workForSixAward(value);
                            break;
                    }

                }
            }
        });

        this.addCalendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivityV2.this, AwardActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("Ym", dto.getInfo().getStages().getAwardRangDate().toString());
                it.putExtras(bundle);

                BeanUtil.info = dto.getInfo();
                startActivityForResult(it, GO_SEE_INVOICE_CODE);

            }
        });
    }

    private void work2Continue(String value) {
        showNumberDiaglog();
    }

    private void showNumberDiaglog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_layout, null);
        builder.setCancelable(false);
        builder.setView(dialogView);

        TextView showView = (TextView) dialogView.findViewById(R.id.numberText);
        KeyBoardLayout keyBoardLayout = (KeyBoardLayout) dialogView.findViewById(R.id.keyboardLayout);
        keyBoardLayout.setMonitorView(showView);

        final AlertDialog dialog = builder.create();
        dialog.show();
        keyBoardLayout.setOnValueChangeListener(new KeyBoardLayout.OnValueChangeListener() {
            @Override
            public void onChange(String value) {
                if (value.length() == 8) {
                    dialog.dismiss();
                    check8NumberAction(value);
                    ;
                }

            }
        });

    }

    private void workForSixAward(String value) {
        InvoiceKeyIn keyIn = new InvoiceKeyIn(value);
        keyIn.setAward(Award.Exactsix);

        BeanUtil.allInvoices.add(keyIn);

        myVibrator.vibrate(200);


        addCalendarBtn.setText(getString(R.string.addCalendarBtn, BeanUtil.allInvoices.size()));


        if (addCalendarBtn.getVisibility() == View.GONE) {
            addCalendarBtn.setVisibility(View.VISIBLE);
            AnimationSet animationset = new AnimationSet(true);
            animationset.addAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
            addCalendarBtn.startAnimation(animationset);


        } else {


            AnimationSet animationset = new AnimationSet(true);

            Animation translateAnimation = new TranslateAnimation(0, 0, 0, 20);
            translateAnimation.setDuration(100);
            animationset.addAnimation(translateAnimation);

            animationset.setRepeatCount(3);

            addCalendarBtn.startAnimation(animationset);
        }


    }

    private void check8NumberAction(String value) {
        Award award = commomUtil.checkAward(value, dto.getInvoices());

        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);

        myAlertDialog.setTitle(getString(R.string.app_name));

        if (award != null) {

            InvoiceKeyIn keyIn = new InvoiceKeyIn(value);
            keyIn.setAward(award);
            BeanUtil.allInvoices.add(keyIn);


            if (addCalendarBtn.getVisibility() == View.GONE) {
                addCalendarBtn.setVisibility(View.VISIBLE);
                AnimationSet animationset = new AnimationSet(true);
                animationset.addAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
                addCalendarBtn.startAnimation(animationset);


            } else {


                AnimationSet animationset = new AnimationSet(true);

                Animation animation = new ScaleAnimation(0, 0, 0, 20);
                animation.setDuration(100);
                animation.setRepeatCount(3);

                Animation translateAnimation = new TranslateAnimation(0, 0, 0, 20);
                translateAnimation.setDuration(100);
                animationset.addAnimation(translateAnimation);

                animationset.setRepeatCount(3);

                addCalendarBtn.startAnimation(animationset);
            }
            addCalendarBtn.setText(getString(R.string.addCalendarBtn, BeanUtil.allInvoices.size()));


            myAlertDialog.setMessage("中" + award.message);
            messageLabel.setText("中" + award.message);
        } else {
            myAlertDialog.setMessage("沒有中獎");
            messageLabel.setText("沒有中獎");
        }


        myAlertDialog.setNegativeButton("知道", null);
        myAlertDialog.show();
    }
}
