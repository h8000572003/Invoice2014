package tw.com.wa.invoice.api;

import android.app.Activity;

import tw.com.wa.invoice.domain.LoadDTO;
import tw.com.wa.invoice.util.InvoiceBusinessException;

/**
 * Created by Andy on 2015/1/7.
 */
public interface LoadService {
    public void loadData(LoadDTO dto, Activity activity) throws InvoiceBusinessException;
}
