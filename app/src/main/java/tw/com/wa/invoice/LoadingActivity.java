package tw.com.wa.invoice;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import tw.com.wa.invoice.domain.BeanUtil;
import tw.com.wa.invoice.domain.InVoiceInfo;
import tw.com.wa.invoice.domain.Invoice;

/**
 * Created by Andy on 2014/12/12.
 */
public class LoadingActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_layout);
        Parse.initialize(this, "hgne1bjc7IaI7ZmpBN7dobThoeVzGy6RirURDo44", "K9Qum9KClGT789nE2fkleYqXa294NVO9I12cHxQI");


        new AsyncTask<String, String, String>() {


            @Override
            protected String doInBackground(String... params) {

                ParseQuery<ParseObject> query = ParseQuery.getQuery("Invoice");
                query.addAscendingOrder("title");
                try {
                    List<ParseObject> scoreList = query.find();

                    for (ParseObject parseObject : scoreList) {
                        String title = parseObject.getString("title");
                        InVoiceInfo invoices = BeanUtil.map.get(title);
                        if (invoices == null) {

                            invoices = new InVoiceInfo();
                        }

                        Invoice invoice = new Invoice(//
                                parseObject.getString("awards"),//
                                parseObject.getString("number"),//
                                parseObject.getBoolean("specialize")

                        );
                        invoices.getInvoice().add(invoice);


                        BeanUtil.map.put(title, invoices);

                    }
                    for (String key : BeanUtil.map.keySet()) {
                        ParseQuery<ParseObject> queryInfo = ParseQuery.getQuery("InvoiceInfo");
                        List<ParseObject> results = queryInfo.find();
                        BeanUtil.map.get(key).setDescribe(results.get(0).getString("info"));
                    }
                } catch (Exception e) {
                    return "取得得獎發票錯誤，請確定網路正常再嘗試看看";

                }


                return null;
            }


            @Override
            protected void onPostExecute(String s) {

                if (s != null) {
                    Toast.makeText(LoadingActivity.this, s, Toast.LENGTH_SHORT).show();
                }else{
//FIXME
                    Intent it = new Intent(LoadingActivity.this, MainActivity.class);
                    startActivity(it);
                }


            }
        }.execute("");


    }
}
