package tw.com.wa.invoice.marker;

import android.util.Log;

import com.google.gson.reflect.TypeToken;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;

import tw.com.wa.invoice.util.GetCompent;
import tw.com.wa.invoice.util.InvoiceBusinessException;

/**
 * Created by Andy on 2015/1/6.
 */
public class ApiMarker<T> {
    private static final String TAG = "ApiMarker";
    private SqlAdapter adapter;

    public void setAdapter(SqlAdapter adapter) {
        this.adapter = adapter;
    }

    public T getQuery() throws InvoiceBusinessException {
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        GetCompent getCompent = new GetCompent();


        TypeToken<T> typeToken = new TypeToken<T>() {
        };

        T result = null;

        try {
            result = getCompent.getJsonString(adapter.getSqlString(), params, typeToken);
        } catch (Exception e) {
            Log.e(TAG, "error", e);
        }

        return result;
    }
}
