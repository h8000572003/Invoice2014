package tw.com.wa.invoice.util;

import android.os.Parcel;
import android.os.Parcelable;

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

    /**
     * 取得領獎區間
     * @return
     */
    public InvoYm getAwardRangDate(){

        final InvoYm newYm = new InvoYm(this.beging, this.end);
        newYm.beging.add(Calendar.MONTH,+3);
        newYm.beging.set(Calendar.DAY_OF_MONTH,6);


        newYm.end.setTime(newYm.beging.getTime());
        newYm.end.add(Calendar.MONTH,+3);
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

        int beginYear = beging.get(Calendar.YEAR);
        int beginMon = beging.get(Calendar.MONTH) +1;


        int lastYear = end.get(Calendar.YEAR);
        int lastMon = end.get(Calendar.MONTH) + 1;

        return String.format("%03d/%02d/6-%03d/%02d/5", beginYear, beginMon,lastYear,lastMon);
    }


}
