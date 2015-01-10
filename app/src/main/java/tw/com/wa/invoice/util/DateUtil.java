package tw.com.wa.invoice.util;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Andy on 2015/1/9.
 */
public class DateUtil {
    public static final String YEAR_MON_DAY_PATTERN = "yyyyMMdd";
    public static final String YEAR_MONTH_PATTERN = "yyyyMM";
    static final SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
    private static final String TAG = "DateUtil";

    public static InvoYm getYm(String invoceYm) throws InvoiceBusinessException {

        try {

            final int year = Integer.parseInt(invoceYm.substring(0, 3)) + 1911;
            final int month = Integer.parseInt(invoceYm.substring(3));
            final String yyyymm = String.format("%04d%02d", year, month);
            final SimpleDateFormat format = new SimpleDateFormat(YEAR_MONTH_PATTERN);


            Calendar calendarOfBegin = Calendar.getInstance();
            calendarOfBegin.setTime(format.parse(yyyymm));
            calendarOfBegin.add(Calendar.MONTH, -1);


            Calendar calendarOfEnd = Calendar.getInstance();
            calendarOfEnd.setTime(format.parse(yyyymm));

            InvoYm ym = new InvoYm(calendarOfBegin, calendarOfEnd);


            return ym;
        } catch (Exception e) {
            Log.e(TAG, "e:" + e.getMessage());
            throw new InvoiceBusinessException(e.getMessage());

        }


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
