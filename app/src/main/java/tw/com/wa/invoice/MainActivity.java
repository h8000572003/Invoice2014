package tw.com.wa.invoice;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import tw.com.wa.invoice.domain.Award;
import tw.com.wa.invoice.domain.BeanUtil;
import tw.com.wa.invoice.domain.CheckStatus;
import tw.com.wa.invoice.domain.Invoice;
import tw.com.wa.invoice.domain.InvoiceInfoV2;
import tw.com.wa.invoice.domain.InvoiceKeyIn;
import tw.com.wa.invoice.domain.MainDTO;
import tw.com.wa.invoice.ui.DialogUtil;
import tw.com.wa.invoice.ui.KeyBoardLayout;
import tw.com.wa.invoice.util.CommomUtil;


public class MainActivity extends ActionBarActivity {

    private final static int GO_SEE_INVOICE_CODE = 001;
    private static final int QR_CODE = 002;


    private Spinner spinner = null;
    private TextView invoviceLabel = null;
    private TextView invoiceContent = null;
    private TextView messageLabel = null;

    private Button addCalendarBtn;
    private KeyBoardLayout keyBoard = null;
    private ImageButton cameraBtn = null;


    private MainDTO dto;
    private Map<String, InvoiceInfoV2> map = null;
    private List<String> items = null;


    private CommomUtil commomUtil = new CommomUtil();
    private Vibrator myVibrator = null;

