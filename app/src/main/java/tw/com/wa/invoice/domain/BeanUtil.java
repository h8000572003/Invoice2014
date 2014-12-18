package tw.com.wa.invoice.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Andy on 2014/12/12.
 */
public class BeanUtil {

    public static Map<String, InvoiceInfoV2> map = new HashMap<String, InvoiceInfoV2>();

    public static List<MainNumber> mainNumbers = new ArrayList<MainNumber>();

    public static List<InvoiceKeyIn> allInvoices = new ArrayList<InvoiceKeyIn>();

    static {
        mainNumbers.add(new MainNumber(Award.Veryspecial));
        mainNumbers.add(new MainNumber(Award.Special));

    }
}
