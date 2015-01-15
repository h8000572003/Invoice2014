package tw.com.wa.invoice.marker;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import tw.com.wa.invoice.util.Api;

/**
 * Created by Andy on 2015/1/15.
 */
public class DetialByBarcodeAdapter extends SqlAdapter {

    private String invNum;
    private String invTerm;
    private String randomNumber = "0000";
    private String BARCODE = "Barcode";


    /**
     *
     * @param invNum 發票號碼 10碼
     * @param invTerm
     */
    public DetialByBarcodeAdapter(String invNum, String invTerm) {
        super(Api.QuryDetial);
        this.invNum = invNum;
        this.invTerm = invTerm;

    }


    @Override
    protected TypeToken getToken() {
        return null;
    }

    @Override
    protected List<AdpterParmer> getParmers() {

        final List<AdpterParmer> parmers = new ArrayList<AdpterParmer>();
        parmers.add(AdpterParmer.get("type", BARCODE));
        parmers.add(AdpterParmer.get("invNum", invNum));
        parmers.add(AdpterParmer.get("action", "qryInvDetail"));
        parmers.add(AdpterParmer.get("generation", "generation"));
        parmers.add(AdpterParmer.get("invTerm", invTerm));
        parmers.add(AdpterParmer.get("randomNumber", randomNumber));

        return parmers;
    }
}
