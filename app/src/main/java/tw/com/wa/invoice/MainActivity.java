package tw.com.wa.invoice;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

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
import tw.com.wa.invoice.domain.MainDTO;
import tw.com.wa.invoice.domain.MainNumber;
import tw.com.wa.invoice.util.CommomUtil;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {


    private Spinner spinner = null;

    private TextView invoviceLabel = null;
    private TextView invoiceContent = null;
    private TextView messageLabel = null;

    private MainDTO dto;

    private String Setting = "Setting";


    private CommomUtil commomUtil = new CommomUtil();

    private List<String> items = null;

    private RecyclerView my_recycler_view = null;
    private NumberAdapter adapter = null;

    private class OrderObject {
        private Invoice invoice;
        private Award award;
    }


    private class NumberAdapter extends RecyclerView.Adapter<NumberAdapter.ViewHolder> {


        private List<MainNumber> mainNumbers = null;
        private Context context;

        private NumberAdapter(List<MainNumber> mainNumbers, Context context) {
            this.mainNumbers = mainNumbers;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.number_layout, null, false);
            // set the view's size, margins, paddings and layout parameters

            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {


            final MainNumber mainNumber = mainNumbers.get(position);


            viewHolder.awardText.setText(mainNumber.getAward().message);
            viewHolder.numberText.setText(mainNumber.getCountOfInvoice());
            viewHolder.doorText.setText(mainNumber.sum() + "元");
        }

        @Override
        public int getItemCount() {
            return mainNumbers.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public TextView awardText;
            private TextView doorText;
            private TextView numberText;

            public ViewHolder(View v) {
                super(v);
                doorText = (TextView) v.findViewById(R.id.doorText);
                awardText = (TextView) v.findViewById(R.id.awardText);
                numberText = (TextView) v.findViewById(R.id.numberText);


            }
        }


//        private NumberAdapter(List<MainNumber> mainNumbers, Context context) {
//            this.mainNumbers = mainNumbers;
//            this.context = context;
//        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SharedPreferences sp =
                getSharedPreferences(Setting, Context.MODE_PRIVATE);


        boolean isFirstTimeFlag = sp.getBoolean("isFirstTime", true);
        //fixme
        if (isFirstTimeFlag) {
            sp.edit().putBoolean("isFirstTime", true).commit();

            AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);

            myAlertDialog.setTitle(R.string.teachTitle);
            myAlertDialog.setMessage(R.string.teachContent);
            myAlertDialog.setNegativeButton("知道", null);
            myAlertDialog.show();


        }

        this.dto = new MainDTO();


        this.spinner = (Spinner) this.findViewById(R.id.monthSpinner);
        this.invoviceLabel = (TextView) this.findViewById(R.id.invoviceLabel);
        this.invoiceContent = (TextView) this.findViewById(R.id.invoiceContent);
        this.messageLabel = (TextView) this.findViewById(R.id.messageLabel);
        this.my_recycler_view = (RecyclerView) this.findViewById(R.id.my_recycler_view);


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

        this.setInvoiceDataAdapter();
        this.setInvoiceNumAdapter();


    }

    /**
     * 設定對發票日期
     */
    private void setInvoiceDataAdapter() {
        items = new ArrayList<>();

        for (Map.Entry<String, InvoiceInfoV2> entry : BeanUtil.map.entrySet()) {
            items.add(entry.getKey());
        }
        BaseAdapter spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        this.spinner.setAdapter(spinnerAdapter);
        this.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                InvoiceInfoV2 info =
                        BeanUtil.map.get(items.get(position));

                dto.setInvoices(info.getInvoice());


                List<OrderObject> rrderObjects = new ArrayList<OrderObject>();


                for (Invoice invoice : info.getInvoice()) {
                    OrderObject obj = new OrderObject();
                    obj.award = Award.lookup(invoice.getAwards());
                    obj.invoice = invoice;
                    rrderObjects.add(obj);


                }

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

    private void setInvoiceNumAdapter() {
        adapter = new NumberAdapter(dto.getMainNumbers(), this);
        this.my_recycler_view.setAdapter(adapter);
        this.refreshNumAdapter();
        ;
    }

    private void refreshNumAdapter() {
        adapter.notifyDataSetChanged();
        ;
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
        if (id == R.id.action_settings) {
            AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);

            myAlertDialog.setTitle(R.string.teachTitle);
            myAlertDialog.setMessage(R.string.teachContent);
            myAlertDialog.setNegativeButton("知道", null);
            myAlertDialog.show();


            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void cleanValue(View view) {
        this.dto.setNumber("");
        this.invoviceLabel.setText("");
        this.messageLabel.setVisibility(View.INVISIBLE);
    }


    /**
     * 點擊號碼
     *
     * @param view
     */
    public void clickValue(View view) {
        this.messageLabel.setVisibility(View.INVISIBLE);


        final TextView label = (TextView) view;

        dto.setNumber(dto.getNumber() + label.getText());


        invoviceLabel.setText(dto.getNumber());


        final CheckStatus checkStatus =
                commomUtil.checkAward3Number(dto.getNumber(), dto.getInvoices());

        switch (checkStatus) {


            case None:


                this.messageLabel.setVisibility(View.VISIBLE);
                this.messageLabel.setText("沒得獎，換一張");

                dto.setNumber("");


                break;


            case Continue:

                this.messageLabel.setVisibility(View.VISIBLE);
                this.messageLabel.setText("有中大獎的可能，請輸入完整發票號");


                AlertDialog.Builder builder = new AlertDialog.Builder(this);


                View dialog = LayoutInflater.from(this).inflate(R.layout.dialog_layout, null);
                builder.setCancelable(false);
                builder.setView(dialog);

                final TextView textView = (TextView) dialog.findViewById(R.id.numberText);


                final AlertDialog dialog1 = builder.create();


                final TouchKey keyAction = new TouchKey(textView, dialog1);

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

                dto.setNumber("");


                break;


            case Get:
                AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);

                myAlertDialog.setTitle(getString(R.string.app_name));

                myAlertDialog.setMessage("中六獎");
                myAlertDialog.setNegativeButton("知道", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        addAward(Award.Sixth);

                    }
                });
                myAlertDialog.show();

                invoviceLabel.setText("");

                dto.setNumber("");

                break;


        }


    }

    private void addAward(Award award) {
        final MainNumber number = new MainNumber(Award.Sixth);
        number.setCountOfInvoice(1);


        if (dto.getMainNumbers().contains(number)) {
            MainNumber inDtoNumber =
                    dto.getMainNumbers().get(dto.getMainNumbers().indexOf(number));

            inDtoNumber.setCountOfInvoice(inDtoNumber.getCountOfInvoice() + 1);


        } else {
            dto.getMainNumbers().add(number);
        }
        this.refreshNumAdapter();

    }

    private class TouchKey implements View.OnClickListener {
        private String number = "";
        private TextView text = null;
        private AlertDialog dialog1;

        private TouchKey(TextView text, AlertDialog dialog1) {
            this.text = text;
            this.dialog1 = dialog1;
        }

        @Override
        public void onClick(View v) {
            TextView textView = (TextView) v;


            number += textView.getText();

            text.setText(number);


            if (number.length() == 8) {
                CommomUtil commomUtil = new CommomUtil();


                Award award =
                        commomUtil.checkAward(number, dto.getInvoices());


                AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(MainActivity.this);

                myAlertDialog.setTitle(getString(R.string.app_name));

                if (award != null) {

                    addAward(award);


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


                number = "";

                ;
            }
        }
    }

    @Override
    public void onClick(View v) {
        this.clickValue(v);
    }
}
