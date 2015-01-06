package tw.com.wa.invoice.marker;

import tw.com.wa.invoice.util.Api;
import tw.com.wa.invoice.util.GetCompent;

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
    protected String getSqlString() {

        StringBuffer buffer = new StringBuffer();
        buffer.append("action=QryWinningList");
        buffer.append("&");

        buffer.append("appID=" + GetCompent.API_ID);
        buffer.append("&");

        buffer.append("invTerm=" + invTerm);


        return getApi().getBaseSqlAppendCommonValue() + buffer.toString();
    }
}
