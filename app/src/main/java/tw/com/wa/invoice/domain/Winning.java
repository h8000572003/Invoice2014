package tw.com.wa.invoice.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tw.com.wa.invoice.util.InvoYm;

/**
 * Created by Andy on 2015/1/12.
 */
public class Winning {
    private Map<String, List<InvoiceKeyIn>> map = new HashMap();

    public void addInvoive(String ym, InvoiceKeyIn keyIn) {
        List<InvoiceKeyIn> keys = map.get(ym);
        if (keyIn == null) {
            keys = new ArrayList<InvoiceKeyIn>();
            map.put(ym, keys);
        }
        keys.add(keyIn);

    }

    public List<InvoiceKeyIn> get(String ym) {
        if (map.containsKey(ym)) {
            return map.get(ym);
        }
        return Collections.<InvoiceKeyIn>emptyList();
    }

}
