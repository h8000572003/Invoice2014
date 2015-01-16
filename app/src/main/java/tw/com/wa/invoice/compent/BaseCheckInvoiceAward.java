package tw.com.wa.invoice.compent;

import tw.com.wa.invoice.domain.Award;
import tw.com.wa.invoice.domain.CheckStatus;
import tw.com.wa.invoice.domain.Invoice;
import tw.com.wa.invoice.domain.OutInfo;
import tw.com.wa.invoice.domain.WiningInfo;
import tw.com.wa.invoice.util.CommomUtil;

/**
 * Created by Andy on 2015/1/16.
 */
public class BaseCheckInvoiceAward implements ExecutionCheckInvoiceAward {


    private WiningInfo info = null;
    private CheckStatus checkStatus = CheckStatus.None;
    private CommomUtil commomUtil = new CommomUtil();

    private ExecutionCheckManger.OnAwardListener onAwardListener;


    @Override
    public void setOnAwardListener(ExecutionCheckManger.OnAwardListener onAwardListener) {
        this.onAwardListener = onAwardListener;
    }


    @Override
    public void exe(String number, WiningInfo info) {
        final Award award = commomUtil.checkAward(number, info.getInvoice());
        this.onAwardListener.onEnd(award);

    }
}
