package tw.com.wa.invoice.marker;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import tw.com.wa.invoice.domain.WiningBean;
import tw.com.wa.invoice.util.Api;
import tw.com.wa.invoice.util.Contract;

/**
 * Created by Andy on 2015/1/6.
 */
public class WiningsAdapter extends SqlAdapter {

    private String invTerm;


    /**
     * @param invTerm 期別
     */
    public WiningsAdapter(String invTerm) {
        super(Api.QuryWin);
        this.invTerm = invTerm;
    }


    @Override
    protected TypeToken getToken() {
        TypeToken<WiningBean> typeToken = new TypeToken<WiningBean>() {
        };
        return typeToken;
    }

    @Override
    protected List<AdpterParmer> getParmers() {
        final List<AdpterParmer> parmers = new ArrayList<AdpterParmer>();
        parmers.add(AdpterParmer.get("action", "QryWinningList"));
        parmers.add(AdpterParmer.get("appID", Contract.API_ID));
        parmers.add(AdpterParmer.get("invTerm", invTerm));
        return parmers;
    }
}
