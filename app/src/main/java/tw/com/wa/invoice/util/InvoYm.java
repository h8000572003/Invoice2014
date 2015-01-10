package tw.com.wa.invoice.util;

import java.util.Calendar;

/**
 * Created by Andy on 2015/1/9.
 */
public class InvoYm {
    private Calendar beging;
    private Calendar end;


    public InvoYm(Calendar beging, Calendar end) {
        this.beging = Calendar.getInstance();
        this.end = Calendar.getInstance();


        this.beging.setTime(beging.getTime());
        this.end.setTime(end.getTime());

    }

    public static InvoYm before(InvoYm ym) {
        final InvoYm newYm = new InvoYm(ym.beging, ym.end);
        newYm.end.add(Calendar.MONTH, -2);
        newYm.beging.add(Calendar.MONTH, -2);

        return newYm;

    }

    public static InvoYm after(InvoYm ym) {
        final InvoYm newYm = new InvoYm(ym.beging, ym.end);
        newYm.end.add(Calendar.MONTH, +2);
        newYm.beging.add(Calendar.MONTH, +2);

        return newYm;
    }

    public Calendar getEnd() {
        return end;
    }

    public void setEnd(Calendar end) {
        this.end = end;
    }

    public Calendar getBeging() {
        return beging;
    }

    public void setBeging(Calendar beging) {
        this.beging = beging;
    }

    @Override
    public String toString() {
        int year = end.get(Calendar.YEAR)-1911;
        int mon = end.get(Calendar.MONTH) + 1;

        return String.format("%03d%02d", year, mon);
    }
}
