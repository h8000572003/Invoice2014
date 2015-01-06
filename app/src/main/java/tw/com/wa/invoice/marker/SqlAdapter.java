package tw.com.wa.invoice.marker;

import com.google.gson.reflect.TypeToken;

import tw.com.wa.invoice.util.Api;

/**
 * Created by Andy on 2015/1/6.
 */
public abstract class SqlAdapter {
    protected Api api;


    public SqlAdapter(Api api) {
        this.api = api;
    }

    protected abstract String getSqlString();

    protected abstract TypeToken getToken();


    public Api getApi() {
        return api;
    }

    public void setApi(Api api) {
        this.api = api;
    }
}
