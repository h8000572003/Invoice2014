package tw.com.wa.invoice.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Andy on 2015/1/9.
 */
public class DateUtil {
    static final SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
    public static final String YEAR_MON_DAY_PATTERN = "yyyyMMdd";
    public static final String YEAR_MONTH_PATTERN = "yyyyMM";

    public static InvoYm getYm(String invoceYm) {
        return null;
    }

    public static String yyyynndd(Calendar calendar) {
        return sdFormat.format(calendar.getTime());

    }

    public static String yyymm(Calendar calendar) {

        int year = calendar.get(Calendar.YEAR) - 1911;
        int month = calendar.get(Calendar.MONTH) + 1;
        return String.format("%3d%02d", year, month);

    }

}
