package tw.com.wa.invoice.util;

import tw.com.wa.invoice.domain.Award;
import tw.com.wa.invoice.domain.BeanUtil;
import tw.com.wa.invoice.domain.CheckStatus;

/**
 * Created by Andy on 15/1/24.
 */
public class AwardUtl {

    private static CommomUtil commomUtl = null;


    public static CheckStatus getString(String number) {
        return getCommomUtl().checkStatus(number, BeanUtil.getInfo().getInvoice());
    }
    public static Award getBestAwrd(String number){
        return getCommomUtl().checkBestAward(number,BeanUtil.getInfo().getInvoice());
    }

    public static CommomUtil getCommomUtl() {
        if (commomUtl == null) {
            commomUtl = new CommomUtil();
        }
        return commomUtl;
    }
}

