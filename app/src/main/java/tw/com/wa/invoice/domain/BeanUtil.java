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

    static {
        mainNumbers.add(new MainNumber(Award.Fifth));
        mainNumbers.add(new MainNumber(Award.Top));
        mainNumbers.add(new MainNumber(Award.Fifth));
        mainNumbers.add(new MainNumber(Award.Fifth));
        mainNumbers.add(new MainNumber(Award.Fifth));
        mainNumbers.add(new MainNumber(Award.Fifth));
        mainNumbers.add(new MainNumber(Award.Fifth));
        mainNumbers.add(new MainNumber(Award.Top));
        mainNumbers.add(new MainNumber(Award.Fifth));
        mainNumbers.add(new MainNumber(Award.Fifth));
        mainNumbers.add(new MainNumber(Award.Fifth));
        mainNumbers.add(new MainNumber(Award.Fifth));
    }
}
