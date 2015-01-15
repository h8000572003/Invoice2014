package tw.com.wa.invoice.marker;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.parse.codec.binary.StringUtils;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tw.com.wa.invoice.util.GetCompent;
import tw.com.wa.invoice.util.InvoiceBusinessException;

/**
 * Created by Andy on 2015/1/6.
 */
public class ApiGetter<T> {

    private static final String TAG = "ApiGetter";

    private static final String AND_SIGNAL = "&";

    private SqlAdapter adapter;

    private GetCompent getCompent = new GetCompent();

    public void setAdapter(SqlAdapter adapter) {
        this.adapter = adapter;
    }

    public T getQuery() throws InvoiceBusinessException {

        this.check();

        T result = null;
        try {
            result = this.getCompent.getJsonString(//
                    this.getUrl(), //
                    Collections.<NameValuePair>emptyList(),//
                    this.adapter.getToken());//
        } catch (Exception e) {
            Log.e(TAG, "error", e);
            throw new InvoiceBusinessException(e.getMessage());
        }

        Log.i(TAG, result.toString());
        return result;
    }

    private String getUrl() {


        final StringBuffer url = new StringBuffer();


        final List<AdpterParmer> parmers = this.adapter.getParmers();
        url.append(adapter.getApi().getBaseSqlAppendCommonValue());

        if (parmers == null || parmers.isEmpty()) {
            return url.toString();
        } else {

            return url.toString() + AND_SIGNAL + TextUtils.join(AND_SIGNAL, parmers);

        }

    }

    private void check() throws InvoiceBusinessException {
        if (this.adapter == null) {
            throw new InvoiceBusinessException("adapter is null");
        }

    }
}
