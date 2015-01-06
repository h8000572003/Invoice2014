package tw.com.wa.invoice.util;

import android.util.Log;


import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import tw.com.wa.invoice.domain.WiningBean;

/**
 * Created by Andy on 2015/1/6.
 */
public class GetCompent<Result> {

    public static final String API = "https://www.einvoice.nat.gov.tw/PB2CAPIVAN/invapp/InvApp";


    public static final String API_ID = "EINV9201412111086";
    private static final String TAG = "GetCompent";

    /**
     * getCompent
     *
     * @param yyymm
     * @return
     * @throws Exception
     */
    public List<WiningBean> getWinings(String yyymm) throws Exception {


        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("version", "0.2"));
        params.add(new BasicNameValuePair("action", "QryWinningList"));
        params.add(new BasicNameValuePair("invTerm", yyymm));
        params.add(new BasicNameValuePair("UUID", UUID.randomUUID().toString()));
        params.add(new BasicNameValuePair("appID", API_ID));

        TypeToken typeToken = new TypeToken<List<WiningBean>>() {
        };


        StringBuffer buffer = new StringBuffer();
        buffer.append("action=QryWinningLis");
        buffer.append("&");

        buffer.append("appID=EINV9201412111086");
        buffer.append("&");

        buffer.append("invTerm=");
        buffer.append(yyymm);
        buffer.append("&");


        buffer.append("UUID=");
        buffer.append("&");

        buffer.append("version=0.2");


        return (List<WiningBean>) this.getJsonString(GetCompent.API, params, typeToken);


    }

    public Result getJsonString(String url, List<NameValuePair> pairs, TypeToken<Result> type) throws Exception {

        DefaultHttpClient demo = new DefaultHttpClient();
        demo.getParams().setParameter("http.protocol.content-charset", "UTF-8");


        HttpPost httpPost = new HttpPost(url);
        StringEntity entity = new StringEntity(URLEncodedUtils.format(pairs, "UTF-8"));
        httpPost.setEntity(entity);
        HttpResponse response = demo.execute(httpPost);
        String responseString = EntityUtils.toString(response.getEntity());
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            // 如果回傳是 200 OK 的話才輸出
            Log.i(TAG, responseString);
            return this.getListofGson(responseString, type.getType());

        } else {

        }
        return null;
    }

    public Result getListofGson(String jsonString, Type listType) {
        Objects arrayObject = null;

        try {
            arrayObject = new Gson().fromJson(jsonString, listType);
        } catch (Exception e) {
            // e.printStackTrace();
            Log.e(TAG, e.getMessage(), e);
            return null;
        }

        return (Result) arrayObject;

    }

}