package tw.com.wa.invoice;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;

import tw.com.wa.invoice.domain.Invoice;
import tw.com.wa.invoice.domain.InvoiceInfoV2;

/**
 * Created by Andy on 14/12/24.
 */
public class InvoiceApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(Invoice.class);
        ParseObject.registerSubclass(InvoiceInfoV2.class);

        Parse.enableLocalDatastore(this);//本地存資料
        Parse.initialize(this, "hgne1bjc7IaI7ZmpBN7dobThoeVzGy6RirURDo44", "K9Qum9KClGT789nE2fkleYqXa294NVO9I12cHxQI");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
