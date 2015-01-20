package tw.com.wa.invoice.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import tw.com.wa.invoice.domain.InvoiceEnter;

/**
 * Created by Andy on 2015/1/20.
 */
public class InvoiceEnterDAO extends GenericDAO<InvoiceEnter> {


    public InvoiceEnterDAO(SQLiteDatabase db, Context context) {
        super(db, context);
    }

    @Override
    public DBContract.Table getTable() {
        return DBContract.Table.INVOICE_ENTER;
    }

    public List<InvoiceEnter> get(String inYm) {
        return super.doQuery("inYm=?", new String[]{inYm}, InvoiceEnter.class);
    }
}
