package tw.com.wa.invoice.util;

import java.util.List;

import tw.com.wa.invoice.domain.Invoice;
import tw.com.wa.invoice.domain.WiningInfo;

/**
 * Created by Andy on 15/1/24.
 */
public class RisCommon {

    private static RisCommon risCommon = null;


    private RisCommon() {

    }


    public static RisCommon getRisCommon() {
        if (risCommon == null) {
            risCommon = new RisCommon();
        }
        return risCommon;
    }


    public String getImYm(WiningInfo info) {
        return info.getStages().getStatge();
    }

    public String getTitle(WiningInfo info) {
        return info.getTitle();
    }

    public List<Invoice> getInvoice(WiningInfo info) {
        return info.getInvoice();
    }

    public String getRang(WiningInfo info) {
        return info.getStages().getAwardRangDate().toString();
    }
}
