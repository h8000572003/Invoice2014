package tw.com.wa.invoice;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import tw.com.wa.invoice.util.CommomUtil;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private final static int GO_SEE_INVOICE_CODE = 001;
    private final static String Setting = "Setting";
    private String TEACH_DIALOG_VISIBLE_FLAG = "isVisibleFlag";

    private Spinner spinner = null;
    private TextView invoviceLabel = null;
    private TextView invoiceContent = null;
    private TextView messageLabel = null;
    private ViewGroup content = null;
    private Button addCalendarBtn;
    private TableLayout keyboardLayout = null;

    private MainDTO dto;
    private Map<String, InvoiceInfoV2> map = null;
    private List<String> items = null;
    private InvoiceKeyIn keyIn = null;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.dto = new MainDTO();

        map = BeanUtil.getMap();


        if (this.isTechDiaglogFlag()) {
            AlertDialog.Builder diaglogOfTech = new AlertDialog.Builder(this);
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


        this.setViewById();
        this.setActionListener();
        this.setInvoiceDataAdapter();

        myVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

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
        this.keyboardLayout = (TableLayout) this.findViewById(R.id.keyboardLayout);


    }


    /**
     * set action listner
     */
    public void setActionListener() {
        this.findViewById(R.id.button).setOnClickListener(this);
        this.findViewById(R.id.btn1).setOnClickListener(this);
        this.findViewById(R.id.btn2).setOnClickListener(this);
        this.findViewById(R.id.btn3).setOnClickListener(this);
        this.findViewById(R.id.btn4).setOnClickListener(this);
        this.findViewById(R.id.btn5).setOnClickListener(this);
        this.findViewById(R.id.btn6).setOnClickListener(this);
        this.findViewById(R.id.btn7).setOnClickListener(this);
        this.findViewById(R.id.btn8).setOnClickListener(this);
        this.findViewById(R.id.btn9).setOnClickListener(this);


        this.findViewById(R.id.cleanBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleanValue(v);
            }
        });


        this.addCalendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, AwardActivity.class);
                BeanUtil.infoV2 = dto.getInvoiceInfoV2();
                startActivityForResult(it, GO_SEE_INVOICE_CODE);
                startActivity(it);
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


                }

                /**
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

    /**
     * Clean 畫面輸入
     *
     * @param view
     */
    public void cleanValue(View view) {
        this.dto.setNumber("");
        this.invoviceLabel.setText("");
        this.messageLabel.setText("");
    }

    /**
     * 點擊號碼
     *
     * @param view
     */
    public void clickValue(View view) {
        this.messageLabel.setText(getString(R.string.plsEnter));


        final TextView label = (TextView) view;

        this.dto.setNumber(dto.getNumber() + label.getText());


        this.invoviceLabel.setText(dto.getNumber());

        if (dto.getNumber().length() < 3) {
            return;
        }


        final CheckStatus checkStatus =
                commomUtil.checkAward3Number(dto.getNumber(), dto.getInvoices());

        keyIn = new InvoiceKeyIn(dto.getNumber());

        switch (checkStatus) {


            case None:


                this.messageLabel.setVisibility(View.VISIBLE);
                this.messageLabel.setText("沒得獎，換一張");
                this.dto.setNumber("");
                break;


            case Continue:

                this.messageLabel.setVisibility(View.VISIBLE);
                this.messageLabel.setText("有中大獎的可能，請輸入完整發票號");

                AlertDialog.Builder builder = new AlertDialog.Builder(activity);


                View dialog = LayoutInflater.from(activity).inflate(R.layout.dialog_layout, null);
                builder.setCancelable(false);
                builder.setView(dialog);

                final TextView textView = (TextView) dialog.findViewById(R.id.numberText);


                final AlertDialog dialog1 = builder.create();
                dialog1.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Toast.makeText(activity, "dismiss..", Toast.LENGTH_SHORT).show();

                        keyboardLayout.setVisibility(View.VISIBLE);
                    }
                });
                dialog1.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        Toast.makeText(activity, "cancel..", Toast.LENGTH_SHORT).show();
                        keyboardLayout.setVisibility(View.VISIBLE);
                    }
                });

                final DialogTouchClickListener keyAction = new DialogTouchClickListener(textView, dialog1);

                dialog.findViewById(R.id.button).setOnClickListener(keyAction);
                dialog.findViewById(R.id.btn1).setOnClickListener(keyAction);
                dialog.findViewById(R.id.btn2).setOnClickListener(keyAction);
                dialog.findViewById(R.id.btn3).setOnClickListener(keyAction);
                dialog.findViewById(R.id.btn4).setOnClickListener(keyAction);
                dialog.findViewById(R.id.btn5).setOnClickListener(keyAction);
                dialog.findViewById(R.id.btn6).setOnClickListener(keyAction);
                dialog.findViewById(R.id.btn7).setOnClickListener(keyAction);
                dialog.findViewById(R.id.btn8).setOnClickListener(keyAction);
                dialog.findViewById(R.id.btn9).setOnClickListener(keyAction);

                dialog.findViewById(R.id.cleanBtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        keyAction.number = "";
                        keyAction.text.setText("");
                    }
                });
                dialog1.show();


                Animation animation = AnimationUtils.loadAnimation(activity, android.R.anim.fade_out);


                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        keyboardLayout.setVisibility(View.INVISIBLE);




                        dto.setNumber("");
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                this.keyboardLayout.startAnimation(animation);


                break;


            case Get:

                myVibrator.vibrate(200);
                keyIn.setAward(Award.Exactsix);
                BeanUtil.allInvoices.add(keyIn);

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

//                myAlertDialog.show();
                this.messageLabel.setText("中六獎");

                invoviceLabel.setText("");

                dto.setNumber("");

                break;


        }


    }


    @Override
    public void onClick(View v) {
        this.clickValue(v);
    }

    private class OrderObject {
        private Invoice invoice;
        private Award award;
    }

    private class DialogTouchClickListener implements View.OnClickListener {
        private String number = "";
        private TextView text = null;
        private AlertDialog dialog1;

        private DialogTouchClickListener(TextView text, AlertDialog dialog1) {
            this.text = text;
            this.dialog1 = dialog1;
        }

        private void doCheck() {
            CommomUtil commomUtil = new CommomUtil();


            Award award =
                    commomUtil.checkAward(number, dto.getInvoices());


            AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(MainActivity.this);

            myAlertDialog.setTitle(getString(R.string.app_name));

            if (award != null) {


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
                myAlertDialog.setMessage(" 沒有中獎下次再加油");
            }


            myAlertDialog.setNegativeButton("知道", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog1.dismiss();
                }
            });
            myAlertDialog.show();
        }

        @Override
        public void onClick(View v) {
            TextView textView = (TextView) v;


            number += textView.getText();

            text.setText(number);

            keyIn = new InvoiceKeyIn(number);

            if (number.length() == 8) {

                this.doCheck();


                number = "";

                ;
            }
        }
    }
}
