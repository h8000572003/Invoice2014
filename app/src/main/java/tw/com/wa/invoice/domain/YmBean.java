package tw.com.wa.invoice.domain;

import java.util.Calendar;

/**
 * Created by Andy on 15/1/7.
 */
public class YmBean extends Ym {

    final static String DataTemplate = "%d年%d-%d月";

    private Calendar calendar;

    public YmBean() {
        this.calendar = Calendar.getInstance();
    }

    public YmBean(Calendar calendar) {
        this.calendar = calendar;
    }

    @Override
    public void forth() {

        this.calendar.add(Calendar.MONTH, -2);
    }

    @Override
    public void next() {

        this.calendar.add(Calendar.MONTH, +2);
    }

    @Override
    public String getTitle() {

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        return String.format(DataTemplate, year, month, month + 2);
    }

    @Override
    public String twYearMonth() {
        return null;
    }
}
