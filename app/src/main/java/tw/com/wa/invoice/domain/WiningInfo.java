package tw.com.wa.invoice.domain;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

import tw.com.wa.invoice.util.InvoYm;

/**
 * Created by Andy on 2015/1/9.
 */
public interface WiningInfo  {
    public List<Invoice> getInvoice();

    public String getTitle();





    public InvoYm getStages();


}
