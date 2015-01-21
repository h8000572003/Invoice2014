package tw.com.wa.invoice.util;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

import tw.com.wa.invoice.R;
import tw.com.wa.invoice.domain.Invoice;
import tw.com.wa.invoice.domain.InvoiceInfoV2;

/**
 * Created by Andy on 2014/12/23.
 */
public class DbHelper extends SQLiteOpenHelper {

    final static String DB_NAME = "INVOICE_DB";
    final static int VERSION = 2;
    private static final String TAG = "DbHelper";


    final Context context;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
        this.context = context;
    }


    @Override

    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(this.readRawFile(R.raw.db));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(this.readRawFile(R.raw.del_db));
            db.execSQL(this.readRawFile(R.raw.db));
        } catch (Exception e) {
            Log.e(TAG, "e:" + e.getMessage(), e);
        }
    }

    String readRawFile(int rawId) throws Exception {

        Resources resources = this.context.getResources();
        InputStream is = null;
        try {
            is = resources.openRawResource(rawId);
            byte buffer[] = new byte[is.available()];
            is.read(buffer);
            return new String(buffer);

        } catch (IOException e) {
            throw e;

        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    Log.e(TAG, "close file", e);
                }
            }
        }
    }

    public void insert(SQLiteDatabase coon, InvoiceInfoV2 invoiceInfo) throws RuntimeException {
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", invoiceInfo.getTitle());

        coon.insertOrThrow("InvoiceInfo", null, contentValues);
    }

    public void insert(SQLiteDatabase coon, Invoice invoice) throws RuntimeException {
        ContentValues contentValues = new ContentValues();
        contentValues.put("awards", invoice.getAwards());
        contentValues.put("number", invoice.getNumber());
        contentValues.put("specialize", invoice.isSpecialize() ? "Y" : "N");
        coon.insertOrThrow("Invoice", null, contentValues);
    }
}