    private Activity activity = this;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GO_SEE_INVOICE_CODE) {
            this.initView();
        } else if (requestCode == QR_CODE & null != data && data.getExtras() != null) {
            //掃描結果存放在 key 為 la.droid.qr.result 的值中
            String result = data.getExtras().getString("la.droid.qr.result");

            String seq = result.substring(2, 10);

            if (seq.matches("[0-9]{8}")) {
                keyBoard.setValue(seq);
                check8NumberAction(seq);

            } else {

                this.messageLabel.setText(R.string.plsOtherQr);
                Toast.makeText(getApplicationContext(), R.string.plsOtherQr, Toast.LENGTH_SHORT).show();
            }
            keyBoard.cleanValueWithoutUI();

        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        this.dto = new MainDTO();
        this.myVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

   //     this.map = BeanUtil.getMap();
        new SettingJob(map).execute();


    }

    private boolean isTechDiaglogFlag() {
        SharedPreferences sp =
                getSharedPreferences(DialogUtil.Setting, Context.MODE_PRIVATE);


        return sp.getBoolean(DialogUtil.TEACH_DIALOG_VISIBLE_FLAG, true);
    }

    /**
     * SET VIEW OBJECT BY ID
     */
    private void setViewById() {

        this.spinner = (Spinner) this.findViewById(R.id.monthSpinner);
        this.invoviceLabel = (TextView) this.findViewById(R.id.invoviceLabel);
        this.invoiceContent = (TextView) this.findViewById(R.id.invoiceContent);
        this.messageLabel = (TextView) this.findViewById(R.id.messageLabel);

        this.addCalendarBtn = (Button) this.findViewById(R.id.addCalendarBtn);
        this.keyBoard = (KeyBoardLayout) this.findViewById(R.id.keyboardLayout);
        this.cameraBtn = (ImageButton) this.findViewById(R.id.cameraBtn);


    }

    private void check8NumberAction(String value) {
        Award award = commomUtil.checkAward(value, dto.getInvoices());

        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(MainActivity.this);

        myAlertDialog.setTitle(getString(R.string.app_name));

        if (award != null) {

            InvoiceKeyIn keyIn = new InvoiceKeyIn(value);
            keyIn.setAward(award);
            BeanUtil.allInvoices.add(keyIn);


            if (addCalendarBtn.getVisibility() == View.GONE) {
                addCalendarBtn.setVisibility(View.VISIBLE);
                AnimationSet animationset = new AnimationSet(true);
                animationset.addAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.slide_in_left));
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

    private void showNumberDiaglog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final View dialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_layout, null);
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

    private void work2Continue(String value) {

        showNumberDiaglog();
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
            animationset.addAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.slide_in_left));
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

    private class KeyBoardListener implements KeyBoardLayout.OnValueChangeListener {


        @Override
        public void onChange(String value) {
            messageLabel.setText(getString(R.string.plsEnter));

            if (value.length() == 3) {
                keyBoard.cleanValueWithoutUI();

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
    }
    /**
     * 設定鍵盤監控事件
     */
    public void setKeyBoardListener() {


        this.keyBoard.setMonitorView(this.invoviceLabel);
        this.keyBoard.setOnValueChangeListener(new KeyBoardListener());
        this.cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent("la.droid.qr.scan");    //使用QRDroid的掃描功能
                i.putExtra("la.droid.qr.complete", true);   //完整回傳，不截掉scheme
                try {
                    //開啟 QRDroid App 的掃描功能，等待 call back onActivityResult()
                    startActivityForResult(i, QR_CODE);
                } catch (ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(), R.string.downloadFromGooglePlay, Toast.LENGTH_LONG).show();
                    //若沒安裝 QRDroid，則開啟 Google Play商店，並顯示 QRDroid App
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=la.droid.qr"));
                    startActivity(intent);
                }
            }
        });

        this.addCalendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, AwardActivity.class);
              //  Serializable serializable=dto.getInfo();
            //    it.putExtra("Ym",dto.getInfo().getStages().getAwardRangDate().toString());

            //    BeanUtil.infoV2 = dto.getInvoiceInfoV2();
                startActivityForResult(it, GO_SEE_INVOICE_CODE);

            }
        });
        this.initView();

    }

    private void initView() {
        if (!BeanUtil.allInvoices.isEmpty()) {
            if (addCalendarBtn.getVisibility() == View.GONE) {
                addCalendarBtn.setVisibility(View.VISIBLE);
                AnimationSet animationset = new AnimationSet(true);
                animationset.addAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_in));
                addCalendarBtn.startAnimation(animationset);
                addCalendarBtn.setText(getString(R.string.addCalendarBtn, BeanUtil.allInvoices.size()));
            }


        }
    }

    /**
     * 設定對發票日期
     */
    private void setInvoiceDataAdapter() {
        items = new ArrayList<String>();

        for (Map.Entry<String, InvoiceInfoV2> entry : map.entrySet()) {
            items.add(entry.getKey());
        }
        BaseAdapter spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        this.spinner.setAdapter(spinnerAdapter);


        this.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                InvoiceInfoV2 info =
                        map.get(items.get(position));

                dto.setInvoices(info.getInvoice());
                dto.setInvoiceInfoV2(info);

                List<OrderObject> rrderObjects = new ArrayList<OrderObject>();


                for (Invoice invoice : info.getInvoice()) {
                    OrderObject obj = new OrderObject();
                    obj.award = Award.lookup(invoice.getAwards());
                    obj.invoice = invoice;
                    rrderObjects.add(obj);
                }                /**
                 * sort
                 */
                Collections.sort(rrderObjects, new Comparator<OrderObject>() {
                    @Override
                    public int compare(OrderObject lhs, OrderObject rhs) {

                        return lhs.award.order > rhs.award.order ? 1 : -1;

                    }
                });

                StringBuffer buffer = new StringBuffer();
                buffer.append(CommomUtil.getTitleDate(info, activity) + "\n");
                for (OrderObject a : rrderObjects) {
                    buffer.append(a.award.message);
                    buffer.append("\t:\t");
                    buffer.append(a.invoice.getNumber());
                    buffer.append("\n");
                }


                invoiceContent.setText(buffer.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_helper) {
//            AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
//
//            myAlertDialog.setTitle(R.string.teachTitle);
//            myAlertDialog.setMessage(R.string.teachContent);
//            myAlertDialog.setNegativeButton("知道", null);
//            myAlertDialog.show();


            Intent it = new Intent(this, TeachungActivity.class);
            startActivity(it);

            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private class SettingJob extends AsyncTask<Void, Void, Void> {


        private Map<String, InvoiceInfoV2> map = null;

        private SettingJob(Map<String, InvoiceInfoV2> map) {
            this.map = map;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(Void aVoid) {
       //     this.map = BeanUtil.getMap();
            setViewById();
            setKeyBoardListener();
            setInvoiceDataAdapter();


            if (isTechDiaglogFlag()) {
                DialogUtil.showTeching(activity);

            }

        }


        @Override
        protected Void doInBackground(Void... params) {


            return null;
        }
    }

    private class OrderObject {
        private Invoice invoice;
        private Award award;
    }


}
