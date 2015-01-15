package tw.com.wa.invoice.marker;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import tw.com.wa.invoice.util.Api;
import tw.com.wa.invoice.util.GetCompent;

/**
 * Created by Andy on 2015/1/15.
 */
public class DetialByQRCodeAdapter extends SqlAdapter {

    private String invNum;
    private String invTerm;

    public DetialByQRCodeAdapter(Api api) {
        super(Api.QuryDetial);
    }

    @Override
    protected TypeToken getToken() {
        return null;
    }

    @Override
    protected List<AdpterParmer> getParmers() {
        final List<AdpterParmer> parmers = new ArrayList<AdpterParmer>();
        parmers.add(AdpterParmer.get("type", "Barcode"));
        parmers.add(AdpterParmer.get("invNum", invNum));
        parmers.add(AdpterParmer.get("action", "qryInvDetail"));
        parmers.add(AdpterParmer.get("generation", "generation"));
        parmers.add(AdpterParmer.get("invTerm", invTerm));
        return parmers;
    }
}
