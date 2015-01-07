package tw.com.wa.invoice.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andy on 2015/1/7.
 */
public enum Msg {


    Good("200", "處理成功");
    ;
    final String code;
    final String msg;
    final static Map<String, Msg> MAP;

    Msg(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    static {
        Map<String, Msg> map = new HashMap<String, Msg>();

        for (Msg msg : Msg.values()) {
            map.put(msg.code, msg);
        }

        MAP = Collections.unmodifiableMap(map);


    }

    public static Msg lookupByCode(String code) throws InvoiceBusinessException {

        if (MAP.containsKey(code)) {
            return MAP.get(code);
        }
        throw new InvoiceBusinessException("代碼檔有誤");
    }
}
