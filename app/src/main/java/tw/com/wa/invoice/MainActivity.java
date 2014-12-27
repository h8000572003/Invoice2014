package tw.com.wa.invoice;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.inputmethodservice.KeyboardView;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import tw.com.wa.invoice.ui.KeyBoardLayout;
import tw.com.wa.invoice.util.CommomUtil;


public class MainActivity extends ActionBarActivity {

    private final static int GO_SEE_INVOICE_CODE = 001;
    private final static String Setting = "Setting";
    private String TEACH_DIALOG_VISIBLE_FLAG = "isVisibleFlag";

    private Spinner spinner = null;
    private TextView invoviceLabel = null;
    private TextView invoiceContent = null;
    private TextView messageLabel = null;
    private ViewGroup content = null;
    private Button addCalendarBtn;
    private KeyBoardLayout keyBoard = null;


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
        }


    }

    private class SettingJob extends AsyncTask<Void, Void, Void> {


        private ProgressDialog loadProgress = null;
        private Map<String, InvoiceInfoV2> map = null;

        private SettingJob(Map<String, InvoiceInfoV2> map) {
            this.map = map;
        }

        @Override
        protected void onPreExecute() {

            this.loadProgress = ProgressDialog.show(activity, "", "", true, false);


        }

        @Override
        protected void onPostExecute(Void aVoid) {

            this.loadProgress.dismiss();

            if (isTechDiaglogFlag()) {
                AlertDialog.Builder diaglogOfTech = new AlertDialog.Builder(activity);
                diaglogOfTech.setTitle(R.string.teachTitle);
                diaglogOfTech.setMessage(R.string.teachContent);
                diaglogOfTech.setNegativeButton("知道", null);
                diaglogOfTech.setPositiveButton(R.string.noReminder, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sp =
                                getSharedPreferences(Setting, Context.MODE_PRIVATE);
                        sp.edit().putBoolean(TEACH_DIALOG_VISIBLE_FLAG, false).commit();
                    }
                });
                diaglogOfTech.show();


            }

        }

        @Override
        protected Void doInBackground(Void... params) {
            this.map = BeanUtil.getMap();
            setViewById();
            setKeyBoardListener();
            setInvoiceDataAdapter();
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        this.dto = new MainDTO();
        this.myVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        this.map = BeanUtil.getMap();
        new SettingJob(map).execute();


    }

    private boolean isTechDiaglogFlag() {
        SharedPreferences sp =
                getSharedPreferences(Setting, Context.MODE_PRIVATE);


        return sp.getBoolean(TEACH_DIALOG_VISIBLE_FLAG, true);
    }

    /**
     * SET VIEW OBJECT BY ID
     */
    private void setViewById() {

        this.spinner = (Spinner) this.findViewById(R.id.monthSpinner);
        this.invoviceLabel = (TextView) this.findViewById(R.id.invoviceLabel);
        this.invoiceContent = (TextView) this.findViewById(R.id.invoiceContent);
        this.messageLabel = (TextView) this.findViewById(R.id.messageLabel);
        this.content = (ViewGroup) this.findViewById(R.id.content);
        this.addCalendarBtn = (Button) this.findViewById(R.id.addCalendarBtn);
        this.keyBoard = (KeyBoardLayout) this.findViewById(R.id.keyboardLayout);


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
        } else {
            myAlertDialog.setMessage("沒有中獎");
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
        keyBoardLayout.setChangeView(showView);

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
        messageLabel.setText(R.string.haveOpportunity);
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


        messageLabel.setText("中六獎");

    }


    /**
     * 設定鍵盤監控事件
     */
    public void setKeyBoardListener() {


        this.keyBoard.setChangeView(this.invoviceLabel);
        this.keyBoard.setOnValueChangeListener(new KeyBoardLayout.OnValueChangeListener() {
            @Override
            public void onChange(String value) {

                messageLabel.setText(getString(R.string.plsEnter));

                if (value.length() == 3) {

                    final CheckStatus checkStatus =
                            commomUtil.checkAward3Number(value, dto.getInvoices());

                    switch (checkStatus) {
                        case None:
                            messageLabel.setText(R.string.no_award_change);
                            break;

                        case Continue:
                            work2Continue(value);
                            break;

                        case Get:
                            workForSixAward(value);
                            break;
                    }
                    keyBoard.cleanValueWithoutUI();
                }
            }
        });


        this.addCalendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, AwardActivity.class);

                BeanUtil.infoV2 = dto.getInvoiceInfoV2();
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
            AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);

            myAlertDialog.setTitle(R.string.teachTitle);
            myAlertDialog.setMessage(R.string.teachContent);
            myAlertDialog.setNegativeButton("知道", null);
            myAlertDialog.show();


            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    private class OrderObject {
        private Invoice invoice;
        private Award award;
    }


}
