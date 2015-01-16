package tw.com.wa.invoice.compent;

import tw.com.wa.invoice.domain.Award;

/**
 * Created by Andy on 2015/1/16.
 */
public abstract class ExecutionCheckManger {
    private static ExecutionCheckInvoiceAward executionCheckInvoiceAward;

    public interface OnAwardListener {
        public void onEnd(Award award);
    }



    public static ExecutionCheckInvoiceAward get() {
        if (executionCheckInvoiceAward == null) {
            executionCheckInvoiceAward = new BaseCheckInvoiceAward();
        }
        return executionCheckInvoiceAward;
    }

    private ExecutionCheckManger() {

    }

}
