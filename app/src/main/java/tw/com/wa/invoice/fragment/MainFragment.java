package tw.com.wa.invoice.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tw.com.wa.invoice.AwardActivity;
import tw.com.wa.invoice.R;
import tw.com.wa.invoice.domain.Award;
import tw.com.wa.invoice.domain.BeanUtil;
import tw.com.wa.invoice.domain.CheckStatus;
import tw.com.wa.invoice.domain.InvoiceKeyIn;
import tw.com.wa.invoice.domain.MainDTO;
import tw.com.wa.invoice.domain.WiningInfo;
import tw.com.wa.invoice.ui.KeyBoardLayout;
import tw.com.wa.invoice.ui.StagingView;
import tw.com.wa.invoice.util.CommomUtil;

/**
 * Created by Andy on 15/1/22.
 */
public class MainFragment extends Fragment {

    public final static String LIST = "list";
    public final static String YM = "Ym";


    private final static int GO_SEE_INVOICE_CODE = 001;


    private KeyBoardLayout keyboardView = null;

    private StagingView stagingView = null;
    private TextView messageLabel = null;
    private TextView invoviceLabel = null;
    private CommomUtil commomUtil;


    private Button addCalendarBtn;

    private MainDTO dto;
    private Vibrator myVibrator = null;
    private ProgressDialog dialog;


    public static MainFragment newInstance() {
        final MainFragment fragment = new MainFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        this.dto = new MainDTO();

        this.myVibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        this.commomUtil = new CommomUtil();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        final View rootView = inflater.inflate(R.layout.activity_main2, container, false);

        this.findVIew(rootView);
        this.setKeyBoardListener();
        this.initValue();


        return rootView;
    }

    private void findVIew(View rootView) {
        this.invoviceLabel = (TextView) rootView.findViewById(R.id.invoviceLabel);
        this.keyboardView = (KeyBoardLayout) rootView.findViewById(R.id.keyboardLayout);
        this.stagingView = (StagingView) rootView.findViewById(R.id.stagingView);
        this.messageLabel = (TextView) rootView.findViewById(R.id.messageLabel);
        this.addCalendarBtn = (Button) rootView.findViewById(R.id.addCalendarBtn);
    }

    private void initValue() {
        this.stagingView.buildNowStatus();
        final WiningInfo info = this.stagingView.getOutInfo();
        this.dto.setInfo(BeanUtil.getInfo());
        this.dto.setInvoiceInfoV2(info.getInfoV2());
        this.dto.setInvoices(info.getInvoice());
        this.dto.setNowStageTitle(this.stagingView.getStagingText().getText().toString());
        this.dto.getKeyIn().put(dto.getNowStageTitle(), new ArrayList<InvoiceKeyIn>());

    }

    /**
     * 設定鍵盤監控事件
     */
    public void setKeyBoardListener() {

        this.stagingView.setOnValueChangeListener(new MyOnValueChangeListener());
        //  this.keyboardView.setMonitorView(this.invoviceLabel);
        this.keyboardView.setOnValueChangeListener(new MyKeyboardOnValueChangeListener());
        this.addCalendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), AwardActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(MainFragment.YM, dto.getInfo().getStages().getAwardRangDate().toString());
                bundle.putSerializable(MainFragment.LIST, (java.io.Serializable) dto.getKeyIn().get(dto.getNowStageTitle()));
                it.putExtras(bundle);
                startActivityForResult(it, MainFragment.GO_SEE_INVOICE_CODE);

            }
        });
    }

    private void workForSixAward(String value) {

        final InvoiceKeyIn keyIn = new InvoiceKeyIn(value);
        keyIn.setAward(Award.Exactsix);


        List<InvoiceKeyIn> keyIns =
                dto.getKeyIn().get(dto.getNowStageTitle());
        keyIns.add(keyIn);

        this.myVibrator.vibrate(200);
        this.addCalendarBtn.setText(getString(R.string.addCalendarBtn, this.dto.getKeyIn().get(dto.getNowStageTitle()).size()));

        this.showAddCalendarBtn();


    }


    private void showAddCalendarBtn() {
        if (addCalendarBtn.getVisibility() == View.GONE) {
            addCalendarBtn.setVisibility(View.VISIBLE);
            AnimationSet animationset = new AnimationSet(true);
            animationset.addAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_in_left));
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

    private void work2Continue() {
        showNumberDialog();
    }

    private void showNumberDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_layout, null);
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

    private void check8NumberAction(String value) {
        Award award = commomUtil.checkBestAward(value, dto.getInvoices());

        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(getActivity());

        myAlertDialog.setTitle(getString(R.string.app_name));

        if (award != null) {

            InvoiceKeyIn keyIn = new InvoiceKeyIn(value);
            keyIn.setAward(award);


            this.dto.getKeyIn().get(dto.getNowStageTitle()).add(keyIn);
            if (addCalendarBtn.getVisibility() == View.GONE) {
                addCalendarBtn.setVisibility(View.VISIBLE);
                AnimationSet animationset = new AnimationSet(true);
                animationset.addAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_in_left));
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
            addCalendarBtn.setText(getString(R.string.addCalendarBtn, dto.getKeyIn().get(dto.getNowStageTitle()).size()));


            myAlertDialog.setMessage("中" + award.message);
            messageLabel.setText("中" + award.message);
        } else {
            myAlertDialog.setMessage("沒有中獎");
            messageLabel.setText("沒有中獎");
        }


        myAlertDialog.setNegativeButton("知道", null);
        myAlertDialog.show();
    }

    private class MyKeyboardOnValueChangeListener implements KeyBoardLayout.OnValueChangeListener {

        @Override
        public void onChange(String value) {
            invoviceLabel.setText(value);
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
                        work2Continue();
                        break;

                    case Get:
                        messageLabel.setText(getString(R.string.get6Award));


                        workForSixAward(value);
                        break;
                }

            }
        }
    }

    private class MyOnValueChangeListener implements StagingView.OnInfoChangeListener {

        @Override
        public void onFail(final Throwable e, final String messsage) {
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getActivity(), messsage, Toast.LENGTH_SHORT).show();

                }
            });1
        }

        @Override
        public void onLoad() {
            dialog = ProgressDialog.show(getActivity(), "", "取得發票資訊", true, false);
            getActivity().runOnUiThread(new Runnable() {
                public void run() {

                }
            });
        }

        @Override
        public void onFinish() {
            getActivity().runOnUiThread(new Runnable() {
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
            dto.setNowStageTitle(outInfo.getTitle());

            getActivity().runOnUiThread(new Runnable() {
                public void run() {

                    if (!dto.getKeyIn().containsKey(dto.getNowStageTitle())) {
                        dto.getKeyIn().put(dto.getNowStageTitle(), new ArrayList<InvoiceKeyIn>());
                    }

                    if (dto.getKeyIn().get(dto.getNowStageTitle()).size() > 0) {
                        addCalendarBtn.setVisibility(View.VISIBLE);

                    } else {
                        addCalendarBtn.setVisibility(View.GONE);


                    }
                    addCalendarBtn.setText(getString(R.string.addCalendarBtn, dto.getKeyIn().get(dto.getNowStageTitle()).size()));

                }
            });


        }
    }


}
