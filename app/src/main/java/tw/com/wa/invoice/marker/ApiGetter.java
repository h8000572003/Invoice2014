package tw.com.wa.invoice.marker;

import android.util.Log;

import com.google.gson.reflect.TypeToken;

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
    private static final String TAG = "ApiMarker";

    private SqlAdapter adapter;

    private GetCompent getCompent = new GetCompent();

    public void setAdapter(SqlAdapter adapter) {
        this.adapter = adapter;
    }

    public T getQuery() throws InvoiceBusinessException {

        this.check();
        T result = null;
        try {
            result = getCompent.getJsonString(//
                    adapter.getSqlString(), //
                    Collections.<NameValuePair>emptyList(),//
                    adapter.getToken());//
        } catch (Exception e) {
            Log.e(TAG, "error", e);
            throw new InvoiceBusinessException(e.getMessage());
        }

        Log.i(TAG, result.toString());
        return result;
    }

    private void check() throws InvoiceBusinessException {
        if (this.adapter == null) {
            throw new InvoiceBusinessException("adapter is null");
        }

    }
}
