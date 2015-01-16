package tw.com.wa.invoice.compent;

import tw.com.wa.invoice.domain.Award;
import tw.com.wa.invoice.domain.WiningInfo;

/**
 * Created by Andy on 2015/1/16.
 */
public interface ExecutionCheckInvoiceAward {
    public void exe(String number, WiningInfo info);

    public void setOnAwardListener(ExecutionCheckManger.OnAwardListener onAwardListener);
}
