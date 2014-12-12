package tw.com.wa.invoice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import tw.com.wa.invoice.domain.CommonUtil;
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


        ParseQuery<ParseObject> query = ParseQuery.getQuery("Invoice");
        query.addAscendingOrder("title");

        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {

                CommonUtil.map.clear();


                for (ParseObject parseObject : scoreList) {
                    String title = parseObject.getString("title");
                    List<Invoice> invoices = CommonUtil.map.get(title);
                    if (invoices == null) {
                        invoices = new ArrayList<Invoice>();
                    }

                    Invoice invoice = new Invoice(//
                            parseObject.getString("awards"),//
                            parseObject.getString("number"),//
                            parseObject.getBoolean("specialize")

                    );
                    invoices.add(invoice);


                    CommonUtil.map.put(title, invoices);

                }


                if (e == null) {
                    Intent it = new Intent(LoadingActivity.this, MainActivity.class);
                    startActivity(it);
                } else {
                    Toast.makeText(LoadingActivity.this, "取得得獎發票錯誤，請確定網路正常再嘗試看看", Toast.LENGTH_SHORT).show();
                    ;
                }
            }
        });


    }
}
